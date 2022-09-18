package pl.cyryl.quizapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.cyryl.quizapi.answer.Answer;
import pl.cyryl.quizapi.question.Question;
import pl.cyryl.quizapi.question.QuestionDto;
import pl.cyryl.quizapi.question.QuestionService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
class QuizControllerTest {

    private static final long QUESTION_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private QuestionService questionService;

    private Question getQuestion() {
        Question question = new Question();
        question.setId(QUESTION_ID);
        Answer answer1 = new Answer();
        answer1.setCorrect(true);
        answer1.setId(1L);
        answer1.setAnswer("true");
        Answer answer2 = new Answer();
        answer2.setCorrect(false);
        answer2.setId(2L);
        answer2.setAnswer("false");
        question.setAnswers(List.of(answer1, answer2));
        question.setQuestion("True or False?");
        return question;
    }

    @BeforeEach
    public void setUp() {
        Mockito.when(questionService.findById(any(Long.class))).thenReturn(getQuestion());
    }

    @Test
    public void getRandomQuestionSuccess() throws Exception {
        Mockito.when(questionService.getRandomQuestionNoMissingData()).thenReturn(getQuestion());

        mockMvc.perform(get("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is("True or False?")))
                .andExpect(jsonPath("$.answers[1].answer", is("false")));
    }

    @Test
    public void getRandomQuestionFailure() throws Exception {
        Mockito.when(questionService.getRandomQuestionNoMissingData()).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException));
    }

    @Test
    public void postCorrectAnswer() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(QUESTION_ID);
        questionDto.setAnswers(List.of(1L));

        mockMvc.perform(post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct", is(true)));
    }

    @Test
    public void postIncorrectAnswer() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(QUESTION_ID);
        questionDto.setAnswers(List.of(2L));

        mockMvc.perform(post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(questionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct", is(false)));
    }


}
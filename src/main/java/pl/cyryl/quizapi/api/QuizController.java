package pl.cyryl.quizapi.api;

import org.springframework.web.bind.annotation.*;
import pl.cyryl.quizapi.answer.AnswerResponse;
import pl.cyryl.quizapi.question.Question;
import pl.cyryl.quizapi.question.QuestionDto;
import pl.cyryl.quizapi.question.QuestionService;

@RequestMapping("/api")
@RestController
public class QuizController {

    private final QuestionService questionService;

    public QuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public Question getRandomQuestionNoMissingData() {
        return questionService.getRandomQuestionNoMissingData();
    }

    @PostMapping("/answers")
    public AnswerResponse validateAnswer(@RequestBody QuestionDto questionDto) {
        Question question = questionService.findById(questionDto.getQuestionId());
        return new AnswerResponse(question.verifyAnswers(questionDto.getAnswers()));
    }
}

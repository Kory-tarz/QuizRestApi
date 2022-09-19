package pl.cyryl.quizapi.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class QuestionJsonTest {

    @Autowired
    private JacksonTester<Question> jacksonTester;

    @Test
    void testDeserialization() throws Exception {
        String jsonBody = """
                {
                        "id": 550,
                        "question": "Which sign is used to access a variable of  a variable in PHP?",
                        "description": null,
                        "answers": {
                            "answer_a": "$$",
                            "answer_b": "$|",
                            "answer_c": "#@",
                            "answer_d": "$",
                            "answer_e": null,
                            "answer_f": null
                        },
                        "multiple_correct_answers": "false",
                        "correct_answers": {
                            "answer_a_correct": "true",
                            "answer_b_correct": "false",
                            "answer_c_correct": "false",
                            "answer_d_correct": "false",
                            "answer_e_correct": "false",
                            "answer_f_correct": "false"
                        },
                        "correct_answer": "answer_a",
                        "explanation": null,
                        "tip": null,
                        "tags": [
                            {
                                "name": "PHP"
                            }
                        ],
                        "category": "Code",
                        "difficulty": "Medium"
                    }""";

        Question parsedQuestion = jacksonTester.parse(jsonBody).getObject();

        assertNull(parsedQuestion.getId());
        assertEquals(4, parsedQuestion.getAnswers().size());
        assertEquals(550, parsedQuestion.getApiId());
        assertEquals("Which sign is used to access a variable of  a variable in PHP?", parsedQuestion.getQuestion());
    }
}

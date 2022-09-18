package pl.cyryl.quizapi.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class QuestionJsonTest {

    @Autowired
    private JacksonTester<Question> jacksonTester;

    @Test
    public void testDeserialization() throws Exception {
        String jsonBody = "{\n" +
                "        \"id\": 550,\n" +
                "        \"question\": \"Which sign is used to access a variable of  a variable in PHP?\",\n" +
                "        \"description\": null,\n" +
                "        \"answers\": {\n" +
                "            \"answer_a\": \"$$\",\n" +
                "            \"answer_b\": \"$|\",\n" +
                "            \"answer_c\": \"#@\",\n" +
                "            \"answer_d\": \"$\",\n" +
                "            \"answer_e\": null,\n" +
                "            \"answer_f\": null\n" +
                "        },\n" +
                "        \"multiple_correct_answers\": \"false\",\n" +
                "        \"correct_answers\": {\n" +
                "            \"answer_a_correct\": \"true\",\n" +
                "            \"answer_b_correct\": \"false\",\n" +
                "            \"answer_c_correct\": \"false\",\n" +
                "            \"answer_d_correct\": \"false\",\n" +
                "            \"answer_e_correct\": \"false\",\n" +
                "            \"answer_f_correct\": \"false\"\n" +
                "        },\n" +
                "        \"correct_answer\": \"answer_a\",\n" +
                "        \"explanation\": null,\n" +
                "        \"tip\": null,\n" +
                "        \"tags\": [\n" +
                "            {\n" +
                "                \"name\": \"PHP\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"category\": \"Code\",\n" +
                "        \"difficulty\": \"Medium\"\n" +
                "    }";

        Question parsedQuestion = jacksonTester.parse(jsonBody).getObject();

        assertNull(parsedQuestion.getId());
        assertEquals(4, parsedQuestion.getAnswers().size());
        assertEquals(550, parsedQuestion.getApiId());
        assertEquals("Which sign is used to access a variable of  a variable in PHP?", parsedQuestion.getQuestion());
    }
}

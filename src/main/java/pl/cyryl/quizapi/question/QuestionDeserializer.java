package pl.cyryl.quizapi.question;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.cyryl.quizapi.answer.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDeserializer extends StdDeserializer<Question> {

    public QuestionDeserializer() {
        this(null);//???????????
    }

    protected QuestionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Question deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Question question = new Question();
        question.setApiId(node.get("id").asInt());
        question.setQuestion(node.get("question").asText());
        List<Answer> answers = new ArrayList<>();

        for (JsonNode answerNode : node.get("answers")) {
            String answerText = answerNode.asText();
            if(!answerText.equalsIgnoreCase("null")){
                Answer answer = new Answer();
                answer.setAnswer(answerText);
                answers.add(answer);
            }
        }
        Iterator<JsonNode> correct_answers = node.get("correct_answers").iterator();
        for (Answer answer : answers){
            answer.setCorrect(correct_answers.next().asBoolean());
        }
        question.setAnswers(answers);
        return question;
    }
}

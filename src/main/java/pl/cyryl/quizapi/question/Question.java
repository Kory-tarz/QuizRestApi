package pl.cyryl.quizapi.question;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyryl.quizapi.answer.Answer;
import pl.cyryl.quizapi.model.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
@ToString
@JsonDeserialize(using = QuestionDeserializer.class)
@Entity(name = "questions")
public class Question extends BaseEntity {

    @Column(name = "question")
    private String question;

    @Column(name = "api_id")
    private Integer apiId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

    public boolean verifyAnswers(List<Long> providedAnswers) {
        HashSet<Long> provided = new HashSet<>(providedAnswers);
        for (Answer answer : answers) {
            if (answer.isCorrect() && !provided.contains(answer.getId())) {
                return false;
            } else if (!answer.isCorrect() && provided.contains(answer.getId())) {
                return false;
            }
        }
        return true;
    }
}

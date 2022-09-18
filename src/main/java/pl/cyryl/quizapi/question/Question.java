package pl.cyryl.quizapi.question;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyryl.quizapi.answer.Answer;
import pl.cyryl.quizapi.model.BaseEntity;

import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;
}

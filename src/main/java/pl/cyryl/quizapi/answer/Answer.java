package pl.cyryl.quizapi.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyryl.quizapi.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;


@Getter
@Setter
@ToString
@Entity(name = "answers")
public class Answer extends BaseEntity {

    @JsonIgnore
    @Column(name = "correct")
    private boolean correct;

    @Column(name = "answer")
    private String answer;

}



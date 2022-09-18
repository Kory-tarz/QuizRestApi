package pl.cyryl.quizapi.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QuestionDto {

    private long questionId;
    private List<Long> answers;

}

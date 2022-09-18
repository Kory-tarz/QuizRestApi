package pl.cyryl.quizapi.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QuestionDto {

    private Long questionId;
    private List<Long> answers;

    public boolean isValid() {
        return questionId != null && answers != null && answers.size() > 0;
    }

}

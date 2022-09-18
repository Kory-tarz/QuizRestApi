package pl.cyryl.quizapi.answer;

public class AnswerResponse {

    private boolean correct;

    public AnswerResponse(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }
}

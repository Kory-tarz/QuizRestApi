package pl.cyryl.quizapi.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.cyryl.quizapi.question.Question;
import pl.cyryl.quizapi.question.QuestionService;

import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final QuizRemoteApiService quizRemoteService;
    private final QuestionService questionService;

    private static final int NR_OF_CONNECTIONS = 10;

    public CommandLineRunnerImpl(QuizRemoteApiService quizRemoteService, QuestionService questionService) {
        this.quizRemoteService = quizRemoteService;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= NR_OF_CONNECTIONS; i++) {
            List<Question> questions = quizRemoteService.getQuestions();
            questionService.saveAll(questions);
        }
    }
}

package pl.cyryl.quizapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.cyryl.quizapi.question.Question;
import pl.cyryl.quizapi.question.QuestionService;

import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final QuizRemoteApiService quizRemoteService;
    private final QuestionService questionService;

    public CommandLineRunnerImpl(QuizRemoteApiService quizRemoteService, QuestionService questionService) {
        this.quizRemoteService = quizRemoteService;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Question> questions = quizRemoteService.getQuestions();
        questionService.saveAll(questions);
    }
}

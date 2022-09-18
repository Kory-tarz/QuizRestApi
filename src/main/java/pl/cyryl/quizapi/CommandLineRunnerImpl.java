package pl.cyryl.quizapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final QuizRemoteApiService quizRemoteService;

    public CommandLineRunnerImpl(QuizRemoteApiService quizRemoteService) {
        this.quizRemoteService = quizRemoteService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("commanding");
        quizRemoteService.getQuestions();
        System.out.println("i got something??");
    }
}

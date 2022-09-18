package pl.cyryl.quizapi.question;

import org.springframework.stereotype.Service;
import pl.cyryl.quizapi.answer.Answer;
import pl.cyryl.quizapi.answer.AnswerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveAll(Iterable<Question> questions) {
        questionRepository.saveAll(questions);
    }

    // assuming no missing indexes in db
    public Question getRandomQuestionNoMissingData() {
        long count = questionRepository.count();
        long firstId = questionRepository.findFirstByOrderById().orElseThrow().getId();
        long randomId = new Random().nextLong(firstId, firstId + count);
        return questionRepository.findById(randomId).orElseThrow();
    }

    // no assumption about data in db
    public Question getRandomQuestion() {
        return questionRepository.findRandomQuestion().orElseThrow();
    }

    public Question findById(long id){
        return questionRepository.findById(id).orElseThrow();
    }
}

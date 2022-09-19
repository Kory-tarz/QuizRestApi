package pl.cyryl.quizapi.question;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
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

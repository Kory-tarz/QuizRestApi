package pl.cyryl.quizapi.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import pl.cyryl.quizapi.answer.Answer;
import pl.cyryl.quizapi.question.Question;
import pl.cyryl.quizapi.question.QuestionDeserializer;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class QuizRemoteApiService {

    @Value("${app.quiz.path}")
    private String urlPath;

    private WebClient webClient;

    public QuizRemoteApiService(@Value("${app.quiz.url}") String baseUrl, @Value("${app.quiz.token}") String token, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .filters(exchangeFilterFunctions ->
                        exchangeFilterFunctions.add(logRequest()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Api-Key", token)
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Connecting to: " + clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    public List<Question> getQuestions() {
        Mono<List<Question>> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlPath)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<Question> questions = response.block();
        return questions;
    }

}

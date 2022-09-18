package pl.cyryl.quizapi;

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
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class QuizRemoteApiService {

    @Value("${app.quiz.token}")
    private String token;

    @Value("${app.quiz.path}")
    private String urlPath;

    private WebClient webClient;

    public QuizRemoteApiService(@Value("${app.quiz.url}") String baseUrl, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .filters(exchangeFilterFunctions ->
                    exchangeFilterFunctions.add(logRequest()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Connecting to: " + clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    public Mono<Question> getQuestions() {

        String responseStr = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlPath)
                        .build())
                .headers(headers -> headers.add("X-Api-Key", token))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(responseStr);

        Mono<List<Question>> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlPath)
                        .build())
                .headers(headers -> headers.add("X-Api-Key", token))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Question>>() {});

        List<Question> questions = response.block();

        questions.forEach(System.out::println);

        //List<List<Answer>> answers = questions.stream().map(Question::getAnswers).collect(Collectors.toList());


        return null;
    }

}

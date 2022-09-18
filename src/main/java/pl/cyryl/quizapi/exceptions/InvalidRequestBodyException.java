package pl.cyryl.quizapi.exceptions;

public class InvalidRequestBodyException extends RuntimeException{

    public InvalidRequestBodyException(String message){
        super(message);
    }
}

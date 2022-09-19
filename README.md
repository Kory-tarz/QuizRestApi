# QuizApi

Api udostępnia dwa endpointy:

1. Pobranie losowego pytania - dostępny metodą GET pod adresem /api/questions 
2. Udzielenie odpowiedzi na pytanie - dostępny metodą POST pod adresem /api/answers

format odpowiedzi: 
```json
{
  "answers": [
    1,2
  ],
  "questionId": 1
}
```

Instrukcja uruchomienia lokalnie za pomocą docker-compose

1. Utwórz plik jar wykonując komendę:
```console
mvn clean package
```
2. Stwórz obraz aplikacji (upewnij się że nie istnieje inny obraz pod tą samą nazwą)
```console
docker build -t quizapi .
```
3. Wykonaj komendę
```console
docker-compose up
```
Aplikacja domyślnie domyślnie będzie operować na porcie 8080, a baza danych MySQL na porcie 3307.

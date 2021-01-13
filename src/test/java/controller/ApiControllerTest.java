package controller;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.zaychikov.ibsbackendtest.Application;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class ApiControllerTest {

    public final static Logger LOG = Logger.getLogger(ApiControllerTest.class);
    RestTemplate restTemplate = new RestTemplate();
    String patternURL = "http://localhost:8080/api/documents";

    @Test
    public void testAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Document>> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(patternURL, HttpMethod.GET, requestEntity, String.class);
        Assert.assertNotNull(result);

        //Не знаю почему не приходит 401, но то что возвращается страница логина,
        //говорит о том, что запрос без авторизации перенаправился на форму входа
        //вообще надо довести до ума этот тест
        String waitingResponse = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "    <title>Документоборот</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/login.css\"/>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "    <form action=\"/login\" method=\"POST\" class=\"form-signin\">\n" +
                "        <h3 class=\"form-signin-heading\">Welcome</h3>\n" +
                "        <br/>\n" +
                "        <input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Username\"\n" +
                "               class=\"form-control\"/> <br/>\n" +
                "        <input type=\"password\" placeholder=\"Password\"\n" +
                "               id=\"password\" name=\"password\" class=\"form-control\"/> <br/>\n" +
                "        \n" +
                "        <button class=\"btn btn-lg btn-primary btn-block\" name=\"Submit\" value=\"Login\" type=\"Submit\">Login</button>\n" +
                "    </form><br/>\n" +
                "    <form action=\"/registration\" method=\"get\">\n" +
                "        <button class=\"btn btn-md btn-warning btn-block\" type=\"Submit\">Go To Registration Page</button>\n" +
                "    </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на отказ доступа без авторизации пройден");
    }

    @Test
    public void testGetAdminDocumentList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", "123123");
        HttpEntity<List<Document>> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(patternURL, HttpMethod.GET, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "[{\"id\":1,\"name\":\"Договор\",\"number\":\"РК/01\",\"creator\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"secondSide\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signatures\":[{\"id\":1,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":true},{\"id\":2,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":true}]},{\"id\":2,\"name\":\"Соглашение\",\"number\":\"РК/01-С1\",\"creator\":{\"id\":3,\"name\":\"Кошкин дом\"},\"secondSide\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signatures\":[{\"id\":3,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":true},{\"id\":4,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":false}]},{\"id\":3,\"name\":\"Спецификация\",\"number\":\"РК/01-01\",\"creator\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"secondSide\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signatures\":[{\"id\":5,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":false},{\"id\":6,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":false}]},{\"id\":4,\"name\":\"Спецификация\",\"number\":\"РК/01-02\",\"creator\":{\"id\":3,\"name\":\"Кошкин дом\"},\"secondSide\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signatures\":[{\"id\":7,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":false},{\"id\":8,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":true}]}]";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на права админа пройден");
    }

    @Test
    public void testGetUserDocumentList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        HttpEntity<List<Document>> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange(patternURL, HttpMethod.GET, requestEntity, String.class);

        Assert.assertNotNull(result);

        String waitingResponse = "[{\"id\":1,\"name\":\"Договор\",\"number\":\"РК/01\",\"creator\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"secondSide\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signatures\":[{\"id\":1,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":true},{\"id\":2,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":true}]},{\"id\":2,\"name\":\"Соглашение\",\"number\":\"РК/01-С1\",\"creator\":{\"id\":3,\"name\":\"Кошкин дом\"},\"secondSide\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signatures\":[{\"id\":3,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":true},{\"id\":4,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":false}]},{\"id\":3,\"name\":\"Спецификация\",\"number\":\"РК/01-01\",\"creator\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"secondSide\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signatures\":[{\"id\":5,\"user\":{\"id\":2,\"name\":\"Рога и Копыта\"},\"signature\":false},{\"id\":6,\"user\":{\"id\":3,\"name\":\"Кошкин дом\"},\"signature\":false}]}]";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на получение списка документов пользователя пройден");
    }

    @Test
    public void testCreateDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document("Договор", "Test", new User("Рога и Копыта"), new User("Кошкин дом"));
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = restTemplate.exchange(patternURL, HttpMethod.POST, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "{ \"result\":\"success\" }";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на создание документа пройден");
    }

    @Test
    public void testCreateDocumentError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document("Договор", "РК/01", new User("Рога и Копыта"), new User("Кошкин дом"));
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = consumeWebService(patternURL, HttpMethod.POST, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "\"status\":\"404\",\"error\":\"Not found\",\"message\":\"Документ уже существует в системе! Создание дубликата невозможно!\"}";
        Assert.assertTrue(result.getBody().contains(waitingResponse));
        LOG.info("Тест на запрет создания дубликата документа пройден");
    }

    @Test
    public void testSignDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(4, "Договор", "Test");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = restTemplate.exchange(patternURL + "/signdoc", HttpMethod.POST, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "{ \"result\":\"success\" }";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на подпись документа пройден");
    }

    @Test
    public void testSignDocumentError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(1, "Договор", "РК/01");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = consumeWebService(patternURL + "/signdoc", HttpMethod.POST, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "\"status\":\"404\",\"error\":\"Not found\",\"message\":\"Документ уже подписан, нельзя подписать документ повторно!!!\"}";
        Assert.assertTrue(result.getBody().contains(waitingResponse));
        LOG.info("Тест на запрет повторной подписи документа пройден");
    }

    @Test
    public void testDeleteDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(3, "Договор", "Test");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = restTemplate.exchange(patternURL + "/delete", HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "{ \"result\":\"success\" }";
        Assert.assertEquals(waitingResponse, result.getBody());
        LOG.info("Тест на удаление документа пройден");
    }

    @Test
    public void testDeleteSignedDocumentError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(1, "Договор", "РК/01");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = consumeWebService(patternURL + "/delete", HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "\"status\":\"404\",\"error\":\"Not found\",\"message\":\"Документ подписан одной из сторон, удаление невозможно!\"}";
        Assert.assertTrue(result.getBody().contains(waitingResponse));
        LOG.info("Тест на запрет удаления подписанного документа пройден");
    }

    @Test
    public void testDeleteOtherUserDocumentError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(4, "Спецификация", "РК/02");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = consumeWebService(patternURL + "/delete", HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "\"status\":\"404\",\"error\":\"Not found\",\"message\":\"Удалить документ может только создатель!\"}";
        Assert.assertTrue(result.getBody().contains(waitingResponse));
        LOG.info("Тест на запрет удаления подписанного документа пройден");
    }

    @Test
    public void testDeleteNotExistDocumentError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("roga", "123123");
        Document body = new Document(99, "Спецификация", "РК/02");
        HttpEntity<Document> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = consumeWebService(patternURL + "/delete", HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertNotNull(result);
        String waitingResponse = "\"status\":\"404\",\"error\":\"Not found\",\"message\":\"Документ' Спецификация №РК/02' не существует! Проверьте правильность ввода данных\"}";
        Assert.assertTrue(result.getBody().contains(waitingResponse));
        LOG.info("Тест на попытку удаления несуществующего документа пройден");
    }


    <T> ResponseEntity consumeWebService(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        try {
            return restTemplate.exchange(url, method, requestEntity, responseType);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

}

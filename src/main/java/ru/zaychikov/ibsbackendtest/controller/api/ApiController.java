package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.http.ResponseEntity;
import ru.zaychikov.ibsbackendtest.domain.Document;

import java.security.Principal;
import java.util.List;

public interface ApiController {

    ResponseEntity<List<Document>> getAllDocuments(Principal principal);

    ResponseEntity<String> createDocument(Document document, Principal principal);

    ResponseEntity<String> signDocument(Document document, Principal principal);

    ResponseEntity<String> deleteDocument(Document document, Principal principal);
}

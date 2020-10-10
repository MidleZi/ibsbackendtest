package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.ui.Model;
import ru.zaychikov.ibsbackendtest.domain.Document;

import java.security.Principal;
import java.util.List;

public interface ApiController {

    List<Document> getAllDocuments(Principal principal);

    String createDocument(Document document, Principal principal);

    String signDocument(Document document, Principal principal);

    String deleteDocument(Document document, Principal principal);
}

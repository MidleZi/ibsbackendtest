package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.ui.Model;
import ru.zaychikov.ibsbackendtest.domain.Document;

import java.util.List;

public interface ApiController {

    List<Document> getAllDocuments();

    String createDocument(Document document);

    String signDocument(int userId, Document document);

    String deleteDocument(Document document);
}

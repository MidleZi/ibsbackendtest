package ru.zaychikov.ibsbackendtest.controller.web;

import org.springframework.ui.Model;
import ru.zaychikov.ibsbackendtest.domain.Document;

public interface DocumentController {

    String getAllDocuments(Model model);

    String createDocument(Document document);

    String signDocument(Document document);

    String deleteDocument(Document document);
}

package ru.zaychikov.ibsbackendtest.service.document;

import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

public interface DocumentService {

    Document getDocumentById(int id);
    boolean documentIsExist(User user, Document document);
    List<Document> getAllDocuments(User user);
    void createDocument(User user, Document document);
    void signDocument(User user, Document document);
    void deleteDocument(User user, Document document);


}

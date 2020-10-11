package ru.zaychikov.ibsbackendtest.dao.document;

import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

public interface DocumentDAO {

    List<Document> getAllDocuments(User user);
    Document getDocumentById(int id);
    void saveDocument(Document document);
    void deleteDocument(Document document);
}

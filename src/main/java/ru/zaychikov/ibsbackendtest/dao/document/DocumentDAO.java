package ru.zaychikov.ibsbackendtest.dao.document;

import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

public interface DocumentDAO {

    List<Document> getAllDocuments(User user);
    Document getDocumentById(int id);
    boolean documentIsExist(Document document);
    boolean createDocument(User user, Document document);
    boolean signDocument(User user, Document document);
    boolean deleteDocument(User user, Document document);
}

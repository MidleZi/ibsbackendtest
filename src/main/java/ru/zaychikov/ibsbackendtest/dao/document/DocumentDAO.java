package ru.zaychikov.ibsbackendtest.dao.document;

import ru.zaychikov.ibsbackendtest.domain.Document;

import java.util.List;

public interface DocumentDAO {

    List<Document> getAllDocuments();
    Document getDocumentById(int id);
    boolean createDocument(Document document);
    boolean updateDocument(Document document);
    boolean deleteDocument(Document document);
}

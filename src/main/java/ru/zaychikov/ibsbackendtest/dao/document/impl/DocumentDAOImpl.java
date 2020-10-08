package ru.zaychikov.ibsbackendtest.dao.document.impl;

import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentDAOImpl implements DocumentDAO {

    private DocumentRepository documentRepository;

    //тут спринг сам инжектит зависимость через конструктор
    public DocumentDAOImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        return documents;
    }

    @Override
    public Document getDocumentById(int id) {
        return null;
    }

    @Override
    public boolean createDocument(Document document) {
        documentRepository.save(document);
        return true;
    }


    @Override
    public boolean updateDocument(Document document) {
        return false;
    }

    @Override
    public boolean deleteDocument(Document document) {
        if(document.getSignatures().get(0).isSignature() || document.getSignatures().get(1).isSignature()) {
            System.out.println("Документ подписан одной из сторон, удаление невозможно!");
            return true;
        } else {
            documentRepository.delete(document);
            System.out.println("Документ удален");
            return false;
        }
    }
}

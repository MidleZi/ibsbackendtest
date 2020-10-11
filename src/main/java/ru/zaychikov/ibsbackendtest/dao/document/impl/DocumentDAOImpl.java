package ru.zaychikov.ibsbackendtest.dao.document.impl;

import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.repository.DocumentRepository;
import ru.zaychikov.ibsbackendtest.repository.RoleRepository;
import ru.zaychikov.ibsbackendtest.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentDAOImpl implements DocumentDAO {

    private final DocumentRepository documentRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    //тут спринг сам инжектит зависимость через конструктор
    public DocumentDAOImpl(DocumentRepository documentRepository, RoleRepository roleRepository, UserService userService) {
        this.documentRepository = documentRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public Document getDocumentById(int id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public List<Document> getAllDocuments(User user) {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        return documents;
    }


    @Override
    public void saveDocument(Document document) {
        documentRepository.save(document);
    }


    @Override
    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }
}

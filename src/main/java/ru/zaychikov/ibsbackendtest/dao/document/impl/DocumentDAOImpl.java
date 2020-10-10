package ru.zaychikov.ibsbackendtest.dao.document.impl;

import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.Signature;
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

    /**
     * Метод возвращает документ по id
     *
     * @param id id документа
     * @return доаумент
     */
    @Override
    public Document getDocumentById(int id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public boolean documentIsExist(Document document) {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        return documents.contains(document);
    }

    /**
     * Метод возвращает список документов, которые пользователь создал
     * и которые ему направили на подпись
     *
     * @param user - пользователь вызвавший метод
     * @return - статус операции
     */
    @Override
    public List<Document> getAllDocuments(User user) {
        List<Document> documents = new ArrayList<>();
        List<Document> documentsForUser = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        if (user.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            return documents;
        } else {
            for (Document document : documents) {
                if ((document.getCreator().getId() == user.getId()) || document.getSignatures().get(0).isSignature()) {
                    documentsForUser.add(document);
                }
            }
        }
        return documentsForUser;
    }


    @Override
    public boolean createDocument(User user, Document document) {
        document.setCreator(userService.findUserByUsername(user.getUsername()));
        document.setSecondSide(userService.findUserByName(document.getSecondSide().getName()));
        List<Signature> signatures = new ArrayList<>();
        signatures.add(new Signature(user, document));
        signatures.add(new Signature(userService.findUserByName(document.getSecondSide().getName()), document));
        document.setSignatures(signatures);
        if (!documentIsExist(document)) {
            documentRepository.save(document);
            System.out.println("Документ заведен");
        } else {
            System.out.println("Документ уже существует!");
        }
        return true;
    }

    /**
     * Метод подписывает документ пользователя.
     *
     * @param user     - пользователь, подписывающий документ
     * @param document - подписываемый документ
     * @return - статус операции
     */
    @Override
    public boolean signDocument(User user, Document document) {
        document = getDocumentById(document.getId());
        List<Signature> signs = document.getSignatures();
        for (Signature sign : signs) {
            if (sign.getUser().getId() == user.getId()) {
                if (!sign.isSignature()) {
                    sign.setSignature(true);
                    documentRepository.save(document);
                    System.out.println("Документ подписан");
                    return true;
                } else {
                    System.out.println("Документ уже подписан!!!");
                }
            }
        }
        return false;
    }


    /**
     * Метод, удаляющий документ.
     * Документ может быть удален только создателем или администратором
     *
     * @param user     пользователь, удаляющий документ
     * @param document - удаляемый документ
     * @return - статус операции
     */
    @Override
    public boolean deleteDocument(User user, Document document) {
        document = getDocumentById(document.getId());
        if (document.getCreator().getId() == user.getId() || user.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            if (document.getSignatures().get(0).isSignature() || document.getSignatures().get(1).isSignature()) {
                System.out.println("Документ подписан одной из сторон, удаление невозможно!");
                return true;
            } else {
                documentRepository.delete(document);
                System.out.println("Документ удален");
                return false;
            }
        } else {
            System.out.println("Удалить документ может только создатель! Ну или админ");
            return false;
        }
    }
}

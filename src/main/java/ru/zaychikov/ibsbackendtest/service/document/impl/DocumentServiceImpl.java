package ru.zaychikov.ibsbackendtest.service.document.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.Signature;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.exceptions.document.DocumentDeleteException;
import ru.zaychikov.ibsbackendtest.exceptions.document.DocumentNotFoundException;
import ru.zaychikov.ibsbackendtest.exceptions.document.DocumentSaveException;
import ru.zaychikov.ibsbackendtest.exceptions.document.DocumentSignException;
import ru.zaychikov.ibsbackendtest.service.document.DocumentService;
import ru.zaychikov.ibsbackendtest.service.role.RoleService;
import ru.zaychikov.ibsbackendtest.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DocumentServiceImpl implements DocumentService {

    public final static Logger LOG = Logger.getLogger(DocumentService.class);
    private final DocumentDAO documentDAO;
    private final UserService userService;
    private final RoleService roleService;

    public DocumentServiceImpl(DocumentDAO documentDAO, UserService userService, RoleService roleService) {
        this.documentDAO = documentDAO;
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Метод возвращает документ по id
     *
     * @param id id документа
     * @return документ
     */
    @Override
    public Document getDocumentById(int id) {
        return documentDAO.getDocumentById(id);
    }

    /**
     * Метод проверяет наличие документа в бд
     *
     * @param user     пользователь вызвавший операцию
     * @param document проверяемый документ
     * @return существует или нет
     */
    @Override
    public boolean documentIsExist(User user, Document document) {
        return getAllDocuments(user).contains(document);
    }

    /**
     * Метод возвращает список документов, которые пользователь создал
     * и которые ему направили на подпись
     *
     * @param user - пользователь вызвавший метод
     * @return - список документов пользователя
     */
    @Override
    public List<Document> getAllDocuments(User user) {
        List<Document> documents = documentDAO.getAllDocuments(user);
        List<Document> documentsForUser = new ArrayList<>();
        if (user.getRoles().contains(roleService.findByRole("ADMIN"))) {
            return documents;
        } else {
            for (Document document : documents) {
                if ((document.getCreator().getId() == user.getId()) || document.getSignatures().get(0).isSignature()) {
                    documentsForUser.add(document);
                }
            }
            return documentsForUser;
        }
    }

    @Override
    public void createDocument(User user, Document document) {
        if(!user.getRoles().contains(roleService.findByRole("ADMIN"))) {
            document.setCreator(userService.findUserByUsername(user.getUsername()));
            document.setSecondSide(userService.findUserByName(document.getSecondSide().getName()));
            List<Signature> signatures = new ArrayList<>();
            signatures.add(new Signature(user, document));
            signatures.add(new Signature(userService.findUserByName(document.getSecondSide().getName()), document));
            document.setSignatures(signatures);
            if (!documentIsExist(user, document)) {
                documentDAO.saveDocument(document);
                LOG.info("Документ '" + document.getName() + " №" + document.getNumber() + "' заведен");
            } else {
                throw new DocumentSaveException("Документ уже существует в системе! Создание дубликата невозможно!");
            }
        } else {
            throw new DocumentSaveException("Пользователь с ролью 'Администратор' не может создать документ");
        }
    }

    /**
     * Метод подписывает документ пользователя.
     *
     * @param user     - пользователь, подписывающий документ
     * @param document - подписываемый документ
     * @return - статус операции
     */
    @Override
    public void signDocument(User user, Document document) {
        if(!user.getRoles().contains(roleService.findByRole("ADMIN"))) {
            try {
                document = getDocumentById(document.getId());
                List<Signature> signs = document.getSignatures();
                for (Signature sign : signs) {
                    if (sign.getUser().getId() == user.getId()) {
                        if (!sign.isSignature()) {
                            sign.setSignature(true);
                            documentDAO.saveDocument(document);
                            LOG.info("Документ '" + document.getName() + " №" + document.getNumber() + "' подписан");
                            break;
                        } else {
                            throw new DocumentSignException("Документ уже подписан, нельзя подписать документ повторно!!!");
                        }
                    }
                }
            } catch (NoSuchElementException ex) {
                throw new DocumentNotFoundException("Документ не существует! Проверьте правильность ввода данных");
            }
        } else {
            throw new DocumentSignException("Пользователь с ролью 'Администратор' не может подписать документ");
        }
    }

    /**
     * Метод, удаляющий документ.
     * Документ может быть удален только создателем или администратором
     *
     * @param user     пользователь, удаляющий документ
     * @param document - удаляемый документ
     */
    @Override
    public void deleteDocument(User user, Document document) {
        try {
            document = getDocumentById(document.getId());
            if (document.getCreator().getId() == user.getId() || user.getRoles().contains(roleService.findByRole("ADMIN"))) {
                if (document.getSignatures().get(0).isSignature() || document.getSignatures().get(1).isSignature()) {
                    throw new DocumentDeleteException("Документ подписан одной из сторон, удаление невозможно!");
                } else {
                    documentDAO.deleteDocument(document);
                    LOG.info("Документ '" + document.getName() + " №" + document.getNumber() + "' удален");
                }
            } else {
                throw new DocumentDeleteException("Удалить документ может только создатель!");
            }
        } catch (NoSuchElementException ex) {
            throw new DocumentNotFoundException("Документ' " + document.getName() + " №" + document.getNumber() + "' не существует! Проверьте правильность ввода данных");
        }
    }
}

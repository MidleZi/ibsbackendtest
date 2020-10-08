package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.web.bind.annotation.*;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;

import java.util.List;

@RestController
@RequestMapping("api/documents")
public class ApiControllerImpl implements ApiController {

    private final DocumentDAO documentDAO;

    public ApiControllerImpl(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentDAO.getAllDocuments();
    }

    @PostMapping
    public String createDocument(@RequestBody Document document) {
        document.getSignatures().get(0).setDocument(document);
        document.getSignatures().get(1).setDocument(document);
        document.getSignatures().get(0).setSignature(false);
        document.getSignatures().get(1).setSignature(false);
        documentDAO.createDocument(document);
        return "Success";
    }

    @PostMapping("/signdoc/{id}")
    public String signDocument(@PathVariable int userId, Document document) {
        String result;
        if(userId == document.getCreator().getId()) {
            if(!document.getSignatures().get(0).isSignature()) {
                document.getSignatures().get(0).setSignature(false);
                documentDAO.updateDocument(document);
                result = "Success";
            } else {
                result = "Документ уже подписан";
            }
        }
        return null;
    }

    @DeleteMapping("/delete")
    public String deleteDocument(Document document) {
        documentDAO.deleteDocument(document);
        return "success";
    }


}

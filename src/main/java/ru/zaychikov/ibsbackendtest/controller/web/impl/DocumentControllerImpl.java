package ru.zaychikov.ibsbackendtest.controller.web.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zaychikov.ibsbackendtest.controller.web.DocumentController;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.dao.user.UserDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

@Controller
@RequestMapping("/documents")
public class DocumentControllerImpl implements DocumentController {

    private final DocumentDAO documentDAO;
    private final UserDAO userDAO;

    public DocumentControllerImpl(DocumentDAO documentDAO, UserDAO userDAO) {
        this.documentDAO = documentDAO;
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String getAllDocuments(Model model, Authentication authentication) {
        User user = userDAO.findUserByUsername(authentication.getName());
        List<Document> documents = documentDAO.getAllDocuments(user);
        model.addAttribute("documents", documents);
        return "view/alldocuments";
    }

    @GetMapping("/new")
    public String getNewDocumentForm(@ModelAttribute("document") Document document) {
        return "/view/new";
    }

    @PostMapping()
    public String createDocument(@ModelAttribute("document") Document document) {
        //documentDAO.createDocument(document);
        return "redirect:/documents";
    }

    @Override
    public String signDocument(Document document) {
        document.getSignatures().get(0).setSignature(false);
        //documentDAO.signDocument(document);
        return "redirect:/documents";
    }

    @PostMapping("/delete")
    public String deleteDocument(@ModelAttribute("document") Document document) {
        //documentDAO.deleteDocument(document);
        return "redirect:/documents";

    }
}

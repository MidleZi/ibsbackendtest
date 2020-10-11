package ru.zaychikov.ibsbackendtest.controller.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zaychikov.ibsbackendtest.controller.web.DocumentController;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;

@Controller
@RequestMapping("/documents")
public class DocumentControllerImpl implements DocumentController {

    private final DocumentDAO documentDAO;

    //тут спринг сам инжектит зависимость через конструктор
    public DocumentControllerImpl(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @GetMapping() //работает
    public String getAllDocuments(Model model) {
        //List<Document> documents = documentDAO.getAllDocuments();
        //model.addAttribute("documents", documents);
        return "view/alldocuments";
    }

    @GetMapping("/new") //работает
    public String getNewPlanetForm(@ModelAttribute("document") Document document) {
        return "/view/new";
    }

    @PostMapping() //работает
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

    @PostMapping("/delete") //работает
    public String deleteDocument(@ModelAttribute("document") Document document) {
        //documentDAO.deleteDocument(document);
        return "redirect:/documents";

    }
}

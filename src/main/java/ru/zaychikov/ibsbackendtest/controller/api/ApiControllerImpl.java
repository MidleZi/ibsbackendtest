package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.web.bind.annotation.*;
import ru.zaychikov.ibsbackendtest.dao.document.DocumentDAO;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.service.user.UserService;
import ru.zaychikov.ibsbackendtest.service.user.impl.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/documents")
public class ApiControllerImpl implements ApiController {

    private final DocumentDAO documentDAO;
    private final UserService userService;

    public ApiControllerImpl(DocumentDAO documentDAO, UserService userService) {
        this.documentDAO = documentDAO;
        this.userService = userService;
    }

    @GetMapping
    public List<Document> getAllDocuments(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return documentDAO.getAllDocuments(user);
    }

    @PostMapping
    public String createDocument(@RequestBody Document document, Principal principal) {
        documentDAO.createDocument(userService.findUserByUsername(principal.getName()), document);
        return "Success";
    }

    @PostMapping("/signdoc")
    public String signDocument(@RequestBody Document document, Principal principal) {
        String result;
        User user = userService.findUserByUsername(principal.getName());
        documentDAO.signDocument(user, document);
        return null;
    }

    @DeleteMapping("/delete")
    public String deleteDocument(@RequestBody Document document, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        documentDAO.deleteDocument(user, document);
        return "success";
    }


}

package ru.zaychikov.ibsbackendtest.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.service.document.DocumentService;
import ru.zaychikov.ibsbackendtest.service.user.UserService;
import ru.zaychikov.ibsbackendtest.util.Constant;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/documents")
public class ApiControllerImpl implements ApiController {

    private final DocumentService documentService;
    private final UserService userService;

    public ApiControllerImpl(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return new ResponseEntity<>(documentService.getAllDocuments(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createDocument(@RequestBody Document document, Principal principal) {
        documentService.createDocument(userService.findUserByUsername(principal.getName()), document);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(Constant.CONTENT_TYPE_HEADER, Constant.APPLICATION_JSON);
        return new ResponseEntity<>(Constant.SUCCESS, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/signdoc")
    public ResponseEntity<String> signDocument(@RequestBody Document document, Principal principal) {
        documentService.signDocument(userService.findUserByUsername(principal.getName()), document);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(Constant.CONTENT_TYPE_HEADER, Constant.APPLICATION_JSON);
        return new ResponseEntity<>(Constant.SUCCESS, responseHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocument(@RequestBody Document document, Principal principal) {
        documentService.deleteDocument(userService.findUserByUsername(principal.getName()), document);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(Constant.CONTENT_TYPE_HEADER, Constant.APPLICATION_JSON);
        return new ResponseEntity<>(Constant.SUCCESS, responseHeaders, HttpStatus.OK);
    }


}

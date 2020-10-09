package ru.zaychikov.ibsbackendtest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zaychikov.ibsbackendtest.domain.Document;
import ru.zaychikov.ibsbackendtest.domain.User;

public interface DocumentRepository extends CrudRepository<Document, Integer> {

}

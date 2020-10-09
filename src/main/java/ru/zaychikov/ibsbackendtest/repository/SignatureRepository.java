package ru.zaychikov.ibsbackendtest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zaychikov.ibsbackendtest.domain.Signature;

public interface SignatureRepository extends CrudRepository<Signature, Integer> {
}

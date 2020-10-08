package ru.zaychikov.ibsbackendtest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zaychikov.ibsbackendtest.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}

package ru.zaychikov.ibsbackendtest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zaychikov.ibsbackendtest.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    @Query(value = "select * from users where name = ?1", nativeQuery = true)
    User findUserByName(String name);
}

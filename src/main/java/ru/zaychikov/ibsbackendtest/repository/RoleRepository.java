package ru.zaychikov.ibsbackendtest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zaychikov.ibsbackendtest.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}

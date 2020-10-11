package ru.zaychikov.ibsbackendtest.dao.role;

import ru.zaychikov.ibsbackendtest.domain.Role;

public interface RoleDAO {

    Role findByRole(String role);
}

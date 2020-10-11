package ru.zaychikov.ibsbackendtest.service.role;

import ru.zaychikov.ibsbackendtest.domain.Role;

public interface RoleService {

    Role findByRole(String role);
}

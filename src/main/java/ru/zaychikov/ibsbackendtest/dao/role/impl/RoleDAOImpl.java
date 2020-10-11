package ru.zaychikov.ibsbackendtest.dao.role.impl;

import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.role.RoleDAO;
import ru.zaychikov.ibsbackendtest.domain.Role;
import ru.zaychikov.ibsbackendtest.repository.RoleRepository;

@Component
public class RoleDAOImpl implements RoleDAO {

    private final RoleRepository roleRepository;

    public RoleDAOImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}

package ru.zaychikov.ibsbackendtest.service.role.impl;

import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.role.RoleDAO;
import ru.zaychikov.ibsbackendtest.domain.Role;
import ru.zaychikov.ibsbackendtest.exceptions.RoleNotFoundException;
import ru.zaychikov.ibsbackendtest.service.role.RoleService;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Role findByRole(String roleName) {
        Role role = roleDAO.findByRole(roleName);
        if(role != null) {
            return role;
        } else {
            throw new RoleNotFoundException("Роль не найдена, обратитесь к администратору системы");
        }
    }
}

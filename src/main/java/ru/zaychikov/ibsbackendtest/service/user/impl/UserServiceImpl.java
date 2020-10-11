package ru.zaychikov.ibsbackendtest.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.dao.user.UserDAO;
import ru.zaychikov.ibsbackendtest.domain.Role;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.exceptions.user.UserNotFoundException;
import ru.zaychikov.ibsbackendtest.service.role.RoleService;
import ru.zaychikov.ibsbackendtest.service.user.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userDAO.findUserByUsername(username);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }
    }

    @Override
    public User findUserByName(String name) {
        User user = userDAO.findUserByName(name);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }
    }


    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleService.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDAO.saveUser(user);
    }



}

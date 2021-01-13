package ru.zaychikov.ibsbackendtest.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zaychikov.ibsbackendtest.dao.user.UserDAO;
import ru.zaychikov.ibsbackendtest.domain.Role;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.exceptions.user.UserNotFoundException;
import ru.zaychikov.ibsbackendtest.service.role.RoleService;
import ru.zaychikov.ibsbackendtest.service.user.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service
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
        return checkUser(user);
    }

    @Override
    public User findUserByName(String name) {
        User user = userDAO.findUserByName(name);
        return checkUser(user);
    }


    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleService.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDAO.saveUser(user);
    }

    private User checkUser(User user) {
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }
    }
}

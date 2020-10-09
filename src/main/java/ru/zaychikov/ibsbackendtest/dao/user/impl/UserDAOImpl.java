package ru.zaychikov.ibsbackendtest.dao.user.impl;

import ru.zaychikov.ibsbackendtest.dao.user.UserDAO;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}

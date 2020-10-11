package ru.zaychikov.ibsbackendtest.dao.user;

import ru.zaychikov.ibsbackendtest.domain.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUser();
    User findUserByUsername(String username);
    User findUserByName(String name);
    void saveUser(User user);
}

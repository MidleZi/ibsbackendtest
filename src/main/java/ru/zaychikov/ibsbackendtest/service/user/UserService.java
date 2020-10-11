package ru.zaychikov.ibsbackendtest.service.user;

import ru.zaychikov.ibsbackendtest.domain.User;

public interface UserService {

    User findUserByUsername(String username);
    User findUserByName(String name);
    void saveUser(User user);
}

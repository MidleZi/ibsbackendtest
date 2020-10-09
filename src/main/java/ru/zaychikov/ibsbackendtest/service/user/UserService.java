package ru.zaychikov.ibsbackendtest.service.user;

import ru.zaychikov.ibsbackendtest.domain.User;

public interface UserService {

    User findUserByUsername(String username);
    User saveUser(User user);
}

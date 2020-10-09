package ru.zaychikov.ibsbackendtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.zaychikov.ibsbackendtest.domain.Role;
import ru.zaychikov.ibsbackendtest.domain.User;
import ru.zaychikov.ibsbackendtest.repository.RoleRepository;
import ru.zaychikov.ibsbackendtest.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class UserServiceImpl {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //@Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //@Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}

package org.example.Service;

import org.example.Entity.User;

public interface UserService {

    void save(User user) throws Exception;

    User login(User user) throws Exception;
}

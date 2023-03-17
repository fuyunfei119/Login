package org.example.Service;

import org.example.Entity.User;
import org.example.Repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepositry userRepositry;

    @Override
    public void save(User user) throws Exception {
        if (userRepositry.findByUserName(user.getUsername()) != null) throw new Exception("This Username has been registed.");

        user.setId(UUID.randomUUID().toString());
        user.setStatus("Active");
        user.setRegisterTime(new Date());
        userRepositry.save(user);
    }

    @Override
    public User login(User user) throws Exception {
        User userName = userRepositry.findByUserName(user.getUsername());
        if (userName==null) throw new Exception("Incorrect Username");
        if (!userName.getPassword().equals(user.getPassword())) throw new Exception("Incorrect Password");
        return userName;
    }

}

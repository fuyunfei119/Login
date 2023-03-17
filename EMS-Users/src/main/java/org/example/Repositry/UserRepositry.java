package org.example.Repositry;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.Entity.User;

@Mapper
public interface UserRepositry {

    String insertWithAllParamters = "INSERT INTO User VALUES (#{id},#{username},#{realname},#{password},#{sex},#{status},#{registerTime})";
    String findByUserName = "SELECT * FROM User WHERE username=#{username}";

    @Insert(insertWithAllParamters)
    void save(User user);

    @Select(findByUserName)
    User findByUserName(String username);
}

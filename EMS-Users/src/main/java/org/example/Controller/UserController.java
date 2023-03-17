package org.example.Controller;

import org.example.Entity.User;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;


    @GetMapping("/user/getVerifyCode")
    public Map<String,String> getVerifyCode() {
        Map<String,String> map = new HashMap<>();

        // create random verify code
        String verifyCode = UUID.randomUUID().toString();

        // save created verify code in redis cache
        stringRedisTemplate.opsForValue().set("code", verifyCode,60, TimeUnit.SECONDS);

        //return verify code
        map.put("code",verifyCode);
        return map;
    }

    @PostMapping("/user/register/{codekey}")
    public Map<String,Object> register(@RequestBody User user,@PathVariable String codekey) throws Exception {
        Map<String,Object> map = new HashMap<>();
        try {

            // check if from user created random verify code has been expired
            if (!(stringRedisTemplate.hasKey("code"))) {
                throw new Exception("Incorrect Verify Code");
            }

            // get in redis cache storaged verify code to compare with the code user sended
            String oldCode = stringRedisTemplate.opsForValue().get("code");

            if (oldCode.equalsIgnoreCase(codekey)) {

                userService.save(user);
                map.put("message","Succesfully Registered");
                map.put("state",true);
            }else {
                throw new Exception("Incorrect Verify Code");

            }
        }catch (Exception exception) {
            map.put("message",exception.getMessage());
            map.put("state",false);
        }
        return map;
    }

    @PostMapping("/user/login")
    public Map<String,Object> login(@RequestBody User user) throws Exception {
        Map<String,Object> map = new HashMap<>();

        try {
            User login = userService.login(user);

            map.put("message","Succesfully Login");
            map.put("state",true);
            map.put("user",login);
        }catch (Exception exception) {
            map.put("message",exception.getMessage());
            map.put("state",false);
        }

        return map;
    }

}

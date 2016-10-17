package com.dj.controller;

import com.dj.model.CustomResponse;
import com.dj.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Darshan on 10/15/2016.
 */
@RestController
@RequestMapping("login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
//    public CustomResponse login(@RequestParam(value = "username", required = false) String username, @RequestParam(value="password", required = false) String password, @RequestBody LoginUser loginuser) {
    public CustomResponse login(@RequestBody LoginUser loginuser) {
        logger.info("Login request received for user: {}", new Object[]{loginuser});
        return new CustomResponse(true);
    }
}

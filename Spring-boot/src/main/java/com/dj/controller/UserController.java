package com.dj.controller;

import com.dj.model.CustomResponse;
import com.dj.model.User;
import com.dj.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Darshan on 10/15/2016.
 */
@RestController
@RequestMapping("users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserRepository userRepo;

    @RequestMapping(value="/", method = RequestMethod.GET)
    @ResponseBody
    public Collection<User> getAllUsers() {
        logger.info("Requesting page: {} of size: {}");
        return userRepo.findAll();
    }

    @RequestMapping(value="/paged", method = RequestMethod.GET)
    @ResponseBody
    public Page<User> getAllUsersByPage(
            @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
    ) {
        logger.info("Requesting page: {} of size: {}", page, size);
        if (page == null) page = 0;
        if (size == null) size = 5;

        int start = page * size;
        int end = (page + 1) * size;
        logger.info("Retrieving records start: {} and end: {}", start, end);
        AtomicInteger index = new AtomicInteger();
        List<User> users = userRepo.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .filter(user -> {
                    return index.incrementAndGet() > start && index.get() <= end;
                })
                .collect(Collectors.toList());
        return new PageImpl<User>(users, new PageRequest(page, size), userRepo.findAll().size());
    }

    @RequestMapping(value="/adduser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public CustomResponse addUser(User user) {
        logger.info("Adding user: {}", user);
        userRepo.addUser(user);
        return new CustomResponse(true);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User findUserById(@PathVariable("id") int id) {
        logger.info("Retrieving user: {}", id);
        return userRepo.findOne(id);
    }

    @RequestMapping(value="/loadusers", method = RequestMethod.GET)
    @ResponseBody
    public String testLoadAllUsers() throws Exception {
        logger.info("Load 31 users for testing");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 31; i++) {
            userRepo.addUser(new User((i+1), "User_" + (i+1), sdf.parse("25/12/200" + i), ""));
        }
//        userRepo.addUser(new User(1, "Dj", sdf.parse("25/12/2000")));
//        userRepo.addUser(new User(2, "Sim", sdf.parse("30/1/2001")));
        return "User loading done";
    }
}

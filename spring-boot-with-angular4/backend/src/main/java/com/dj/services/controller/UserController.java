package com.dj.services.controller;

import com.dj.services.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 6/23/2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User(0, "Zero"));
		return users;
	}
}

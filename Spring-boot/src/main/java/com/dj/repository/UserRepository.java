package com.dj.repository;

import com.dj.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darshan on 10/15/2016.
 */
@Repository
public class UserRepository {
    private Map<Integer, User> users = new HashMap<Integer, User>();

    public void addUser(User user) {
        users.put(user.getId(), user);
    }
    public User deleteUser(int id) {
        return users.remove(id);
    }
    public void updateUser(User user) {
        addUser(user);
    }
    public Collection<User> findAll() {
        return users.values();
    }
    public User findOne(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new RuntimeException("User not found");
    }
}

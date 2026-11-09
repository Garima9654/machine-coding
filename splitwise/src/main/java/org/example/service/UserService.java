package org.example.service;

import org.example.Repository;
import org.example.model.User;

public class UserService {
    Repository repository;
    public UserService(Repository repository){
        this.repository= repository;
    }

    public void addUser(User user){
        repository.addUser(user);
    }

    public User getUser(String username){
        return repository.getUser(username);
    }

}

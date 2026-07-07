package com.example.demo2.service;

import com.example.demo2.exception.UserNotFoundException;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user){
        return userRepo.save(user);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User getUserById(Long id){
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: "+ id));
    }
//    public User updateUser(Long id, User user){
//        User existing = userRepo.findById(id).orElse(null);
//
//        if(existing != null){
//            existing.setName(user.getName());
//            existing.setEmail(user.getEmail());
//
//            return userRepo.save(existing);
//        }
//        return null;
//    }
public User updateUser(Long id,User user){

    User existing=userRepo.findById(id)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found with id : "+id));

    existing.setName(user.getName());
    existing.setEmail(user.getEmail());

    return userRepo.save(existing);

}
    public String deleteUser(Long id){
        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : "+id));

        userRepo.deleteById(id);
        return "User delete Succesffully";
    }
}

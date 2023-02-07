package com.example.elearningmanagementsystem.services;

import com.example.elearningmanagementsystem.Repository.UserRepository;
import com.example.elearningmanagementsystem.model.User11;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository ;
    public User11 saveUser(User11 user){
        return userRepository.save(user);
    }
    public User11 updateUserProfile(User11 user){
        return  userRepository.save(user) ;
    }


    public List<User11> getAllUsers()
    {
      return (List<User11>) userRepository.findAll();
    }

    public User11 fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
        public User11 fetchUserByUsername(String username)
        {
            return userRepository.findByUsername(username);
        }

        public User11 fetchUserByEmailAndPassword(String email, String password)
        {
            return userRepository.findByEmailAndPassword(email, password);
        }

        public List<User11> fetchProfileByEmail(String email)
        {
            return (List<User11>)userRepository.findProfileByEmail(email);
        }
    }


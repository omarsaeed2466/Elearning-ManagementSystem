package com.example.elearningmanagementsystem.Repository;

import com.example.elearningmanagementsystem.model.User11;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User11,Integer> {
    public User11 findByEmail(String email);

    public User11 findByUsername(String username);

    public User11 findByEmailAndPassword(String email, String password);

    public List<User11> findProfileByEmail(String email);
}

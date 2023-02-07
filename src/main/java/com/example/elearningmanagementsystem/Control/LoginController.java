package com.example.elearningmanagementsystem.Control;

import com.example.elearningmanagementsystem.model.Professor;
import com.example.elearningmanagementsystem.model.User11;
import com.example.elearningmanagementsystem.services.ProfessorService;
import com.example.elearningmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProfessorService professorService;
    @GetMapping("/")
    public String welcomeMessage()
    {
        return "Welcome to Elearning Management system !!!";
    }
    @PostMapping("/loginuser")
    public User11 loginUser(@RequestBody User11 user) throws Exception
    {
        String currEmail = user.getEmail();
        String currPassword = user.getPassword();

        User11 userObj = null;
        if(currEmail != null && currPassword != null)
        {
            userObj = userService.fetchUserByEmailAndPassword(currEmail, currPassword);
        }
        if(userObj == null)
        {
            throw new Exception("User does not exists!!! Please enter valid credentials...");
        }
        return userObj;
    }@PostMapping("/loginprofessor")
    public Professor loginDoctor(@RequestBody Professor professor)throws Exception{
        String currEmail = professor.getEmail();
        String currPassword = professor.getPassword();
        Professor professorObj = null;
        if (currEmail != null && currPassword != null){
            professorObj = professorService.fetchProfessorByEmailAndPassword(currEmail,currPassword);
        }if (professorObj==null){
            throw new Exception("Professor does not exists!!! Please enter valid credentials...");
        }
return professorObj;
    }


}

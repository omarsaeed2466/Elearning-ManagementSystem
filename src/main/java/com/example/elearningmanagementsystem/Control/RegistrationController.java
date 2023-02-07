package com.example.elearningmanagementsystem.Control;

import com.example.elearningmanagementsystem.model.Professor;
import com.example.elearningmanagementsystem.model.User11;
import com.example.elearningmanagementsystem.services.ProfessorService;
import com.example.elearningmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfessorService professorService;
    @PostMapping("/registeruser")
    public User11 registerUser(@RequestBody User11 user) throws Exception
    {
        String currEmail = user.getEmail();
        String newID = getNewId();
        user.setUserid(newID);
        if (currEmail != null || !"".equals(currEmail) ){
            User11 userObj = userService.fetchUserByEmail(currEmail) ;
            if (userObj != null){
                throw new Exception("User with "+currEmail+" already exists !!!");
            }
        }
        User11 userObj = null;
        userObj = userService.saveUser(user);
        return userObj;
    }
    @PostMapping("/registerprofessor")
    public Professor registerDoctor(@RequestBody Professor professor) throws Exception
    {
        String currEmail = professor.getEmail();
        String newID = getNewId();
        professor.setProfessorid(newID);
        if (currEmail != null || !"".equals(currEmail) ){
          Professor professorObj = professorService.fetchProfessorByEmail(currEmail) ;
            if (professorObj != null){
                throw new Exception("Professor with "+currEmail+" already exists !!!");
            }
        }
        Professor professorObj = null;
        professorObj = professorService.saveProfessor(professor);
        return professorObj;
    }


    public String getNewId(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i<12;i++){
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}

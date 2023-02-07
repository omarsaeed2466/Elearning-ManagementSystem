package com.example.elearningmanagementsystem.Control;

import com.example.elearningmanagementsystem.model.*;
import com.example.elearningmanagementsystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserControl {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private ChapterService chapterService;


    @GetMapping("/userlist")
    public ResponseEntity<List<User11>> getUsers() throws Exception {
        List<User11> users = userService.getAllUsers();
        return new ResponseEntity<List<User11>>(users, HttpStatus.OK);
    }

    @PostMapping("/enrollnewcourse/{email}/{role}")
    public String enrollNewCourses(@RequestBody Enrollment enrollment, @PathVariable String email, @PathVariable String role) throws Exception {
        String enrolledUserName = "";
        String enrolledUserID = "";
        if (role.equalsIgnoreCase("user")) {
            List<User11> users = userService.getAllUsers();
            for (User11 userObj : users) {
                if (userObj.getEmail().equalsIgnoreCase(email)) {
                    enrolledUserName = userObj.getUsername();
                    enrolledUserID = userObj.getUserid();
                    enrollment.setEnrolleduserid(enrolledUserID);
                    enrollment.setEnrolledusername(enrolledUserName);
                    break;
                }
            }
        } else if (role.equalsIgnoreCase("professor")) {
            List<Professor> professors = professorService.getAllProfessors();
            for (Professor professorObj : professors) {
                if (professorObj.getEmail().equalsIgnoreCase(email)) {
                    enrolledUserName = professorObj.getProfessorname();
                    enrolledUserID = professorObj.getProfessorid();
                    enrollment.setEnrolleduserid(enrolledUserID);
                    enrollment.setEnrolledusername(enrolledUserName);
                    break;
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = formatter.format(date);
        enrollment.setEnrolleddate(todayDate);
        Enrollment enrollmentObj = null;
        enrollmentObj = enrollmentService.saveEnrollment(enrollment);
        System.out.println(enrollmentObj);
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        Map<String, Integer> enrolledCount = new LinkedHashMap<>();
        for (Enrollment enrollObj : enrollments) {
            String courseName = enrollObj.getCoursename();
            if (enrolledCount.containsKey(courseName)) {
                enrolledCount.put(courseName, enrolledCount.get(courseName) + 1);
            }else
                enrolledCount.put(courseName, 1);
            } for (Map.Entry<String, Integer> obj : enrolledCount.entrySet()) {
                if (obj.getKey().equalsIgnoreCase(enrollment.getCoursename())) {
                    enrollmentService.updateEnrolledcount(obj.getKey(), obj.getValue());
                    courseService.updateEnrolledcount(obj.getKey(),obj.getValue());

                }
            }
            return "done";

        }


    @GetMapping("/getenrollmentstatus/{coursename}/{email}/{role}")
    public ResponseEntity<Set<String>> getEnrollmentStatus(@PathVariable String coursename, @PathVariable String email, @PathVariable String role) throws Exception {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        User11 userObj;
        Professor professorObj;
        String enrolledUser = "";
        if (role.equalsIgnoreCase("user")) {
            userObj = userService.fetchUserByEmail(email);
            enrolledUser = userObj.getUsername();

        } else if (role.equalsIgnoreCase("professor")) {
            professorObj = professorService.fetchProfessorByEmail(email);
            enrolledUser = professorObj.getProfessorname();
        }
        Set<String> enrollmentStatus = new LinkedHashSet<>();
        int flag = 0;
        OUTER:
        for (Enrollment enrollmentObj : enrollments) {
            if (enrollmentObj.getCoursename().equalsIgnoreCase(coursename) &&
                    enrollmentObj.getEnrolledusername().equalsIgnoreCase(enrolledUser)) {
                enrollmentStatus.add("enrolled");
                flag = 1;
                break OUTER;
            }
        }
        if (flag == 0)
            enrollmentStatus.add("notEnrolled");
        return new ResponseEntity<Set<String>>(enrollmentStatus, HttpStatus.OK);
    }

    @PostMapping("/addtowishlist")
    public ResponseEntity<Wishlist> addNewCourse(@RequestBody Wishlist wishlist) throws Exception {
        Wishlist wishlistObj = null;
        wishlistObj = wishlistService.addToWishlist(wishlist);
        return new ResponseEntity<Wishlist>(wishlistObj, HttpStatus.OK);
    }


    @GetMapping("/getwishliststatus/{coursename}/{email}")
    public ResponseEntity<Set<String>> getWishlistStatus(@PathVariable String coursename
            , @PathVariable String email) throws Exception {
        List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
        Set<String> wishlistsStatus = new LinkedHashSet<>();
        int flag = 0;
        OUTER:
        for (Wishlist wishlistsObj : wishlists) {
            if (wishlistsObj.getCoursename().equalsIgnoreCase(coursename) && wishlistsObj.getLikeduser().equalsIgnoreCase(email)) {
                wishlistsStatus.add("liked");
                flag = 1;
                break OUTER;
            }
        }
        if (flag == 0)
            wishlistsStatus.add("notliked");
        return new ResponseEntity<Set<String>>(wishlistsStatus, HttpStatus.OK);

    }
    @GetMapping("/getallwishlist")
    public ResponseEntity<List<Wishlist>> getAllWislist() throws Exception{
        List<Wishlist>wishlists = wishlistService.getAllLikedCourses();
        return new ResponseEntity<List<Wishlist>>(wishlists, HttpStatus.OK);
    }@GetMapping("/getwishlistbyemail/{email}")
    public ResponseEntity<List<Wishlist>> getWishlistByEmail(@PathVariable String email) throws Exception
    {
        List<Wishlist> Wishlists = wishlistService.fetchByLikeduser(email);
        return new ResponseEntity<List<Wishlist>>(Wishlists, HttpStatus.OK);
    }



    @GetMapping("/getenrollmentbyemail/{email}/{role}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByEmail(@PathVariable String email, @PathVariable String role) throws Exception
    {
        User11 userObj;
        Professor professorObj;
        String enrolledUser = "";
        if(role.equalsIgnoreCase("user"))
        {
            userObj = userService.fetchUserByEmail(email);
            enrolledUser = userObj.getUsername();
        }
        else if(role.equalsIgnoreCase("professor"))
        {
            professorObj = professorService.fetchProfessorByEmail(email);
            enrolledUser = professorObj.getProfessorname();
        }
        List<Enrollment> enrollments = enrollmentService.fetchByEnrolledusername(enrolledUser);
        return new ResponseEntity<List<Enrollment>>(enrollments, HttpStatus.OK);
    }	@GetMapping("/getchapterlistbycoursename/{coursename}")
    public ResponseEntity<List<Chapter>> getChapterListByCoursename(@PathVariable String coursename) throws Exception
    {
        List<Chapter> chapterLists = chapterService.fetchByCoursename(coursename);
        if(chapterLists.size()==0)
        {
            Chapter obj1 = new Chapter();
            obj1.setChapter1name("");
            obj1.setChapter2name("");
            obj1.setChapter3name("");
            obj1.setChapter4name("");
            obj1.setChapter5name("");
            obj1.setChapter6name("");
            obj1.setChapter7name("");
            obj1.setChapter8name("");
            chapterLists.add(obj1);
        }
        return new ResponseEntity<List<Chapter>>(chapterLists, HttpStatus.OK);
    }
    @GetMapping("/userprofileDetails/{email}")
    public ResponseEntity<List<User11>> getProfileDetails(@PathVariable String email) throws Exception
    {
        List<User11> users = userService.fetchProfileByEmail(email);
        return new ResponseEntity<List<User11>>(users, HttpStatus.OK);
    }
    @PutMapping("/updateuser")
    public ResponseEntity<User11> updateUserProfile(@RequestBody User11 user) throws Exception
    {
        User11 userobj = userService.updateUserProfile(user);
        return new ResponseEntity<User11>(userobj, HttpStatus.OK);
    }
    @GetMapping("/gettotalusers")
    public ResponseEntity<List<Integer>> getTotalUsers() throws Exception
    {
        List<User11> users = userService.getAllUsers();
        List<Integer> usersCount = new ArrayList<>();
        usersCount.add(users.size());
        return new ResponseEntity<List<Integer>>(usersCount, HttpStatus.OK);
    }
    @GetMapping("/gettotalenrollmentcount")
    public ResponseEntity<List<Integer>> getTotalEnrolmentCount() throws Exception
    {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int count = 0 ;
        for (Enrollment   enrollmentObj : enrollments){
            count+= Integer.parseInt(enrollmentObj.getEnrolledcount());
        }
        List<Integer>  enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(count);
        return new ResponseEntity<List<Integer>>(enrollmentsCount, HttpStatus.OK);
    }@GetMapping("/gettotalenrollments")
    public ResponseEntity<List<Integer>> getTotalEnrollments() throws Exception
    {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        List<Integer> enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(enrollments.size());
        return new ResponseEntity<List<Integer>>(enrollmentsCount, HttpStatus.OK);
    }

}
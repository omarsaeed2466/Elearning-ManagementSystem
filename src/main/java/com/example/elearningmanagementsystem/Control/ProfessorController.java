package com.example.elearningmanagementsystem.Control;

import com.example.elearningmanagementsystem.model.Chapter;
import com.example.elearningmanagementsystem.model.Course;
import com.example.elearningmanagementsystem.model.Professor;
import com.example.elearningmanagementsystem.model.Wishlist;
import com.example.elearningmanagementsystem.services.ChapterService;
import com.example.elearningmanagementsystem.services.CourseService;
import com.example.elearningmanagementsystem.services.ProfessorService;
import com.example.elearningmanagementsystem.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private WishlistService wishlistService;
    @GetMapping("/professorlist")
    public ResponseEntity<List<Professor>> getProfessorList() throws Exception
    {
        List<Professor> professors = professorService.getAllProfessors();
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }
    @GetMapping("/websitecourselist")
    public ResponseEntity<List<Course>> getWebsiteCourseList() throws Exception
    {
        List<Course> websiteCourseList = courseService.fetchByCoursetype("Website");
        return new ResponseEntity<List<Course>>(websiteCourseList,HttpStatus.OK);

    }
    @GetMapping("/youtubecourselist")

    public ResponseEntity<List<Course>> getYoutubeCourseList() throws Exception
    {
        List<Course> youtubeCourseList = courseService.fetchByCoursetype("Youtube");
		for(Course list:youtubeCourseList)
		{
			System.out.println(list.getYoutubeurl());
		}
        return new ResponseEntity<List<Course>>(youtubeCourseList, HttpStatus.OK);
    }
    @GetMapping("/courselistbyname/{coursename}")
    public ResponseEntity<List<Course>> getCourseListByName(@PathVariable String coursename) throws Exception
    {
        Course courseList = courseService.fetchCourseByCoursename(coursename);
        System.out.println(courseList.getCoursename()+"");
        List<Course> courselist = new ArrayList<>();
        courselist.add(courseList);
        return new ResponseEntity<List<Course>>(courselist, HttpStatus.OK);
    }

    @GetMapping("/professorlistbyemail/{email}")
    public ResponseEntity<List<Professor>> getProfessorListByEmail(@PathVariable String email) throws Exception
    {
        List<Professor>professors = professorService.getProfessorListByEmail(email);
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }
    @PostMapping("/addProfessor")
    public Professor  addNewProfessor(@RequestBody Professor professor) throws Exception
    {
Professor professorObj = null ;
String newID = getNewID();
professor.setProfessorid(newID);
professorObj = professorService.addNewProfessor(professor);
professorService.updateStatus(professor.getEmail());
return professorObj;
    }
    @PostMapping("/addCourse")
    public Course addNewCourse(@RequestBody Course course)throws Exception{
        Course courseobj = null ;
        String newID = getNewID();
        course.setCourseid(newID);

        courseobj = courseService.addNewCourse(course);
        return courseobj ;
    }


    @PostMapping("/addnewchapter")
    public Chapter addNewChapters(@RequestBody Chapter chapter) throws Exception
    {
        Chapter chapterObj = null;
        chapterObj = chapterService.addNewChapter(chapter);
        return chapterObj;
    }
    @GetMapping("/acceptstatus/{email}")
    public ResponseEntity<List<String>> updateStatus(@PathVariable String email) throws Exception
    {
        professorService.updateStatus(email);
        List<String> ai = new ArrayList<>();
        ai.add("accepted");
        return new  ResponseEntity<List<String>>(ai,HttpStatus.OK);
    }
    @GetMapping("/rejectstatus/{email}")
    public ResponseEntity<List<String>> rejectStatus(@PathVariable String email) throws Exception
    {
        professorService.rejectStatus(email);
        List<String> al=new ArrayList<>();
        al.add("rejected");
        return new ResponseEntity<List<String>>(al,HttpStatus.OK);
    }
    @GetMapping("/professorprofileDetails/{email}")
    public ResponseEntity<List<Professor>> getProfileDetails(@PathVariable String email) throws Exception
    {
        List<Professor>professors = professorService.fetchProfileByEmail(email);
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }
    @PutMapping("/updateprofessor")
    public ResponseEntity<Professor> updateProfessorProfile(@RequestBody Professor professor) throws Exception
    {
        Professor professorobj = professorService.updateProfessorProfile(professor);
        return new ResponseEntity<Professor>(professorobj, HttpStatus.OK);
    }

    @GetMapping("/gettotalprofessors")
    public ResponseEntity<List<Integer>>getTotalProfessors() throws Exception
    {
        List<Professor> professors = professorService.getAllProfessors();
        List<Integer>  professorsCount = new ArrayList<>();
        professorsCount.add(professors.size());
        return new ResponseEntity<List<Integer>>(professorsCount, HttpStatus.OK);
    }
    @GetMapping("/gettotalchapters")
    public ResponseEntity<List<Integer>> getTotalChapters() throws Exception
    {
        List<Chapter> chapters = chapterService.getAllChapters();
List<Integer> chapterCounts =new ArrayList<>();
chapterCounts.add(chapters.size());
        return new ResponseEntity<List<Integer>>(chapterCounts, HttpStatus.OK);
    }@GetMapping("/gettotalcourses")
    public ResponseEntity<List<Integer>> getTotalCourses() throws Exception
    {
        List<Course> courses = courseService.getAllCourses();
        List<Integer> courseCounts = new ArrayList<>();
        courseCounts.add(courses.size());
        return new ResponseEntity<List<Integer>>(courseCounts,HttpStatus.OK);
    }
    @GetMapping("/gettotalwishlist")
    public ResponseEntity<List<Integer>>gettotalwishlist()throws Exception{
        List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
        List<Integer> wishListCounts =new ArrayList<>();
        wishListCounts.add(wishlists.size());
        return new ResponseEntity<List<Integer>>(wishListCounts, HttpStatus.OK);
    } @GetMapping("/getcoursenames")
    public ResponseEntity<List<String>>  getCourseNames() throws Exception
    {
        List<Course> courses = courseService.getAllCourses();
        List<String> coursesNames = new ArrayList<>();
        for (Course obj:courses){
            coursesNames.add(obj.getCoursename());
        }
        return new ResponseEntity<List<String>>(coursesNames,HttpStatus.OK);
    }

    public String getNewID()
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++)
        {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}

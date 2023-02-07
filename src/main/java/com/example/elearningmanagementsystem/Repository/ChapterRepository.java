package com.example.elearningmanagementsystem.Repository;

import com.example.elearningmanagementsystem.model.Chapter;
import com.example.elearningmanagementsystem.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChapterRepository  extends CrudRepository<Chapter,Integer> {
    public List<Chapter> findByCoursename(String Coursename);
}

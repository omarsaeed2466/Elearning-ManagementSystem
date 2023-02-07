package com.example.elearningmanagementsystem.services;

import com.example.elearningmanagementsystem.Repository.ChapterRepository;
import com.example.elearningmanagementsystem.model.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepo;

    public Chapter saveChapter(Chapter chapter)
    {
        return chapterRepo.save(chapter);
    }

    public Chapter addNewChapter(Chapter chapter)
    {
        return chapterRepo.save(chapter);
    }

    public List<Chapter> getAllChapters()
    {
        return (List<Chapter>)chapterRepo.findAll();
    }

    public List<Chapter> fetchByCoursename(String coursename)
    {
        return (List<Chapter>)chapterRepo.findByCoursename(coursename);
    }

}

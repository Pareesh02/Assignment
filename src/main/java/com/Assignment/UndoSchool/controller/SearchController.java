package com.Assignment.UndoSchool.controller;

import com.Assignment.UndoSchool.document.CourseDocument;
import com.Assignment.UndoSchool.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class SearchController {

    private final CourseService courseService;

    @Autowired
    public SearchController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/search")
    public Page<CourseDocument> search(
            @RequestParam(required = false, name = "q") String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "upcoming") String sort
    ) {
        Sort sortSpec = "upcoming".equalsIgnoreCase(sort)
                ? Sort.by("nextSessionDate").ascending()
                : Sort.by("nextSessionDate").descending();

        return courseService.searchCourses(
                keyword,
                category,
                type,
                minAge,
                maxAge,
                PageRequest.of(page, size, sortSpec)
        );
    }

    @GetMapping
    public Page<CourseDocument> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return courseService.searchCourses(
                null, null, null, null, null,
                PageRequest.of(page, size, Sort.by("nextSessionDate").ascending())
        );
    }
}

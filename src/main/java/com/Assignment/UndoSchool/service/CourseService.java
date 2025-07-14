package com.Assignment.UndoSchool.service;

import com.Assignment.UndoSchool.document.CourseDocument;
import com.Assignment.UndoSchool.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final ElasticsearchOperations operations;

    @Autowired
    public CourseService(CourseRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @PostConstruct
    public void loadCourses() {
        try (InputStream is = getClass().getResourceAsStream("/sample-courses.json")) {
            if (is == null) {
                System.err.println("⚠️ sample-courses.json not found in resources.");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // ✅ Fix for Instant
            CourseDocument[] courses = mapper.readValue(is, CourseDocument[].class);
            repository.saveAll(Arrays.asList(courses));
            System.out.println("✅ Courses loaded successfully.");
        } catch (Exception e) {
            System.err.println("❌ Error loading sample-courses.json:");
            e.printStackTrace();
        }
    }

    public Page<CourseDocument> searchCourses(
            String keyword,
            String category,
            String type,
            Integer minAge,
            Integer maxAge,
            Pageable pageable
    ) {
        Criteria criteria = new Criteria();

        if (keyword != null && !keyword.isBlank()) {
            criteria = criteria.or(new Criteria("title").matches(keyword))
                    .or(new Criteria("description").matches(keyword));
        }

        if (category != null && !category.isBlank()) {
            criteria = criteria.and(new Criteria("category").is(category));
        }

        if (type != null && !type.isBlank()) {
            criteria = criteria.and(new Criteria("type").is(type));
        }

        if (minAge != null) {
            criteria = criteria.and(new Criteria("minAge").greaterThanEqual(minAge));
        }

        if (maxAge != null) {
            criteria = criteria.and(new Criteria("maxAge").lessThanEqual(maxAge));
        }

        Query query = new CriteriaQuery(criteria, pageable);
        SearchHits<CourseDocument> hits = operations.search(query, CourseDocument.class);

        List<CourseDocument> courses = hits.stream()
                .map(hit -> hit.getContent())
                .filter(Objects::nonNull)
                .toList();

        return new PageImpl<>(courses, pageable, hits.getTotalHits());
    }
}

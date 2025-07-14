package com.Assignment.UndoSchool.repository;

import com.Assignment.UndoSchool.document.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {

}

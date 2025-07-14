package com.Assignment.UndoSchool.dto;

import com.Assignment.UndoSchool.document.CourseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor

public class SearchResponse {
    private List<CourseDocument> content;
    private long totalElements;
}

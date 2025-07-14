package com.Assignment.UndoSchool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.Assignment.UndoSchool")
public class ElasticSearchConfig {

}

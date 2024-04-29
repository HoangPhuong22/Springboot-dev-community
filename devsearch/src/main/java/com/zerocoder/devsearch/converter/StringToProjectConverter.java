package com.zerocoder.devsearch.converter;

import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProjectConverter implements Converter<String, Project> {
    @Autowired
    private ProjectService projectService;
    @Override
    public Project convert(String source) {
        Project project = projectService.getProjectById(Long.parseLong(source));
        return project;
    }
}

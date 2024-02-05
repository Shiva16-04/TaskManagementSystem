package com.Apiwiz.taskmanagementapi.generators;

import com.Apiwiz.taskmanagementapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskCodeGenerator implements CodeGenerator{
    @Autowired
    private TaskRepository taskRepository;

    public String generate() {
        String prefix="TSK";
        return CodeGenerator.super.generate(prefix);
    }

    @Override
    public Long getLatestSequenceNumber(String year) {
        Long latestSequenceNumber = taskRepository.findLatestSequenceNumber(year);
        return (latestSequenceNumber!=null) ? latestSequenceNumber : 0;
    }
}

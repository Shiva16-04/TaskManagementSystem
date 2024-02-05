package com.Apiwiz.taskmanagementapi.generators;

import com.Apiwiz.taskmanagementapi.exceptions.MaximumLimitReachedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public interface CodeGenerator {
    static final String prefix=null;
    static final String FORMAT_PATTERN="yyyy";
    static final Long MAX_SEQUENCE_NUMBER=Long.MAX_VALUE;

    default String generate(String prefix){

        LocalDate currentDate=LocalDate.now();
        String year= currentDate.format(DateTimeFormatter.ofPattern(FORMAT_PATTERN));

        //fetching the latest sequence number that persisted previously
        long sequenceNumber = getLatestSequenceNumber(year);
        sequenceNumber+=1;
        if(sequenceNumber>MAX_SEQUENCE_NUMBER){
            throw new MaximumLimitReachedException("Cannot Add user/task as it reached max limit to add users/task for this year "+year);
        }
        return year+prefix+String.format("%04d", sequenceNumber);
    }
    public Long getLatestSequenceNumber(String year);
}

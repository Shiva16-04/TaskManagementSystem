package com.Apiwiz.taskmanagementapi.enums;

public enum Status {
    COMPLETED("Completed"),
    IN_PROGRESS("In Progress"),
    PENDING("Pending");

    public final String displayName;
    Status(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName() {
        return displayName;
    }


}

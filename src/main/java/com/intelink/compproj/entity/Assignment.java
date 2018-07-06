package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class Assignment {
    private Employee employee;
    private Project project;
    @Setter
    private LocalDate dateStartedWorking;
    @Setter
    private LocalDate dateFinishedWorking;
    @Getter
    private String workDone;

    public Assignment(Employee employee, Project project, LocalDate dateStartedWorking, LocalDate dateFinishedWorking) {
        this.employee = employee;
        this.project = project;
        this.dateStartedWorking = dateStartedWorking;
        this.dateFinishedWorking = dateFinishedWorking;
        workDone = "";
    }

    public Assignment(Employee employee, Project project, LocalDate dateStartedWorking) {
        this(employee, project, dateStartedWorking, null);
    }

    public Assignment(Employee employee, Project project) {
        this(employee, project, LocalDate.now());
    }

    public boolean isWorkingOn(LocalDate date) {
        return isStarted(date) && !isFinished(date);
    }

    public boolean isFinished(LocalDate date) {
        return dateFinishedWorking != null
                && (date.isAfter(dateFinishedWorking) || date.isEqual(dateFinishedWorking));
    }

    private boolean isStarted(LocalDate date) {
        return dateStartedWorking != null
                && (dateFinishedWorking == null || date.isBefore(dateFinishedWorking));
    }

    public void addWorkDone(String work) {
        workDone += work + " ";
    }
}

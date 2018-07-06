package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Project extends BasicEntity {

    private Set<Technology> requiredTechnologies;
    @Getter
    private List<Assignment> assignments;
    @Getter
    @Setter
    private Company company;
    @Getter
    @Setter
    private boolean commercial;

    public Project() {
        assignments = new ArrayList<>();
        requiredTechnologies = new HashSet<>();
    }

    public Boolean isNotCommercial() {
        return !this.isCommercial();
    }

    public void require(Technology technology) {
        requiredTechnologies.add(technology);
    }

    public void notRequire(Technology technology) {
        requiredTechnologies.remove(technology);
    }

    public boolean requires(Technology technology) {
        return requiredTechnologies.contains(technology);
    }

    public boolean isSuitableFor(Employee employee) {
        return requiredTechnologies.isEmpty() || requiredTechnologies.stream().anyMatch(employee::knows);
    }

    public List<Assignment> getAssignments(LocalDate date) {
        return assignments.stream().filter(assignment -> assignment.isWorkingOn(date)).collect(Collectors.toList());
    }

    public List<Employee> getCurrentlyWorkingEmployees(LocalDate date) {
        return getAssignments(date).stream().map(Assignment::getEmployee).collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return getAssignments().stream().map(Assignment::getEmployee).collect(Collectors.toList());
    }

    public void assign(Employee employee, LocalDate dateStartedWorking, LocalDate dateFinishedWorking) {
        Assignment assignment = new Assignment(employee, this, dateStartedWorking, dateFinishedWorking);
        assignments.add(assignment);
    }

    public List<Assignment> getActiveAssignmentsOf(Employee employee, LocalDate date) {
        return getAssignments(date).stream()
                .filter(assignment -> assignment.getEmployee().equals(employee)).collect(Collectors.toList());
    }

    public void dismiss(Employee employee, LocalDate now, LocalDate dismissDate) {
        getActiveAssignmentsOf(employee, now).forEach(assignment -> assignment.setDateFinishedWorking(dismissDate));
    }

    public List<Assignment> getFinishedAssignments(Employee employee, LocalDate date) {
        return assignments.stream().filter(assignment ->
                assignment.getEmployee().equals(employee)
                        && assignment.isFinished(date)
        ).collect(Collectors.toList());
    }

    public boolean isFinishedBy(LocalDate date) {
        return assignments.stream().allMatch(assignment -> assignment.isFinished(date));
    }

    public boolean wasDevelopedBy(Employee employee) {
        return getAllEmployees().contains(employee);
    }
}

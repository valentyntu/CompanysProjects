package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Company extends BasicEntity {
    @Getter
    private Set<Employee> employees;
    @Getter
    private Set<Project> projects;
    @Getter
    @Setter
    private String name;

    public Company(String name) {
        this.name = name;
        employees = new HashSet<>();
        projects = new HashSet<>();
    }

    public void employ(Employee employee) throws AssignmentException {
        employees.add(employee);
        employee.setCompany(this);
        employee.assignToProject();
    }

    public void fire(Employee employee, LocalDate now, LocalDate fireDate) {
        employee.setCompany(null);
        employees.remove(employee);
        projects.forEach(project -> project.dismiss(employee, now, fireDate));
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setCompany(this);
    }

    public Set<Project> getCommercialProjects() {
        return projects.stream().filter(Project::isCommercial).collect(Collectors.toSet());
    }

    public Set<Project> getInternalProjects() {
        return projects.stream().filter(Project::isNotCommercial).collect(Collectors.toSet());
    }

    public void abandonProject(Project project, LocalDate date) {
        projects.remove(project);
        project.getCurrentlyWorkingEmployees(date).forEach(employee -> {
            project.getAssignments(date).forEach(assignment -> assignment.setDateFinishedWorking(date));
        });
    }
}

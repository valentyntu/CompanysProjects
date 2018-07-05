package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

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

    public void employ(Employee employee) {
        employees.add(employee);
        employee.setCompany(this);
        employee.assignToProject();
    }

    public void fire(Employee employee) {
        employee.setCompany(null);
        employees.remove(employee);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setCompany(this);
    }

    public void stopProject(Project project) {
        projects.remove(project);
    }

    public Set<Project> getCommercialProjects() {
        return projects.stream().filter(Project::isCommercial).collect(Collectors.toSet());
    }

    public Set<Project> getInternalProjects() {
        return projects.stream().filter(Project::isNotCommercial).collect(Collectors.toSet());
    }

    public void abandonProject(Project project) {
        projects.remove(project);
        project.getEmployees().forEach(employee -> employee.getProjects().remove(project));
    }
}

package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public class Employee extends BasicEntity {
    @Getter
    @Setter
    private String fullName;
    @Getter
    private Portfolio portfolio;
    private Set<Technology> knownTechnologies;
    @Getter
    private Set<Project> projects;
    @Setter
    @Getter
    private Company company;

    public Employee(String fullName) {
        this.fullName = fullName;
        portfolio = new Portfolio();
        knownTechnologies = new HashSet<>();
        projects = new HashSet<>();
    }

    public void assignToProject() {
        Set<Project> commercialProjects = company.getCommercialProjects();
        Project newProject = commercialProjects.stream()
                .filter(project -> project.isSuitableFor(this)).findFirst().orElse(null);
        if (newProject == null) {
            Set<Project> internalProjects = company.getInternalProjects();
            newProject = internalProjects.stream()
                    .filter(project -> project.isSuitableFor(this)).findFirst().orElse(null);
        }
        if (newProject != null) {
            assignToProject(newProject);
        }
    }

    public void assignToProject(Project project) {
        if (project.isSuitableFor(this)) {
            project.getCurrentPhase().getEmployees().add(this);
            projects.add(project);
        }
    }

    public void know(Technology technology) {
        knownTechnologies.add(technology);
    }

    public boolean knows(Technology technology) {
        return knownTechnologies.contains(technology);
    }
}

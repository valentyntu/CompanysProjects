package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Employee extends BasicEntity {
    private String fullName;
    private Portfolio portfolio;
    private Set<Technology> knownTechnologies;
    private Set<Project> projects;
    private Company company;

    public Employee(String fullName) {
        this.fullName = fullName;
        portfolio = new Portfolio();
        knownTechnologies = new HashSet<>();
    }

    public void assignToProject() {
        int projects = this.projects.size();

    }

    public void assignToProject(Project project) {
        if (fitsRequirementsOf(project)) {
            project.getCurrentPhase().getEmployees().add(this);
            projects.add(project);
        }
    }

    public boolean fitsRequirementsOf(Project project) {
        return project.getTechnologies().stream().anyMatch(technology -> getKnownTechnologies().contains(technology));
    }
}

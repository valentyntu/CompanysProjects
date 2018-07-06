package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class Employee extends BasicEntity {
    @Getter
    @Setter
    private String fullName;
    private Set<Technology> knownTechnologies;
    @Setter
    @Getter
    private Company company;

    public Employee(String fullName) {
        this.fullName = fullName;
        knownTechnologies = new HashSet<>();
    }

    public void assignToProject() throws AssignmentException {
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
        } else {
            throw new AssignmentException("No suitable project found.");
        }
    }

    public void assignToProject(Project project, LocalDate dateStartedWorking) throws AssignmentException {
        if (project.isSuitableFor(this)) {
            project.assign(this, dateStartedWorking, null);
        } else {
            throw new AssignmentException("Employee does not fit the requirements");
        }
    }

    public void assignToProject(Project project) throws AssignmentException {
        assignToProject(project, null);
    }


    public void know(Technology technology) {
        knownTechnologies.add(technology);
    }

    public boolean knows(Technology technology) {
        return knownTechnologies.contains(technology);
    }

    public boolean hasWorkedOn(Project project) {
        return project.wasDevelopedBy(this);
    }


    public Function<Project, Stream<Assignment>> assignmentCollector(LocalDate date) {
        return p -> p.getFinishedAssignments(this, date).stream();
    }
}

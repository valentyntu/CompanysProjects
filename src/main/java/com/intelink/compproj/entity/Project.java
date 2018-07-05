package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class Project extends BasicEntity {
    private Set<Phase> phases;
    private Phase currentPhase;
    private Set<Technology> technologies;
    private Company company;
    private boolean commercial;

    public Project() {
        phases = new TreeSet<>();
        technologies = new HashSet<>();
    }

    public Set<Employee> getEmployees() {
        Set<Employee> employees = new HashSet<>();
        phases.forEach(phase -> employees.addAll(phase.getEmployees()));
        return employees;
    }

    public Boolean isNotCommercial() {
        return !this.isCommercial();
    }
}

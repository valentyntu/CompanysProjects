package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class Project extends BasicEntity {
    @Getter
    @Setter
    private Set<Phase> phases;
    @Getter
    @Setter
    private Phase currentPhase;
    private Set<Technology> requiredTechnologies;
    @Getter
    @Setter
    private Company company;
    @Getter
    @Setter
    private boolean commercial;
    private boolean finished;

    public Project() {
        phases = new TreeSet<>();
        requiredTechnologies = new HashSet<>();
        finished = false;
    }

    public Project(int phases) {
        this();
        for (int i = 0; i < phases; i++) {
            this.addPhase(new Phase(this));
        }
    }

    public Set<Employee> getEmployees() {
        Set<Employee> employees = new HashSet<>();
        phases.forEach(phase -> employees.addAll(phase.getEmployees()));
        return employees;
    }

    public Boolean isNotCommercial() {
        return !this.isCommercial();
    }

    public void start() {
        currentPhase = ((TreeSet<Phase>) phases).first();
    }

    public void finish() {
        finished = true;
    }

    public void addPhase(Phase phase) {
        int phaseNumber;
        TreeSet<Phase> phases = (TreeSet<Phase>) this.getPhases();
        try {
            phaseNumber = phases.last().getNumber() + 1;
        } catch (NoSuchElementException e) {
            phaseNumber = 1;
        }
        phase.setNumber(phaseNumber);
        if (phases.isEmpty()) {
            currentPhase = phase;
        }
        phases.add(phase);
        if (finished) {
            finished = false;
            currentPhase = phase;
        }
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
        return requiredTechnologies.stream().anyMatch(employee::knows);
    }
}

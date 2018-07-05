package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Phase extends BasicEntity implements Comparable {

    @Getter
    @Setter
    private Integer number;
    @Getter
    private Set<Employee> employees;
    @Getter
    private Project project;

    public Phase(Project project) {
        employees = new HashSet<>();
        this.project = project;
        number = null;
    }

    @Override
    public int compareTo(Object object) {
        if (!(object instanceof Phase)) {
            return -1;
        } else {
            return this.getNumber().compareTo(((Phase) object).getNumber());
        }
    }

    public void complete() {
        TreeSet<Phase> projectPhases = (TreeSet<Phase>) project.getPhases();
        TreeSet<Phase> nextPhases = (TreeSet<Phase>) projectPhases.tailSet(this, false);
        employees.forEach(employee -> employee.getPortfolio().addTask(project,
                "Completed phase " + this.getNumber()));
        if (nextPhases.size() > 0) {
            project.setCurrentPhase(nextPhases.iterator().next());
        } else {
            project.finish();
        }
    }
}

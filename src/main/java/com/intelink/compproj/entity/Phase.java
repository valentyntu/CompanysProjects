package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.text.Collator;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class Phase extends BasicEntity implements Comparable {

    private Integer number;
    private Set<Employee> employees;
    private Project project;

    public Phase(Integer number) {
        this.number = number;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Phase)) {
            return -1;
        } else {
            return Collator.getInstance().compare(this.getNumber(), ((Phase) o).getNumber());
        }
    }

    public void complete() {
        TreeSet<Phase> projectPhases = (TreeSet<Phase>) project.getPhases();
        TreeSet<Phase> nextPhases = (TreeSet<Phase>) projectPhases.tailSet(this, false);
        if (nextPhases.size() > 0) {
            project.setCurrentPhase(nextPhases.iterator().next());
        }
    }
}

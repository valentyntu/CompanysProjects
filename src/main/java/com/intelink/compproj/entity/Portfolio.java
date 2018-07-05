package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Portfolio extends BasicEntity {
    private Map<Project, Set<String>> completedTasks;

    public Portfolio() {
        completedTasks = new HashMap<>();
    }

    public void addTask(Project project, String task) {
        if (completedTasks.containsKey(project)) {
            completedTasks.get(project).add(task);
        } else {
            completedTasks.put(project, new HashSet<>());
            addTask(project, task);
        }
    }

    public int getCompletedTasksCount(Project project) {
        int result = completedTasks.get(project).size();
        return result;
    }

    public int getCompletedTasksCount() {
        int result = 0;
        for (Project project : completedTasks.keySet()) {
            result += getCompletedTasksCount(project);
        }
        return result;
    }

    public Set<String> getCompletedTasks(Project project) {
        return completedTasks.get(project);
    }

}

package com.intelink.compproj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Portfolio extends BasicEntity {
    private Map<Project, String> tasks;

    public Portfolio() {
        tasks = new HashMap<>();
    }
}

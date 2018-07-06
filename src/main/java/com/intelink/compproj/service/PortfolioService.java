package com.intelink.compproj.service;

import com.intelink.compproj.entity.Assignment;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.repository.CompanyHardcodedRepository;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PortfolioService {

    private CompanyHardcodedRepository companyHardcodedRepository;

    public PortfolioService(CompanyHardcodedRepository companyHardcodedRepository) {
        this.companyHardcodedRepository = companyHardcodedRepository;
    }

    public List<Assignment> getPortfolioOf(Employee employee, LocalDate date) {
        return companyHardcodedRepository.getAll().stream()
                .flatMap(c -> c.getProjects().stream())
                .filter(employee::hasWorkedOn)
                .flatMap(employee.assignmentCollector(date))
                .collect(toList());
    }

}

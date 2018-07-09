package com.intelink.compproj.service;

import com.intelink.compproj.entity.Assignment;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.repository.CompanyRepository;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PortfolioService {

    private CompanyRepository companyRepository;

    public PortfolioService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Assignment> getPortfolioOf(Employee employee, LocalDate date) {
        return companyRepository.getAll().stream()
                .flatMap(c -> c.getProjects().stream())
                .filter(employee::hasWorkedOn)
                .flatMap(employee.assignmentCollector(date))
                .collect(toList());
    }

    public void printPortfolio(List<Assignment> assignments) {
        StringBuilder portfolio = new StringBuilder();
        if (!assignments.isEmpty()) {
            portfolio.append(String.format("The portfolio of %s:\n", assignments.get(0).getEmployee().getFullName()));
            assignments.sort((o1, o2) -> Collator.getInstance().compare(
                    o1.getProject().getCompany().getName(),
                    o2.getProject().getCompany().getName()
                    )
            );
            assignments.forEach(assignment -> {
                portfolio.append(String.format("\t %s %s-%s\n", assignment.getWorkDone(),
                        assignment.getDateStartedWorking().format(DateTimeFormatter.ISO_DATE),
                        assignment.getDateFinishedWorking().format(DateTimeFormatter.ISO_DATE)));
            });
        }
        System.out.println(portfolio);
    }
}

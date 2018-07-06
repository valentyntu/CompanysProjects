package com.intelink.compproj;

import com.intelink.compproj.entity.AssignmentException;
import com.intelink.compproj.entity.Company;
import com.intelink.compproj.repository.CompanyHardcodedRepository;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.entity.Project;
import com.intelink.compproj.entity.Technology;
import com.intelink.compproj.service.PortfolioService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class CompanyTests extends TestEntityBuilder {

    private void tryToEmploy(Company company, Employee employee) {
        try {
            company.employ(employee);
        } catch (AssignmentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testHireEmployee() {
        Company company = buildCompany();

        Project project = buildInternalProject();
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        tryToEmploy(company, employee);

        Assert.assertTrue(company.getEmployees().contains(employee));
        Assert.assertTrue(company.getInternalProjects().iterator().next()
                .getAllEverAssignedEmployees().contains(employee));
    }

    @Test
    public void testPortfolio() {
        CompanyHardcodedRepository companyRepository = new CompanyHardcodedRepository();
        PortfolioService portfolioService = new PortfolioService(companyRepository);

        Company company = buildCompany();
        companyRepository.add(company);

        Project project = buildInternalProject();
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        tryToEmploy(company, employee);

        LocalDate dateOfStartingOfWork = LocalDate.now();
        Assert.assertEquals(0, project.getCurrentlyWorkingEmployees(dateOfStartingOfWork).size());
        project.getAssignments().get(0).setDateStartedWorking(dateOfStartingOfWork.minusDays(1));
        Assert.assertEquals(1, project.getCurrentlyWorkingEmployees(dateOfStartingOfWork).size());

        String work = "Designing frontend";
        project.getAssignments().get(0).addWorkDone(work);
        Assert.assertEquals(0, portfolioService.getPortfolioOf(employee, dateOfStartingOfWork).size());

        LocalDate dateOfFinishingToWork = dateOfStartingOfWork.plusDays(2);
        project.getActiveAssignmentsOf(employee, dateOfStartingOfWork).iterator().next()
                .setDateFinishedWorking(dateOfFinishingToWork);
        Assert.assertTrue(portfolioService.getPortfolioOf(employee, dateOfFinishingToWork).iterator().next()
                .getWorkDone().contains(work));
        Assert.assertEquals(1, portfolioService.getPortfolioOf(employee, dateOfFinishingToWork).size());

    }

}

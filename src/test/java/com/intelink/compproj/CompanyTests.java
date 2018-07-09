package com.intelink.compproj;

import com.intelink.compproj.entity.Assignment;
import com.intelink.compproj.entity.AssignmentException;
import com.intelink.compproj.entity.Company;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.entity.Project;
import com.intelink.compproj.entity.Technology;
import com.intelink.compproj.repository.CompanyRepository;
import com.intelink.compproj.service.PortfolioService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompanyTests extends TestEntityBuilder {

    @Test(expected = AssignmentException.class)
    public void testUnemployment() throws AssignmentException {
        Company company = buildCompany();

        Project commercialProject = buildCommercialProject();
        commercialProject.require(Technology.JAVA);
        company.addProject(commercialProject);

        Project internalProject = buildInternalProject();
        internalProject.require(Technology.JS);
        company.addProject(internalProject);

        Employee employee = buildUnskilledEmployee();
        company.employ(employee);
    }

    @Test
    public void testHireEmployee() throws AssignmentException {
        Company company = buildCompany();

        Project project = buildInternalProject();
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        company.employ(employee);

        assertThat(company.getEmployees(), hasItem(employee));
        assertThat(company.getInternalProjects().iterator().next().getAllEmployees(), hasItem(employee));
    }

    @Test
    public void testProjectAssignment() throws AssignmentException {
        Company company = buildCompany();

        Project internalProject = buildInternalProject();
        company.addProject(internalProject);

        Project commercialProject = buildCommercialProject();
        company.addProject(commercialProject);

        Employee employee = buildUnskilledEmployee();
        company.employ(employee);

        assertThat(commercialProject.getAllEmployees(), hasItem(employee));
    }

    @Test
    public void testFireEmployee() throws AssignmentException {
        Company company = buildCompany();

        Project commercialProject = buildCommercialProject();
        company.addProject(commercialProject);

        Employee employee = buildUnskilledEmployee();
        company.employ(employee);

        LocalDate now = LocalDate.now();
        LocalDate fireDate = now;
        company.fire(employee, now, fireDate);

        Assert.assertFalse(commercialProject.getCurrentlyWorkingEmployees(now).contains(employee));
    }

    @Test
    public void testPortfolio() throws AssignmentException {
        CompanyRepository companyRepository = new CompanyRepository();
        PortfolioService portfolioService = new PortfolioService(companyRepository);

        Company company = buildCompany();
        companyRepository.add(company);

        Project project = buildInternalProject();
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        company.employ(employee);

        Assignment employeeAssignment = project.getAssignments().get(0);

        LocalDate dateOfStartingOfWork = LocalDate.now();
        Assert.assertEquals(0, project.getCurrentlyWorkingEmployees(dateOfStartingOfWork).size());
        employeeAssignment.setDateStartedWorking(dateOfStartingOfWork.minusDays(1));
        Assert.assertEquals(1, project.getCurrentlyWorkingEmployees(dateOfStartingOfWork).size());

        String work = "Designing frontend.";
        employeeAssignment.addWorkDone(work);
        Assert.assertTrue(portfolioService.getPortfolioOf(employee, dateOfStartingOfWork).size() == 0);

        LocalDate dateOfFinishingToWork = dateOfStartingOfWork.plusDays(2);
        project.getActiveAssignmentsOf(employee, dateOfStartingOfWork).iterator().next()
                .setDateFinishedWorking(dateOfFinishingToWork);
        Assert.assertTrue(portfolioService.getPortfolioOf(employee, dateOfFinishingToWork).iterator().next()
                .getWorkDone().contains(work));

        List<Assignment> portfolio = portfolioService.getPortfolioOf(employee, dateOfFinishingToWork);
        Assert.assertEquals(1, portfolio.size());
        portfolioService.printPortfolio(portfolio);

    }

    @Test
    public void testGetProjectsByType() {
        Company company = buildCompany();

        Project internalProject = buildInternalProject();
        company.addProject(internalProject);

        Project commercialProject = buildCommercialProject();
        company.addProject(commercialProject);

        Assert.assertTrue(company.getCommercialProjects().stream().allMatch(Project::isCommercial));
        Assert.assertTrue(company.getInternalProjects().stream().allMatch(Project::isNotCommercial));
    }
}

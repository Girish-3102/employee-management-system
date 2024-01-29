package com.hyperface.employeemanagementsystem.services.impl;
import com.hyperface.employeemanagementsystem.exceptions.DifferentDepartmentException;
import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.Project
import com.hyperface.employeemanagementsystem.models.Role;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.hyperface.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.hyperface.employeemanagementsystem.repositories.EmployeeRepository
import com.hyperface.employeemanagementsystem.security.SecurityUtils;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional;

@Service
class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final EmployeeMapper employeeMapper;
    private final SecurityUtils securityUtils

    EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService, EmployeeMapper employeeMapper, SecurityUtils securityUtils) {
        this.employeeRepository = employeeRepository
        this.departmentService = departmentService
        this.employeeMapper = employeeMapper
        this.securityUtils = securityUtils
    }

    @Override
    @Transactional
    Employee createEmployee(EmployeeRequest employeeRequest) {
        Department department=departmentService.getDepartmentById(employeeRequest.getDepartmentId());
        Employee employee=new Employee();
        employeeMapper.updateEmployeeFromDto(employeeRequest,employee);
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Override
    Employee getEmployeeById(Long id) {
        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }
        else {
            throw new EntityNotFoundException("Employee with the requested ID not found");
        }
    }

    @Override
    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    Employee updateEmployee(Long id,EmployeeRequest employeeRequest){
        Employee employee=getEmployeeById(id);
        verifyEmployeeHasAccess(id)
        employeeMapper.updateEmployeeFromDto(employeeRequest,employee);
        if(employeeRequest.getDepartmentId()!=null) {
            def isAdmin=securityUtils.hasRole(Role.ADMIN.name())
            if(!isAdmin){
                throw new AccessDeniedException("No access to change department")
            }
            Department department = departmentService.getDepartmentById(employeeRequest.getDepartmentId());
            employee.setDepartment(department);
            employee.getProject().clear();
        }
        return employeeRepository.save(employee);
    }
    @Override
    String deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return "Success";
    }

    @Override
    Employee assignProject(Long id, Project project) {
        Employee employee=getEmployeeById(id);
        if(!employee.getDepartment().getId().equals(project.getDepartment().getId())){
          throw new DifferentDepartmentException("Employee with ID"+id+" cannot be assigned due to different department");
        }
        employee.getProject().add(project);
        return employeeRepository.save(employee);
    }

    @Override
    Employee unAssignProject(Long id, Project project) {
        Employee employee=getEmployeeById(id);
        employee.getProject().remove(project);
        return employeeRepository.save(employee);
    }

    void verifyEmployeeHasAccess(Long employeeId){
        def userAuth=securityUtils.getPrincipal()
        def isAdmin=securityUtils.hasRole(Role.ADMIN.name())
        if(!isAdmin && userAuth.getEmployee().getId()!=employeeId){
            throw new AccessDeniedException("Employee doesn't have access")
        }
    }
}

package com.luv2code.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    private EmployeeDAO employeeDAO;

    // quick and dirty: inject employee dao(use constructor injection)
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // expose "/employees" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // add mapping for GET /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        return theEmployee;
    }

    //add mapping for PATCH /employees/{employeeId} -patch employee
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String, Object> patchPayLoad) {
        Employee tempEmployee = employeeService.findById(employeeId);
        // throw exception if null
        if (tempEmployee == null)
            throw new RuntimeException("Employee id not found-" + employeeId);

        // throw exception if request body contains "id" key
        if (patchPayLoad.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body-" + employeeId);
        }
        Employee patchEmployee = apply(patchPayLoad, tempEmployee);
        Employee dbEmployee = employeeService.save(patchEmployee);
        return dbEmployee;

       

    }
        private Employee apply (Map < String, Object > patchPayLoad, Employee tempEmployee){
            // Convert employee object to a JSOn object node
            ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);
            // Convert the patchPayLoad map to a JSON object node
            ObjectNode patchNode = objectMapper.convertValue(patchPayLoad, ObjectNode.class);
            // Merge the patch updates into the employee node
            employeeNode.setAll(patchNode);
            return objectMapper.convertValue(employeeNode, Employee.class);
        }


}

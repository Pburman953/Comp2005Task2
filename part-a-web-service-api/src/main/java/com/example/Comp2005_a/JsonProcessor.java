package com.example.Comp2005_a;

import com.example.Comp2005_a.Models.Admission;
import com.example.Comp2005_a.Models.Allocation;
import com.example.Comp2005_a.Models.Employee;
import com.example.Comp2005_a.Models.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonProcessor {

    public List<Admission> admissionList = new ArrayList<>();
    public List<Allocation> allocationList = new ArrayList<>();
    public List<Employee> employeeList = new ArrayList<>();
    public List<Patient> patientList = new ArrayList<>();


    private final ObjectMapper objectMapper = new ObjectMapper();

    public void JsonToModelConverter(String modelName, String jsonString) throws JsonProcessingException {
        switch(modelName) {
            case "Admission":
                List<Admission> admissions = objectMapper.readValue(jsonString, new TypeReference<List<Admission>>(){});
                admissionList.addAll(admissions);
                break;
            case "Allocation":
                List<Allocation> allocations = objectMapper.readValue(jsonString, new TypeReference<List<Allocation>>(){});
                allocationList.addAll(allocations);
                break;
            case "Employee":
                List<Employee> employees = objectMapper.readValue(jsonString, new TypeReference<List<Employee>>(){});
                employeeList.addAll(employees);
                break;
            case "Patient":
                List<Patient> patients = objectMapper.readValue(jsonString, new TypeReference<List<Patient>>(){});
                patientList.addAll(patients);
                break;
            default:
                // Handle unrecognized modelName
        }
    }



}

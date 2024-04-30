package com.example.Comp2005_a;

import com.example.Comp2005_a.Models.Admission;
import com.example.Comp2005_a.Models.Allocation;
import com.example.Comp2005_a.Models.Employee;
import com.example.Comp2005_a.Models.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class maternityAPIService {


    public int errorCode = 0;
    private final RestTemplate restTemplate;
    private final String apiUrl; // Base URL of the external API

    public maternityAPIService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public Patient getPatientDetails(String forename, String surname) throws IOException {
        Patient foundPatient = null;


        JsonProcessor newJsonProcessor = new JsonProcessor();
        ApiController newApiController = new ApiController(restTemplate);

        String JsonTobeConverted_P = newApiController.fetchDataFromExternalApi("/Patients");
        newJsonProcessor.JsonToModelConverter( "Patient", JsonTobeConverted_P);

        for (Patient patient : newJsonProcessor.patientList) {
            if (patient.getForename().equals(forename) && patient.getSurname().equals(surname)) {
                foundPatient = patient;
                break;
            }
        }

        return foundPatient;
    }

    public List<Admission> F1(String forename, String surname) throws IOException {
        JsonProcessor newJsonProcessor = new JsonProcessor();
        ApiController newApiController = new ApiController(restTemplate);

        List<Admission> patientsAdmissions = new ArrayList<>();

        String JsonTobeConverted_P = newApiController.fetchDataFromExternalApi("/Patients");
        String JsonTobeConverted_A = newApiController.fetchDataFromExternalApi("/Admissions");


        newJsonProcessor.JsonToModelConverter( "Patient", JsonTobeConverted_P);
        newJsonProcessor.JsonToModelConverter( "Admission", JsonTobeConverted_A);

        Patient foundPatient = null;

        int foundPatientId = -1;

        for (Patient patient : newJsonProcessor.patientList) {
            if (patient.getForename().equals(forename) && patient.getSurname().equals(surname)) {
                foundPatient = patient;
                break;
            }
        }

        if (foundPatient != null) {
            foundPatientId = foundPatient.getId();
            System.out.println("Patient found with ID: " + foundPatientId);
            errorCode = 200;
        } else {
            System.out.println("Patient with name '" + forename + "' not found.");
            errorCode = 404;
        }

        if(foundPatientId != -1) {
            for (Admission admission : newJsonProcessor.admissionList) {
                if (admission.getPatientID() == (foundPatientId)) {
                    patientsAdmissions.add(admission);
                }
            }
        }
        return patientsAdmissions;
    }

    public List<Patient> F2() throws IOException {
        List<Patient> currentlyAdmitted = new ArrayList<>();

        JsonProcessor newJsonProcessor = new JsonProcessor();
        ApiController newApiController = new ApiController(restTemplate);

        String JsonTobeConverted_P = newApiController.fetchDataFromExternalApi("/Patients");
        String JsonTobeConverted_A = newApiController.fetchDataFromExternalApi("/Admissions");


        newJsonProcessor.JsonToModelConverter( "Patient", JsonTobeConverted_P);
        newJsonProcessor.JsonToModelConverter( "Admission", JsonTobeConverted_A);

        int admittedPatientID = -1;


        for(Admission admission : newJsonProcessor.admissionList){

            boolean isNotDischarged = admission.getDischargeDate().equals("0001-01-01T00:00:00");

            if (isNotDischarged && admission.getPatientID() != -1) {

                for (Patient patient : newJsonProcessor.patientList) {
                    if (admission.getPatientID() == (patient.getId()) && !currentlyAdmitted.contains(patient)) {
                        currentlyAdmitted.add(patient);
                    }
                }

            }
        }
/*
        for(Patient patient : currentlyAdmitted)
        {
            System.out.println(patient.getId());
        }
*/
        return currentlyAdmitted;
    }

    public Employee F3() throws IOException {


        JsonProcessor newJsonProcessor = new JsonProcessor();
        ApiController newApiController = new ApiController(restTemplate);

        String JsonTobeConverted_Al = newApiController.fetchDataFromExternalApi("/Allocations");
        String JsonTobeConverted_E = newApiController.fetchDataFromExternalApi("/Employees");

        newJsonProcessor.JsonToModelConverter( "Allocation", JsonTobeConverted_Al);
        newJsonProcessor.JsonToModelConverter( "Employee", JsonTobeConverted_E);

        Employee mostAdmission_E = null;
        int mostAl = 0;
        for (Employee employee : newJsonProcessor.employeeList)
        {
            int count = 0;

            for (Allocation allocation : newJsonProcessor.allocationList)
            {
                if(allocation.getEmployeeId() == employee.getId()){
                    count = count + 1;
                }
            }

            if(mostAl < count){
                mostAl = count;
                mostAdmission_E = employee;
            }

        }

        return mostAdmission_E;
    }

    public List<Employee> F4() throws IOException {
        JsonProcessor newJsonProcessor = new JsonProcessor();
        ApiController newApiController = new ApiController(restTemplate);

        String JsonTobeConverted_Al = newApiController.fetchDataFromExternalApi("/Allocations");
        String JsonTobeConverted_E = newApiController.fetchDataFromExternalApi("/Employees");

        newJsonProcessor.JsonToModelConverter( "Allocation", JsonTobeConverted_Al);
        newJsonProcessor.JsonToModelConverter( "Employee", JsonTobeConverted_E);

        List<Employee> zeroAdmissions_E = new ArrayList<>();

        List<Integer> allocated_E = new ArrayList<Integer>();

        for (Allocation allocation : newJsonProcessor.allocationList)
        {
            allocated_E.add(allocation.getEmployeeId());
        }

        for (Employee employee : newJsonProcessor.employeeList)
        {
            if(!allocated_E.contains(employee.getId())){
                zeroAdmissions_E.add(employee);
            }

        }

        return zeroAdmissions_E;
    }


}


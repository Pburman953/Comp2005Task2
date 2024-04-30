package com.example.Comp2005_a;

import com.example.Comp2005_a.Models.Admission;
import com.example.Comp2005_a.Models.Employee;
import com.example.Comp2005_a.Models.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Comp2005ApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void testApiConnection_UNIT() {
		String url = "https://web.socem.plymouth.ac.uk/COMP2005/api/";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		// Verify the response status code is OK
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@MockBean
	private RestTemplate MockRestTemplate;

	@Test
	public void testFetchDataFromExternalApi_UNIT() throws IOException {
		MockitoAnnotations.openMocks(this);

		String apiUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api";
		String requestExt = "/Employees";
		String responseData = "[{\"id\":1,\"surname\":\"Finley\",\"forename\":\"Sarah\"},{\"id\":2,\"surname\":\"Jackson\",\"forename\":\"Robert\"},{\"id\":3,\"surname\":\"Allen\",\"forename\":\"Alice\"},{\"id\":4,\"surname\":\"Jones\",\"forename\":\"Sarah\"},{\"id\":5,\"surname\":\"Wicks\",\"forename\":\"Patrick\"},{\"id\":6,\"surname\":\"Smith\",\"forename\":\"Alice\"}]";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);

		// Mock the response entity for the restTemplate.getForEntity call
		//when(MockRestTemplate.getForEntity(apiUrl + requestExt, String.class)).thenReturn(responseEntity);

		ApiController apiController = new ApiController(MockRestTemplate);

		// Call the fetchDataFromExternalApi method
		String result = apiController.fetchDataFromExternalApi(requestExt);

		System.out.println("Fetched data from API: " + result);

		// Verify that the response from the API matches the result from the method
		assertEquals(responseData, result);
	}


	@Test
	public void testF1_UNIT() throws IOException {

		String mockResponseFromAPI_Admins = "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2},{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1},{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2},{\"id\":4,\"admissionDate\":\"2024-02-23T21:50:00\",\"dischargeDate\":\"2024-09-27T09:56:00\",\"patientID\":5},{\"id\":5,\"admissionDate\":\"2024-04-12T22:55:00\",\"dischargeDate\":\"2024-04-14T11:36:00\",\"patientID\":5},{\"id\":6,\"admissionDate\":\"2024-04-19T21:50:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":5}]\n";
		String mockResponseFromAPI_Patients = "[{\"id\":1,\"surname\":\"Robinson\",\"forename\":\"Viv\",\"nhsNumber\":\"1113335555\"},{\"id\":2,\"surname\":\"Carter\",\"forename\":\"Heather\",\"nhsNumber\":\"2224446666\"},{\"id\":3,\"surname\":\"Barnes\",\"forename\":\"Nicky\",\"nhsNumber\":\"6663338888\"},{\"id\":4,\"surname\":\"King\",\"forename\":\"Jacky\",\"nhsNumber\":\"7773338888\"},{\"id\":5,\"surname\":\"Sharpe\",\"forename\":\"Rhi\",\"nhsNumber\":\"6663339999\"}]\n";

		String expectedResult = "[{\"id\":4,\"admissionDate\":\"2024-02-23T21:50:00\",\"dischargeDate\":\"2024-09-27T09:56:00\",\"patientID\":5},{\"id\":5,\"admissionDate\":\"2024-04-12T22:55:00\",\"dischargeDate\":\"2024-04-14T11:36:00\",\"patientID\":5},{\"id\":6,\"admissionDate\":\"2024-04-19T21:50:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":5}]";

		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");
		ApiController mockAPI = mock(ApiController.class);

		when(mockAPI.fetchDataFromExternalApi("/Patients")).thenReturn(mockResponseFromAPI_Patients);
		when(mockAPI.fetchDataFromExternalApi("/Admissions")).thenReturn(mockResponseFromAPI_Admins);

		List<Admission> result = classUnderTest.F1("Rhi", "Sharpe");

		ObjectMapper ob = new ObjectMapper();
		String resultAsString = ob.writeValueAsString(result);

		assertEquals(expectedResult, resultAsString);

	}

	@Test
	public void testF2_UNIT() throws IOException{

		String mockResponseFromAPI_Admins = "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2},{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1},{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2},{\"id\":4,\"admissionDate\":\"2024-02-23T21:50:00\",\"dischargeDate\":\"2024-09-27T09:56:00\",\"patientID\":5},{\"id\":5,\"admissionDate\":\"2024-04-12T22:55:00\",\"dischargeDate\":\"2024-04-14T11:36:00\",\"patientID\":5},{\"id\":6,\"admissionDate\":\"2024-04-19T21:50:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":5}]\n";
		String mockResponseFromAPI_Patients = "[{\"id\":1,\"surname\":\"Robinson\",\"forename\":\"Viv\",\"nhsNumber\":\"1113335555\"},{\"id\":2,\"surname\":\"Carter\",\"forename\":\"Heather\",\"nhsNumber\":\"2224446666\"},{\"id\":3,\"surname\":\"Barnes\",\"forename\":\"Nicky\",\"nhsNumber\":\"6663338888\"},{\"id\":4,\"surname\":\"King\",\"forename\":\"Jacky\",\"nhsNumber\":\"7773338888\"},{\"id\":5,\"surname\":\"Sharpe\",\"forename\":\"Rhi\",\"nhsNumber\":\"6663339999\"}]\n";

		String expectedResult = "[{\"id\":1,\"surname\":\"Robinson\",\"forename\":\"Viv\",\"nhsNumber\":\"1113335555\"},{\"id\":5,\"surname\":\"Sharpe\",\"forename\":\"Rhi\",\"nhsNumber\":\"6663339999\"}]";

		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");
		ApiController mockAPI = mock(ApiController.class);

		when(mockAPI.fetchDataFromExternalApi("/Patients")).thenReturn(mockResponseFromAPI_Patients);
		when(mockAPI.fetchDataFromExternalApi("/Admissions")).thenReturn(mockResponseFromAPI_Admins);

		List<Patient> result = classUnderTest.F2();
		ObjectMapper ob = new ObjectMapper();

		String resultAsString = ob.writeValueAsString(result);
		assertEquals(expectedResult, resultAsString);
	}

	@Test
	public void testF3_UNIT() throws IOException{

		String mockResponseFromAPI_Allocations = "[{\"id\":1,\"admissionID\":1,\"employeeID\":4,\"startTime\":\"2020-11-28T16:45:00\",\"endTime\":\"2020-11-28T23:56:00\"},{\"id\":2,\"admissionID\":3,\"employeeID\":4,\"startTime\":\"2021-09-23T21:50:00\",\"endTime\":\"2021-09-24T09:50:00\"},{\"id\":3,\"admissionID\":2,\"employeeID\":6,\"startTime\":\"2020-12-07T22:14:00\",\"endTime\":\"2020-12-08T20:00:00\"},{\"id\":4,\"admissionID\":2,\"employeeID\":3,\"startTime\":\"2020-12-08T20:00:00\",\"endTime\":\"2020-12-09T20:00:00\"}]\n";
		String mockResponseFromAPI_Employees = "[{\"id\":1,\"surname\":\"Finley\",\"forename\":\"Sarah\"},{\"id\":2,\"surname\":\"Jackson\",\"forename\":\"Robert\"},{\"id\":3,\"surname\":\"Allen\",\"forename\":\"Alice\"},{\"id\":4,\"surname\":\"Jones\",\"forename\":\"Sarah\"},{\"id\":5,\"surname\":\"Wicks\",\"forename\":\"Patrick\"},{\"id\":6,\"surname\":\"Smith\",\"forename\":\"Alice\"}]";

		String expectedResult = "{\"id\":4,\"surname\":\"Jones\",\"forename\":\"Sarah\"}";

		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");
		ApiController mockAPI = mock(ApiController.class);

		when(mockAPI.fetchDataFromExternalApi("/Allocations")).thenReturn(mockResponseFromAPI_Allocations);
		when(mockAPI.fetchDataFromExternalApi("/Employees")).thenReturn(mockResponseFromAPI_Employees);

		Employee result = classUnderTest.F3();
		ObjectMapper ob = new ObjectMapper();

		String resultAsString = ob.writeValueAsString(result);
		assertEquals(expectedResult, resultAsString);
	}

	@Test
	public void testF4_UNIT() throws IOException{

		String mockResponseFromAPI_Allocations = "[{\"id\":1,\"admissionID\":1,\"employeeID\":4,\"startTime\":\"2020-11-28T16:45:00\",\"endTime\":\"2020-11-28T23:56:00\"},{\"id\":2,\"admissionID\":3,\"employeeID\":4,\"startTime\":\"2021-09-23T21:50:00\",\"endTime\":\"2021-09-24T09:50:00\"},{\"id\":3,\"admissionID\":2,\"employeeID\":6,\"startTime\":\"2020-12-07T22:14:00\",\"endTime\":\"2020-12-08T20:00:00\"},{\"id\":4,\"admissionID\":2,\"employeeID\":3,\"startTime\":\"2020-12-08T20:00:00\",\"endTime\":\"2020-12-09T20:00:00\"}]\n";
		String mockResponseFromAPI_Employees = "[{\"id\":1,\"surname\":\"Finley\",\"forename\":\"Sarah\"},{\"id\":2,\"surname\":\"Jackson\",\"forename\":\"Robert\"},{\"id\":3,\"surname\":\"Allen\",\"forename\":\"Alice\"},{\"id\":4,\"surname\":\"Jones\",\"forename\":\"Sarah\"},{\"id\":5,\"surname\":\"Wicks\",\"forename\":\"Patrick\"},{\"id\":6,\"surname\":\"Smith\",\"forename\":\"Alice\"}]";

		String expectedResult = "[{\"id\":1,\"surname\":\"Finley\",\"forename\":\"Sarah\"},{\"id\":2,\"surname\":\"Jackson\",\"forename\":\"Robert\"},{\"id\":5,\"surname\":\"Wicks\",\"forename\":\"Patrick\"}]";

		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");
		ApiController mockAPI = mock(ApiController.class);

		when(mockAPI.fetchDataFromExternalApi("/Allocations")).thenReturn(mockResponseFromAPI_Allocations);
		when(mockAPI.fetchDataFromExternalApi("/Employees")).thenReturn(mockResponseFromAPI_Employees);

		List<Employee> result = classUnderTest.F4();
		ObjectMapper ob = new ObjectMapper();

		String resultAsString = ob.writeValueAsString(result);
		assertEquals(expectedResult, resultAsString);
	}

	@Test
	public void testF1_INTEGRATION_F1_API() throws IOException {
		MockitoAnnotations.openMocks(this);

		ApiController newApiController = new ApiController(MockRestTemplate);

		// Create an instance of the class under test
		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");

		// Call the method under test
		List<Admission> result = classUnderTest.F1("Heather", "Carter");

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId());
	}

	@Test
	public void testF2_INTEGRATION_F1_API() throws IOException {
		MockitoAnnotations.openMocks(this);


		// Create an instance of the class under test
		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");

		// Call the method under test
		List<Patient> result = classUnderTest.F2();

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId());
		assertEquals(5, result.get(1).getId());

	}
	@Test
	public void testF3_INTEGRATION_F1_API() throws IOException {
		MockitoAnnotations.openMocks(this);


		// Create an instance of the class under test
		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");

		// Call the method under test
		Employee result = classUnderTest.F3();

		// Verify the result
		assertEquals(4, result.getId());
	}

	@Test
	public void testF4_INTEGRATION_F1_API() throws IOException {
		MockitoAnnotations.openMocks(this);


		// Create an instance of the class under test
		maternityAPIService classUnderTest = new maternityAPIService(MockRestTemplate, "https://web.socem.plymouth.ac.uk/COMP2005/api");

		// Call the method under test
		List<Employee> result = classUnderTest.F4();

		// Verify the result
		assertEquals(3, result.size());
		assertEquals(1,result.get(0).getId());
		assertEquals(2,result.get(1).getId());
		assertEquals(5,result.get(2).getId());

	}
}

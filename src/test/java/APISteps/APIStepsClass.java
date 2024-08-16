package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class APIStepsClass  {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    RequestSpecification request;
    Response response;
    public static String token;
    public static String emp_id;

    @Given("a JWT bearer token is generated")
    public void a_jwt_bearer_token_is_generated() {
        request = given().
                header(APIConstants.HEADER_CONTENT_TYPE_KEY,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                body("{\n" +
                        "  \"email\": \"nadyktoo@gmail.com\",\n" +
                        "  \"password\": \"test@123\"\n" +
                        "}");

        response = request.when().post(APIConstants.GENERATE_TOKEN_URI);
        response.then().assertThat().statusCode(200);
        token = "Bearer " + response.jsonPath().getString("token");
    }


    @Given("a request is prepared for creating a employee")
    public void aRequestIsPreparedForCreatingAEmployee() {
        request = given().
                header(APIConstants.HEADER_CONTENT_TYPE_KEY,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                header(APIConstants.HEADER_AUTHORIZATION_KEY,token).
                body(APIPayloadConstants.createEmployeePayload());
    }

    @When("a Post call is made to create the employee")
    public void a_post_call_is_made_to_create_the_employee() {
        response = request.when().post(APIConstants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();
    }

    @Then("the status code will be {int} for this call")
    public void the_status_code_will_be_for_this_call(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value
            (String key, String value) {response.then().assertThat().body(key,equalTo(value));
    }

    @Then("the employee id {string} is stored as a global variable")
    public void the_employee_id_is_stored_as_a_global_variable(String empIdpath) {
        emp_id = response.jsonPath().getString(empIdpath);
        System.out.println(emp_id);
    }

    @Given("a request is prepared for retreiving a employee")
    public void a_request_is_prepared_for_retreiving_a_employee() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                queryParam("employee_id",emp_id);
    }

    @When("a Get call is made to get the employee")
    public void a_get_call_is_made_to_get_the_employee() {
        response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();
    }
    @Then("the status code will be {int}")
    public void the_status_code_will_be(Integer int1) {
        response.then().assertThat().statusCode(int1);
    }
    @Then("the employee id we got {string} must match with global id")
    public void the_employee_id_we_got_must_match_with_global_id(String empId) {
        String tempId = response.jsonPath().getString(empId);
        Assert.assertEquals(tempId,emp_id); 
    }
    @Then("the recieved data from {string} object matches with data we used to create employee")
    public void the_recieved_data_from_object_matches_with_data_we_used_to_create_employee(String string, io.cucumber.datatable.DataTable dataTable) {
        throw new io.cucumber.java.PendingException();
    }



}

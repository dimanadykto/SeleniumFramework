package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardcodedExamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjA4MDI0NTgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcyMDg0NTY1OCwidXNlcklkIjoiNjczNCJ9.-FB5EJAutqC1wzOzyfO3UAUtGMXPpN0-yPUwqojpiO4";
    static String employee_id;

    @Test
    public void acreateEmployee() {
        RequestSpecification preparedRequest = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"emp_firstname\": \"Bob\",\n" +
                        "  \"emp_lastname\": \"test\",\n" +
                        "  \"emp_middle_name\": \"good\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2014-03-01\",\n" +
                        "  \"emp_status\": \"employed\",\n" +
                        "  \"emp_job_title\": \"peasent\"\n" +
                        "}");

        Response response = preparedRequest.when().post("/createEmployee.php");
        response.prettyPrint();

        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);
        response.then().assertThat().statusCode(201);

        response.then().assertThat().header("Connection", "Keep-Alive");

        response.then().assertThat().
                body("Employee.emp_firstname", equalTo("Bob"));
        response.then().assertThat().
                body("Employee.emp_middle_name", equalTo("good"));
        response.then().assertThat().
                body("Employee.emp_lastname", equalTo("test"));
        }

    @Test
    //getting one employee
    public void bgetOneEmployee(){
        //prepare the request
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        //hitting the endpoint
        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();

        //validate the status code
        response.then().assertThat().statusCode(200);
        //get the employee id from  body
        String empId = response.jsonPath().getString("employee.employee_id");
        Assert.assertEquals(empId, employee_id);
        response.then().assertThat().
                body("employee.emp_firstname",equalTo("Bob"));
        response.then().assertThat().
                body("employee.emp_middle_name",equalTo("good"));
        response.then().assertThat().
                body("employee.emp_lastname",equalTo("test"));

    }

    @Test
    public void cupdateEmployee() {
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"alex\",\n" +
                        "  \"emp_lastname\": \"khol\",\n" +
                        "  \"emp_middle_name\": \"dima\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2014-07-06\",\n" +
                        "  \"emp_status\": \"here\",\n" +
                        "  \"emp_job_title\": \"lazy\"\n" +
                        "}");

        Response response = request.when().put("/updateEmployee.php");
        response.then().assertThat().statusCode(200);
        response.prettyPrint();


    }

    @Test
    public void dgetUpdatedEmployee() {
        RequestSpecification request = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }





}



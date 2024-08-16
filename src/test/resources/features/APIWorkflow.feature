Feature: API testing

  Background:
    Given a JWT bearer token is generated

  @api
  Scenario: creating an employee
    Given a request is prepared for creating a employee
    When a Post call is made to create the employee
    Then the status code will be 201 for this call
    And the employee created contains key "Message" and value "Employee Created"
    And the employee id "Employee.employee_id" is stored as a global variable

  @api
  Scenario: getting recently created employee
    Given a request is prepared for retreiving a employee
    When a Get call is made to get the employee
    Then the status code will be 200
    And the employee id we got "employee.employee_id" must match with global id
    And the recieved data from "employee" object matches with data we used to create employee
    |emp_firstname|emp_lastname|emp_middle_name|emp_gender|emp_birthday|emp_status|emp_job_title|
    |Bob          |test        |good           |Male      |2014-03-01  |employed  |peasent      |



Rule Engine with AST
A Spring Boot application that evaluates eligibility rules based on user-defined conditions. It parses rules into an Abstract Syntax Tree (AST) structure and evaluates them against user-provided data.

Features
Rule Parsing: Parses logical expressions (e.g., age > 18 AND department = 'Engineering') into an AST.
AST Evaluation: Evaluates logical expressions based on operators like AND, OR, and conditional statements.
REST API & UI: Provides REST endpoints and a simple UI for rule evaluation.

Prerequisites
Java 17+
Maven
MongoDB (running on localhost:27017)

Project Structure
Controller: REST endpoints for rule creation and evaluation.
Service: Handles rule parsing and evaluation logic.
Model: Defines AST node structure and the rule entity.
Repository: Manages rule storage with MongoDB.
UI: Simple HTML/JavaScript interface for rule testing.

Setup
Step 1: Clone the Repository
git clone <repository-url>
cd Rule-Engine-with-AST

Step 2: Configure MongoDB
Ensure MongoDB is running on localhost:27017.

Step 3: Build the Project
mvn clean install

Step 4: Run the Application
mvn spring-boot:run
The application should now be running on http://localhost:8080.

API Endpoints
1. Create Rule
URL: /rules/create
Method: POST
Payload:
{
  "ruleString": "age > 18 AND department = 'Engineering'"
}
Response: 200 OK (returns created rule with AST structure)

3. Evaluate Rule
URL: /rules/evaluate-rule
Method: POST
Payload:
{
  "ruleString": "age > 18 AND department = 'Engineering'",
  "userData": {
    "age": 25,
    "department": "Engineering"
  }
}
Response: true or false based on evaluation

Example curl Commands
Create Rule:
curl -X POST http://localhost:8080/rules/create \
-H "Content-Type: application/json" \
-d '{
    "ruleString": "age > 18 AND department = \"Engineering\""
}'
Evaluate Rule:
curl -X POST http://localhost:8080/rules/evaluate-rule \
-H "Content-Type: application/json" \
-d '{
    "ruleString": "age > 18 AND department = \"Engineering\"",
    "userData": {
        "age": 25,
        "department": "Engineering"
    }
}'

Testing

Run Unit Tests
mvn test

UI Testing
A simple HTML UI (index.html) is provided for rule evaluation, located in src/main/resources/static. Access it at http://localhost:8080/index.html.

Debugging & Logs
Debug logs print the rule parsing and evaluation process. Examples:

Parsing Logs:
Parsing rule to AST: age > 18 AND department = "Engineering"
Evaluation Logs:
Evaluating node: AND (operator)
Evaluating node: age > 18 (operand)

Future Enhancements
Advanced Parsing for nested conditions.
Extended Operators like >=, <=, and !=.
Enhanced UI with better error handling.

Troubleshooting
MongoDB Issues: Verify MongoDB is running on localhost:27017.
Parsing Errors: Ensure rule strings follow the correct format (e.g., age > 18 AND department = 'Engineering').
Evaluation Errors: Review logs to identify any parsing or evaluation issues.

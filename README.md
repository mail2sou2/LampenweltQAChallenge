# Lampenwelt QA Challenge

## Code Assignment

### Pre-Requisites

1. Install Chrome Browser (version 100 or above)
2. Install Java 11 and set up your environment variables
3. Install Maven and set up your environment variables
4. You may use the terminal or any IDE of your choice to run the project

### Project Specification

1. Language: Selenium WebDriver with Java 11
2. Build tool: Maven
3. Framework: TestNG Framework using Java and Maven
4. Report path: \\Report\\{Date of execution}\\Report_{timestamp}.html
5. Browser used: Chrome Version: 100 (Others browsers like Safari, Edge or Firefox can also be configured based on the
   scope of cross browser testing)

### Getting started with the framework

1. Clone the project from GitHub using: git clone https://github.com/mail2sou2/LampenweltQAChallenge.git
2. Open the project in your desired IDE and resolve the maven dependencies.
3. If you are using Eclipse IDE, then once the project is imported please follow these steps
    1. Project --> Clean
    2. Right click on project: Maven --> Update dependencies
    3. Right click on project: Maven --> Update project configuration
4. While setting up the framework, disconnect any VPN or go behind the firewall as it may not let the pom.xml to connect
   to Maven repo and download the dependencies of the project. This is very important to ensure that you download and
   install all the dependencies
5. Once all maven dependencies are downloaded, update or refresh the project

### Steps to Run the project

1. To run the project, go the directory where the project is stored and open a terminal there. Run maven
   lifecycle <I><B>mvn clean install</I></B>
2. You can also use IDE and run the <I><B>testng.xml</I></B> present in the project
3. If you face this error "Error: Could not find or load main class org.testng.remote.RemoteTestNG" while running the
   testng.xml in eclipse IDE, then right-click on the project, select "Properties", click on "TestNG", uncheck
   checkbox "Use Project TestNG jar", click on "Apply" and then "Close". Run the testng.xml again, and it should solve
   the issue

### Generating Report

1. Once the test execution is complete the reports can be found in this path: <I><B>\\Report\\{Date of
   execution}\\Report_{timestamp}.html</I></B>

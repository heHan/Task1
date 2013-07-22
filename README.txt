OVERVIEW
This project is an example of using webdriver and testng to autoamtion web pages and service response testing. Any pages should extend AbstractBasePage and add its own functionalities.
Ant testng test should extend abstractWebDriverBase, which the abstract class has before method and after method default behavior. There is capture screenshot on failure in the after method as well


CONFIGURE AND RUN
run both tests, please use
mvn test -Dtest.XMLFILE=assignment.xml
run task1
mvn test -Dtest.XMLFILE=task1.xml
run task2
mvn test -Dtest.XMLFILE=task2.xml

This project contains two test cases. 
TestAddApplication has following steps:
1. Load pingone login page: https://test-admin.pingone.com/web-portal/login
2. Login in with account: mdorey+hhan+aps@pingidentity.com/Password123!
3. Click my application tab on home page
4. Click add new application button
5. Select category: Other
6. Enter the application name 
7. Enter application description: A short description
8. Select private visibility
9. Click Continue to Step 2
10. Choose enable SAML through PingOne 
10. Enter domain name: example.com
11. Enter application url: http://myapp.example.com/sso.php
12. Click Continue to Step 3
13. Click Continue to Step 5
14. Save application
Verify new application is created
Delete application
TestSSO has folllowing steps:
Steps
1. Load pingone login page: https://test-admin.pingone.com/web-portal/login
2. Login in with account: mdorey+hhan+aps@pingidentity.com/Password123!
3. Click my application tab on home page
4. Click test button on application SampleAPSEnabledApp
5. Click start SSO link on popup
6. Wait for the idp appication to load; and click login with default user/pass
7. Verify tokenId is in the redirect URL: https://myapp.example.com/sso.php?tokenid=ID9003906d8def3a7cbf9862addac110ebb01efa1095fa5fed01&agentid=9ff8cc0d
8. Retrieve token using tokenId and verify token

CONFIGURE AND RUN
run both tests, please use
mvn test -Dtest.XMLFILE=assignment.xml
run task1
mvn test -Dtest.XMLFILE=task1.xml
run task2
mvn test -Dtest.XMLFILE=task2.xml

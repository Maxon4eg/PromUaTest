Tests are using imap connection to gmail, so it demands some pre-configuration with email passwords
 and email inbox
 all received email's form prom.ua should be filterred with "promUA" label 
 in google mail settigs should be opened ability to connect untrusted app 
 and imap connection should be turned on  
also need to pre-configure application.properties file 

for running tests 
`mvn clean test `

for generating report after need to run  
`mvn allure:serve`

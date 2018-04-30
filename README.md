# Simple-Taxi-Booking-System
A simple taxi booking system using Spring boot.


1) Code - Contains the actual code (both code directory and zip of code directory)
2) Documentation - Contains the design document 
3) Executable Jar - Contains project jar file 

FAQ-
1) How to configure the project?
- Install any IDE to import the project (example IntelliJ IDEA, Eclipse)
- Use open project option to import the project

2) How to run the project in Linux?
- Run the executable jar provided in the project folder
- Use commands provided below to execute in terminal
	1) Reset api:-
	curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/api/reset

	2) Book api:-
	curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/api/book/ -d '{ "source" :{ "x" :-3, "y" :-5}, "destination": { "x":-3, "y":-6} }'

	3) Check api:-
	curl http://localhost:8080/api/check

	4) Tick api
	curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/api/tick


3) How to run the project in windows?
- Once the sucessful build is completed click on run 

- Go to browser and run below urls:
	1) Reset:- http://localhost:8080/api/reset
	2) Check:- http://localhost:8080/api/check
	To run book api, please download Postman
	3) Book: - http://localhost:8080/api/book/
	Body:- { "source" :{ "x" :-3, "y" :-5}, "destination": { "x":-3, "y":-6} }
	4) Tick:- http://localhost:8080/api/tick

If you need assistance at any step, please contact Siddhant Tanpure at sbtanpur@asu.edu.
Thank you! Enjoy!



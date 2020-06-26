# Web_Quiz_Engine_REST-API
Web_Quiz_Engine_REST-API ()
Project from https://hyperskill.org/

Spring boot;

MVC

Security(DB auth BCrypt);

H2 DB;

Gradle;



TO REGISTER A NEW USER, the client needs to send a JSON with email and password via 
POST request to http://localhost:8889/api/register:
{
  "email": "test@example.com",
  "password": "P@ssword"
}
The service returns 200 (OK) if the registration has been completed 
successfully. Or if the email is already taken, the service will 
return the 400 (Bad request).
The email must have a valid format (with @ and .);
The password must have at least five characters.



TO CREATE A NEW QUIZ, the client need to send a JSON as the request's body via
POST to http://localhost:8889/api/quizzes. The JSON should contain the four fields:
title (a string), 
text (a string), 
options (an array of strings, required, should contain at least 2 items) and 
answer (an array of indexes of correct options. Can be empty or null).
Here is a new JSON quiz as an example:
{
  "title": "Some Title",
  "text": "Text of quiz",
  "options": ["first options","second options","third options","forth options"],
  "answer": [0,2]
}



TO SOLVE THE QUIZ, the client sends a POST request to 
http://localhost:8889/api/quizzes/{id}/solve with a JSON that contains the indexes 
of all chosen options as the answer. This looks like a regular JSON object with key 
"answer" and value as the array: {"answer": [0,2]}. If the passed answer is correct:
{
  "success":true,"feedback":"Congratulations, you're right!"
}
If the answer is incorrect:
{
  "success":false,"feedback":"Wrong answer! Please, try again."
}



TO GET A QUIZ BY ID, the client sends the GET request to http://localhost:8889/api/quizzes/{id}.



TO GET ALL EXISTING QUIZZES in the service, the client sends the GET request to
http://localhost:8889/api/quizzes.
The response contains a JSON array of quizzes.
API support the navigation through pages by passing the page parameter 
( /api/quizzes?page=1). If there are no quizzes, content is empty []. 
If the user is authorized, the status code is 200 (OK); otherwise, 
it's 401 (Unauthorized).



TO DELETE USER'S QUIZ client needs to send the DELETE request to 
http://localhost:8889/api/quizzes/{id}.
If the operation was successful, the service returns the 204 (No content).
If the specified quiz does not exist, the server returns 404 (Not found). 
If the specified user is not the author of this quiz, the response is the 403 (Forbidden).



TO GET ALL COMPLETIONS OF QUIZZES for a specified user, client needs to send the 
GET request to http://localhost:8889/api/quizzes/completed. All the completions 
will be sorted from the most recent to the oldest.
API support the navigation through pages by passing the page parameter 
( /api/quizzes?page=1).















# Digibooky

Daily Stand-Up:

Daily stand up at 9.05 am <br>
different person as scrum master each day  <br>
<h3>daily stand up : </h3>
- recap of the day before with a code reviews.
- the daily tasks for each person.
- Set who gonna do wich task.
- speak about pair programming. not an obligation bt if someone struggle -> call others.
- code reviews

<h3>Commit messages :</h3>
- [IMP] for improvements <br>
- [FIX] for bug fixes<br>
- [REF] for refactoring<br>
- [ADD] for adding new resources<br>
- [REM] for removing of resources<br>
- [MOV] for moving files (Do not change content of moved
  file, otherwise Git will loose track, and the
  history will be lost !), or simply moving code from
  a file to another one.
- [MERGE] for merge commits (only for forward/back-port)<br>
- [CLA] for signing the Odoo Individual Contributor License


<h3>Stories :</h3>

<h3> 1</h3>
- class book & bookRepository  -> mapper hmap with isbn as the unique key/ class author & authorRepository / String ISBN -> unique
<h3>2 </h3>
- small sumary for each book when the user get a single book.
<h3>3</h3>
- find a book with isbn / method in service / controller
<h3>4</h3>
- same above
<h3>5</h3>
- same above
<h3>6a</h3>
- enum for the level of a user.
- UUID for the unique id for a new user.
- UUID with user class to be associated to a firstname lastname email inss adress.
- adress should be an object for the differents fields : street name, number, postal code, city.
- email adress should be xxx@xxxx.xx  String have to match a regex. And should be unique.
- when connected -> connect to a random user as a normal member
- to connect as a admin -> just with /login method to connect as a specifiq member.
<h3>7 </h3>
- in service have a user logged in where we check the user rights
- to check if a user have wich rights -> get the enum level.
- to do a request only for admin -> Give a header with logs when requesting in postman.
<h3>9</h3>
- check in the header for the logs in postman to register a new librarian.
<h3>10A,B,C </h3>
- need the logs as librarian or admin to do the request with the logs as header. For update shoulnt be possible to change the ISBN.
- For Delete: not complete delete but soft -> change a boolean isExisting to false and cannot be found in the search.
<h3>11</h3>
- provide UUID of the user and the ISBN of the book lend.
- class rental & rentalrepository , int numberIsStore -> each book can be rent just once.
<h3>12</h3> 
- Post check the return date for too late return -> check with the UUID user.
<h3>13</h3>
- GetAllRented as a librarian or admin.
<h3>14</h3>
- GetALLRented with boolean above.
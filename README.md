# PhoneBook

Simple phone book application allowing user to store his contacts. 

User can add a new contact to the phone book by specifying person's first name, last name and the phone number. All three input fields must be filled to add a new contact to the phone book. 
Phone number is an unique id for the whole phone book base and cannot be added more than once. It must consist of 9 digits (Polish locale-specific data). No other sequence is allowed.
First name and last name may repeat any number of times and in any combination that is desired. What's more, first name and last name may be an arbitrary name but must consist of a single word each.
The phone base may be searched by number or by first name or by last name or by first and last name together. 
No more than 2 words might be entered. The sequence of first and last name typed into a search bar is not important. Searching is case-insensitive.

PhoneBook project is made with JavaFX technology and uses "plain" Java code for GUI building (for exercising purposes). JavaFX Scene Builder haven't been used.

It also uses some basic Spring Framework features (Spring Core) i.e, dependency injection (with annotation-based configuration), Lombok project features and opencsv parser library for Java.

Project #003.

----

TODO in the future releases:
- logging,
- unit testing.

----

This project and the solutions it introduces, like every single one in my repositories, was not based on any ready-made internet or book project sample, any tutorial or anyone else idea other than me. All solutions are made by me, to practice coding and solving problems. Google was a friend of mine (of course) but all the implemented features in my projects are developed or implemented solely by me. That means I did not used any ready project templates from any external sources.

----

Some more inof about PhoneBook project developing process.
From the very beginning this project was supposed to read data from a .txt file. When it was started I didn't know much how to use Hibernate. It's different now but.. It would need fundamental changes now to use Hibernate features and connect it to some SQL database. That's why, I won't do that. I'm going to develope a Web version using Hibernate and MySQL instead with some new features too. This will be done for exercising purpose in the future, coz some other projects come to my mind in the first place now.
This project is the best example for me, how NOT start developing an application. I had a lot of good time with it, I learned many important things developing it but I made one fundamental error: I did not make a map what should be done (I mean what classes, interfaces etc.) and how all these should interact. That's why, while the project was in progress, more and more unpredicted problems were arising. All of them were fast resolved but I guess this is not how it should be. The price are also some solutions that should be implemented in a different manner but on the actual stage of developement, changing things would cost me a lot of additional time. Unfortunately, time is always the enemy. Anyway, I did my best, app works fine, it does works and I`m really happy with it. Quite a good job though, imho. Ok, not much modest this time:-)

----

Take a look at these sample screenshots of this app:
<a target="_blank" href="https://drive.google.com/open?id=1A-9bjH55MHVsyTCw4CNaUhznBNXlunLN">Screenshots of PhoneBook app</a>
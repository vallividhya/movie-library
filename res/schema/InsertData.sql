INSERT INTO VideoLibrary.Catagory(CatagoryName)
Values('Drama');
INSERT INTO VideoLibrary.Catagory(CatagoryName)
Values('Romance');
INSERT INTO VideoLibrary.Catagory(CatagoryName)
Values('Comedy');
INSERT INTO VideoLibrary.Catagory(CatagoryName)
Values('Thriller');
INSERT INTO VideoLibrary.Catagory(CatagoryName)
Values('Documentry');


INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)
VALUES ('Ganesha','Ashtavinayak',NOW(),1.5,5,1);

INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)
VALUES ('Jaws','Spillberg',NOW(),1.5,5,4);

INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)
VALUES ('Titanic','CameronGroup',NOW(),1.5,5,2);

INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)
VALUES ('Funny','ABC',NOW(),1.5,5,3);

INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)
VALUES ('Space','NASA',NOW(),1.5,5,5);

INSERT INTO VideoLibrary.Person (UserId,Password,MembershipType,StartDate,FirstName,LastName,Address,City,State,Zip,CreditcardNumber)
VALUES ('sr@yahoo.com','sr123','Simple',NOW(),'Shri','Ram','Washington Ave,Apt 1234','San Jose','CA','93457','1234567890123456');

INSERT INTO VideoLibrary.Person (UserId,Password,MembershipType,StartDate,FirstName,LastName,Address,City,State,Zip,CreditcardNumber)
VALUES ('ab@yahoo.com','ab123','Premium',NOW(),'Abey','Brin','Newark Ave,674','Sacramento','CA','93347','1234512345123456');

INSERT INTO VideoLibrary.Person (UserId,Password,MembershipType,StartDate,FirstName,LastName,Address,City,State,Zip,CreditcardNumber)
VALUES ('xy@yahoo.com','xy123','Simple',NOW(),'Xiao','Yang','Rodeo Dr,123','Los Angelos','CA','91937','1234567890567890');

INSERT INTO VideoLibrary.Admin(UserId,Password,FirstName,Lastname)
VALUES('admin1','admin1','Dan','Smith');

INSERT INTO VideoLibrary.Admin(UserId,Password,FirstName,Lastname)
VALUES('admin2','admin2','Riya','Shah');



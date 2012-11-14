INSERT INTO VideoLibrary.Category(categoryName)
Values('Drama');
INSERT INTO VideoLibrary.Category(categoryName)
Values('Romance');
INSERT INTO VideoLibrary.Category(categoryName)
Values('Comedy');
INSERT INTO VideoLibrary.Category(categoryName)
Values('Thriller');
INSERT INTO VideoLibrary.Category(categoryName)
Values('Documentary');


INSERT INTO VideoLibrary.Movie(movieName,movieBanner,releaseDate,availableCopies,categoryId)
VALUES ('Ganesha','Ashtavinayak',NOW(),5,1);

INSERT INTO VideoLibrary.Movie(movieName,movieBanner,releaseDate,availableCopies,categoryId)
VALUES ('Jaws','Spielberg',NOW(),5,4);

INSERT INTO VideoLibrary.Movie(movieName,movieBanner,releaseDate,availableCopies,categoryId)
VALUES ('Titanic','Paramount',NOW(),5,2);

INSERT INTO VideoLibrary.Movie(movieName,movieBanner,releaseDate,availableCopies,categoryId)
VALUES ('Funny','ABC',NOW(),5,3);

INSERT INTO VideoLibrary.Movie(movieName,movieBanner,releaseDate,availableCopies,categoryId)
VALUES ('Space','NASA',NOW(),5,5);

INSERT INTO VideoLibrary.User (userId,password,membershipType,startDate,firstName,lastName,address,city,state,zip,creditCardNumber)
VALUES ('sr@yahoo.com','sr123','Simple',NOW(),'Shri','Ram','Washington Ave,Apt 1234','San Jose','CA','93457','1234567890123456');

INSERT INTO VideoLibrary.User (userId,password,membershipType,startDate,firstName,lastName,address,city,state,zip,creditCardNumber)
VALUES ('ab@yahoo.com','ab123','Premium',NOW(),'Abey','Brin','Newark Ave,674','Sacramento','CA','93347','1234512345123456');

INSERT INTO VideoLibrary.User (userId,password,membershipType,startDate,firstName,lastName,address,city,state,zip,creditCardNumber)
VALUES ('xy@yahoo.com','xy123','Simple',NOW(),'Xiao','Yang','Rodeo Dr,123','Los Angelos','CA','91937','1234567890567890');

INSERT INTO VideoLibrary.Admin(userId,password,firstName,lastname)
VALUES('admin1','admin1','Dan','Smith');

INSERT INTO VideoLibrary.Admin(userId,password,firstName,lastname)
VALUES('admin2','admin2','Riya','Shah');

INSERT INTO VideoLibrary.PaymentTransaction(rentDate,totalDueAmount,membershipId)
VALUES (NOW(),4.5,1);
INSERT INTO VideoLibrary.RentMovieTransaction(movieId,transactionId)
Values(1,1);
INSERT INTO VideoLibrary.RentMovieTransaction(movieId,transactionId)
Values(2,1);
INSERT INTO VideoLibrary.RentMovieTransaction(movieId,transactionId)
Values(3,1);

INSERT INTO VideoLibrary.PaymentTransaction(rentDate,totalDueAmount,membershipId)
VALUES (NOW(),1.5,2);
INSERT INTO VideoLibrary.RentMovieTransaction(movieId,transactionId)
Values(3,2);

INSERT INTO VideoLibrary.AmountDetails(membershipType,feesUpdateDate,amount)
values('simple',now(),1.5);

INSERT INTO VideoLibrary.AmountDetails(membershipType,feesUpdateDate,amount)
values('premium',now(),25.0);




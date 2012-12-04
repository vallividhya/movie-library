CREATE SCHEMA VideoLibrary;

CREATE TABLE VideoLibrary.Category
(
categoryName VARCHAR(50),
categoryId INTEGER NOT NULL AUTO_INCREMENT,
PRIMARY KEY (categoryId)
);

CREATE TABLE VideoLibrary.Movie
(
movieId INT NOT NULL AUTO_INCREMENT,
UNIQUE(movieId),
movieName VARCHAR(100),
UNIQUE (movieName),
movieBanner VARCHAR(100),
releaseDate DATE,

availableCopies INT NOT NULL,
categoryId INTEGER NOT NULL,
FOREIGN KEY(categoryId) REFERENCES Category(categoryId),

PRIMARY KEY (movieId)
);

CREATE TABLE VideoLibrary.User(
membershipId INT NOT NULL AUTO_INCREMENT,
userId VARCHAR(50),
UNIQUE (userId),
password VARCHAR(32),
membershipType VARCHAR(10),
startDate DATE,
firstName VARCHAR(50),
lastName VARCHAR(50),
address VARCHAR(50),
city VARCHAR(20),
state CHAR(2),
zip CHAR(5),
creditCardNumber CHAR(16),
latestPaymentDate DATE,
PRIMARY KEY (membershipId)
);

CREATE TABLE VideoLibrary.PaymentTransaction
(
transactionId INT NOT NULL AUTO_INCREMENT,
UNIQUE(transactionId),
rentDate DATE,
totalDueAmount DOUBLE NOT NULL,
membershipId INT NOT NULL,
FOREIGN KEY(membershipId) REFERENCES User(membershipId),
PRIMARY KEY (transactionId)
);

CREATE TABLE VideoLibrary.RentMovieTransaction
(
movieId INTEGER NOT NULL,
transactionId INTEGER NOT NULL,
returnDate DATE,
membershipId INT(9) NOT NULL,
FOREIGN KEY(membershipId) REFERENCES User(membershipId),
FOREIGN KEY(movieId) REFERENCES Movie(movieId),
FOREIGN KEY(transactionId) REFERENCES PaymentTransaction(transactionId),
PRIMARY KEY (transactionId,movieId)
);

CREATE TABLE VideoLibrary.MovieCart
(
movieId INTEGER NOT NULL,
membershipId INTEGER NOT NULL,

FOREIGN KEY(movieId) REFERENCES Movie(movieId),
FOREIGN KEY(membershipId) REFERENCES User(membershipId),
PRIMARY KEY (movieId,membershipId)

);

CREATE TABLE VideoLibrary.Admin
(
userId VARCHAR(20),
password VARCHAR(32),
firstName VARCHAR(20),
lastName VARCHAR(20),
PRIMARY KEY (userId)
);

CREATE TABLE VideoLibrary.Statement
(
statementId INT NOT NULL AUTO_INCREMENT,
month INT NOT NULL,
year INT NOT NULL,
membershipId INT NOT NULL,
FOREIGN KEY(membershipId) REFERENCES User(membershipId),
PRIMARY KEY (statementId)
);

CREATE TABLE VideoLibrary.StatementTransactions
(
statementId INT NOT NULL,
transactionId INT NOT NULL,
FOREIGN KEY(statementId) REFERENCES Statement(statementId),
FOREIGN KEY(transactionId) REFERENCES PaymentTransaction(transactionId)
);

CREATE TABLE VideoLibrary.AmountDetails
(
membershipType VARCHAR(8),
feesUpdateDate DATE,
amount DOUBLE NOT NULL
);

ALTER TABLE videolibrary.user AUTO_INCREMENT = 111111111;
ALTER TABLE `videolibrary`.`user` ADD COLUMN `rentedMovies` INT(1) UNSIGNED ZEROFILL AFTER `latestPaymentDate`;







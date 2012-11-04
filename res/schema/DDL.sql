CREATE SCHEMA VideoLibrary;

CREATE TABLE VideoLibrary.Catagory
(
CatagoryName VARCHAR(100),
CatagoryId INTEGER NOT NULL AUTO_INCREMENT,
PRIMARY KEY (CatagoryId)
);

CREATE TABLE VideoLibrary.Movie
(
MovieId INT NOT NULL AUTO_INCREMENT,
UNIQUE(MovieId),
MovieName VARCHAR(100),
MovieBanner VARCHAR(100),
ReleaseDate DATE,
RentAmount DOUBLE NOT NULL,
AvailableCopies INT NOT NULL,
C_Id INTEGER NOT NULL,
FOREIGN KEY(C_Id) REFERENCES Catagory(CatagoryId),

PRIMARY KEY (MovieId)
);

CREATE TABLE VideoLibrary.Person(
MembershipId INT NOT NULL AUTO_INCREMENT,
UserId VARCHAR(50),
Password VARCHAR(50),
MembershipType VARCHAR(10),
StartDate DATE,
FirstName VARCHAR(50),
LastName VARCHAR(50),
Address VARCHAR(50),
City VARCHAR(20),
State VARCHAR(10),
Zip VARCHAR(20),
CreditcardNumber VARCHAR(40),
PRIMARY KEY (MembershipId)
);

CREATE TABLE VideoLibrary.PaymentTransaction
(
TransactionId INT NOT NULL AUTO_INCREMENT,
UNIQUE(TransactionId),
PurchaseDate DATE,
TotalDueAmount DOUBLE NOT NULL,
Mem_Id INT NOT NULL,
FOREIGN KEY(Mem_Id) REFERENCES Person(MembershipId),
PRIMARY KEY (TransactionId)
);

CREATE TABLE VideoLibrary.RentMovieTransaction
(
M_Id INTEGER NOT NULL,
T_Id INTEGER NOT NULL,
ReturnDate DATE,
FOREIGN KEY(M_Id) REFERENCES Movie(MovieID),
FOREIGN KEY(T_Id) REFERENCES PaymentTransaction(TransactionId),
PRIMARY KEY (T_Id,M_Id)
);

CREATE TABLE VideoLibrary.MovieCart
(
M_Id INTEGER NOT NULL,
U_Id INTEGER NOT NULL,

FOREIGN KEY(M_Id) REFERENCES Movie(MovieId),
FOREIGN KEY(U_Id) REFERENCES Person(MembershipId)
);

CREATE TABLE VideoLibrary.Admin
(
UserId VARCHAR(20),
Password VARCHAR(20),
FirstName VARCHAR(20),
LastName VARCHAR(20),
PRIMARY KEY (UserId)
);

CREATE TABLE VideoLibrary.Statement
(
StatementId INT NOT NULL AUTO_INCREMENT,
StartDate TIMESTAMP ,
EndDate TIMESTAMP ,
U_Id INT NOT NULL,
FOREIGN KEY(U_Id) REFERENCES Person(MembershipId),
PRIMARY KEY (StatementId)
);



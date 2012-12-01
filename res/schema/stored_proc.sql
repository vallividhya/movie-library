DROP PROCEDURE IF EXISTS `videolibrary`.`get_categories`;
DROP PROCEDURE IF EXISTS `videolibrary`.`user_info`;
DROP PROCEDURE IF EXISTS `videolibrary`.`listMoviesByCategory`;
DROP PROCEDURE IF EXISTS `videolibrary`.`searchMovie`;
DROP PROCEDURE IF EXISTS `videolibrary`.`test`;
DROP PROCEDURE IF EXISTS `videolibrary`.`searchUser`;
DROP PROCEDURE IF EXISTS `videolibrary`.`listCartItems`;
DROP PROCEDURE IF EXISTS `videolibrary`.`signInUser`;

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`get_categories` ()
BEGIN
  select categoryname from category;
END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`user_info` (membershipId INT)
BEGIN
  select user.FirstName,user.LastName,user.StartDate,
  user.MembershipType,user.MembershipId,user.userId,user.Password,user.Address,user.City,
user.State,user.Zip,user.CreditCardNumber,user.latestPaymentDate from VideoLibrary.user
where user.MembershipId = membershipId;
END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`listMoviesByCategory` (categoryName VARCHAR(20),listStart int,listStop int)
BEGIN
  Select movieId,movieName,movieBanner,releaseDate,availableCopies from Movie where
 categoryId=(select categoryId from Category where Category.categoryName= categoryName) limit listStart,listStop;
END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`searchMovie` (movieName varchar(50),movieBanner varchar(20),releaseDate varchar(20), start int, stop int)
BEGIN
  DECLARE queryStr varchar(2000);
  set @queryStr=' Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Videolibrary.Movie m, Videolibrary.Category c where m.categoryId=c.categoryId ';
		if movieName is not null then
			set @queryStr = concat(@queryStr, ' and m.movieName like ''%', movieName, '%''');
		end if;
		if movieBanner is not null then
			set @queryStr = concat(@queryStr, ' and m.movieBanner like ''%', movieBanner, '%''');
		end if;
		if releaseDate is not null then
			set @queryStr = concat(@queryStr, ' and m.releaseDate like ''%', releaseDate, '%''');
		end if;

		PREPARE stmt1 FROM @queryStr; 
		EXECUTE stmt1; 
		DEALLOCATE PREPARE stmt1; 
END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`test` ()
BEGIN
	DECLARE queryStr varchar(2000);
	set @queryStr = 'select * from category where categoryName = ''Drama''';
	PREPARE stmt1 FROM @queryStr; 
	EXECUTE stmt1; 
	DEALLOCATE PREPARE stmt1;
END;
$$


DELIMITER $$
CREATE PROCEDURE `videolibrary`.`searchUser` ( membershipId varchar(9), userId varchar(20),
			membershipType varchar(20),startDate varchar(20),firstName varchar(20),
			lastName varchar(20),address varchar(20),city varchar(20), state varchar(2),
			zipCode varchar(10), start int, stop int)
BEGIN
DECLARE queryStr varchar(2500);

	set @queryStr = 'SELECT membershipId, userId, password, membershipType, startDate, firstName, lastName, address, city, state, zip, creditCardNumber, latestPaymentDate FROM videolibrary.user WHERE 1=1 ';
	if membershipId is not null then
		set @queryStr = concat(@queryStr, ' and membershipId like ''%', membershipId, '%''');
	end if;

if userId is not null then
			set @queryStr = concat(@queryStr, ' and userId like ''%', userId, '%''');
		end if;

if membershipType is not null then
			set @queryStr = concat(@queryStr, ' and membershipType like ''%', membershipType, '%''');
		end if;

if startDate is not null then
			set @queryStr = concat(@queryStr, ' and startDate like ''%', startDate, '%''');
		end if;

if firstName is not null then
			set @queryStr = concat(@queryStr, ' and firstName like ''%', firstName, '%''');
		end if;

if lastName is not null then
			set @queryStr = concat(@queryStr, ' and lastName like ''%', lastName, '%''');
		end if;

if address is not null then
			set @queryStr = concat(@queryStr, ' and address like ''%', address, '%''');
		end if;

if city is not null then
			set @queryStr = concat(@queryStr, ' and city like ''%', city, '%''');
		end if;

if state is not null then
			set @queryStr = concat(@queryStr, ' and state like ''%', state, '%''');
		end if;

if zipCode is not null then
			set @queryStr = concat(@queryStr, ' and zipCode like ''%', zipCode, '%''');
		end if;

	set @queryStr = concat( @queryStr, ' LIMIT ', start, ',' , stop );

	PREPARE stmt1 FROM @queryStr; 
	EXECUTE stmt1; 
	DEALLOCATE PREPARE stmt1; 

END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`listCartItems` (pMembershipId int)
BEGIN
	SELECT m.movieId, m.movieName, m.movieBanner, u.membershipType, a.amount 
	FROM videolibrary.moviecart mc, videolibrary.movie m, videolibrary.user u, videolibrary.amountdetails a 
	WHERE mc.membershipId = pMembershipId
	AND mc.movieId = m.movieId AND mc.membershipId = u.membershipId AND u.membershipType = a.membershipType;
END;
$$

DELIMITER $$
CREATE PROCEDURE `videolibrary`.`signInUser` (puserId varchar(20),p_password varchar(32))
BEGIN
	SELECT * FROM user WHERE userId = puserId  AND password = p_password;
END;
$$

-- call videolibrary.signInUser("sr@yahoo.com","sr123");
-- call videolibrary.listCartItems(1);
-- call videolibrary.searchUser('1',null,null,null,null,null,null,null,'CA',null);
-- call videolibrary.test();
-- call videolibrary.searchMovie('Gan',null,null, 0, 100);

-- call videolibrary.listMoviesByCategory('Drama',0,10);

-- call videolibrary.get_categories();
-- call videolibrary.user_info(1);

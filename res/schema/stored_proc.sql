DROP PROCEDURE IF EXISTS `videolibrary`.`get_categories`;
DROP PROCEDURE IF EXISTS `videolibrary`.`user_info`;
DROP PROCEDURE IF EXISTS `videolibrary`.`listMoviesByCategory`;
DROP PROCEDURE IF EXISTS `videolibrary`.`searchMovie`;
DROP PROCEDURE IF EXISTS `videolibrary`.`test`;

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
CREATE PROCEDURE `videolibrary`.`searchMovie` (movieName varchar(50),movieBanner varchar(20),releaseDate varchar(20))
BEGIN
  DECLARE queryStr varchar(2000);
  set queryStr=' Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Videolibrary.Movie m, Videolibrary.Category c where m.categoryId=c.categoryId ';
		if movieName is not null then
			set @queryStr = concat(queryStr, ' and m.movieName like ''%', movieName, '%''');
		end if;
		if movieBanner is not null then
			set @queryStr = concat(queryStr, ' and m.movieBanner like ''%', movieBanner, '%''');
		end if;
		if releaseDate is not null then
			set @queryStr = concat(queryStr, ' and m.releaseDate like ''%', releaseDate, '%''');
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


-- call videolibrary.test();
call videolibrary.searchMovie('Gan',null,null);

-- call videolibrary.listMoviesByCategory('Drama',0,10);

-- call videolibrary.get_categories();
-- call videolibrary.user_info(1);

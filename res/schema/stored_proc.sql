DROP PROCEDURE IF EXISTS `videolibrary`.`get_categories`;
DROP PROCEDURE IF EXISTS `videolibrary`.`user_info`;


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


-- call videolibrary.get_categories();
call videolibrary.user_info(1);

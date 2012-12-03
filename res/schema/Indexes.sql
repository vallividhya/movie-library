ALTER TABLE `videolibrary`.`movie` MODIFY COLUMN `movieBanner` VARCHAR(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 ADD INDEX `movieBanner`(`movieBanner`);
 
ALTER TABLE `videolibrary`.`user` ADD INDEX `lastName`(`lastName`),
 ADD INDEX `state`(`state`);

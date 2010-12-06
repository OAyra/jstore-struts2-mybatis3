ALTER TABLE `jpetstore`.`orders` ADD COLUMN `created_at` TIMESTAMP NULL  AFTER `locale` , 
ADD COLUMN `updated_at` TIMESTAMP NULL  AFTER `created_at` ;


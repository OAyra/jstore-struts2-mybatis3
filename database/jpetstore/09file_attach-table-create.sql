CREATE  TABLE `jpetstore`.`file_attach` (

  `attach_id` INT NOT NULL AUTO_INCREMENT ,

  `item_id` VARCHAR(10) NULL ,

  `filename` VARCHAR(255) NULL ,

  `filepath` VARCHAR(255) NULL ,

  `minetype` VARCHAR(80) NULL ,

  `description` VARCHAR(255) NULL ,

  PRIMARY KEY (`attach_id`) ,

  INDEX `idx_item_id` (`item_id` ASC) ,

  INDEX `fk_itemid` (`item_id` ASC) ,

  CONSTRAINT `fk_itemid`

    FOREIGN KEY (`item_id` )

    REFERENCES `jpetstore`.`item` (`itemid` )

    ON DELETE SET NULL

    ON UPDATE NO ACTION);
    
    
ALTER TABLE `jpetstore`.`file_attach` 
ADD COLUMN `filesize` FLOAT NULL  AFTER `content_type` , 
CHANGE COLUMN `minetype` `content_type` VARCHAR(80) NULL DEFAULT NULL  , 
CHANGE COLUMN `description` `caption` VARCHAR(255) NULL DEFAULT NULL  ;  

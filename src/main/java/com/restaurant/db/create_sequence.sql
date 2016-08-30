CREATE TABLE IF NOT EXISTS `sequence` (  

  
  `name` varchar(50) primary key,
  
  `current_value` int(11) NOT NULL,  
  
  `increment` int(11) NOT NULL DEFAULT '1'  
  
) ENGINE=MyISAM DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='[table_name]'; 

------------------------------------------------------------------
--创建函数用Navicat
DROP FUNCTION IF EXISTS `currval`;
  
CREATE  FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)  
  
    READS SQL DATA  
  
    DETERMINISTIC  
  
BEGIN  
  
DECLARE VALUE INTEGER;  
  
SET VALUE = 0;  
  
SELECT current_value INTO VALUE FROM sequence WHERE NAME = seq_name;  
  
RETURN VALUE;  
  
END

-------------------------------------------------------------------
DROP FUNCTION IF EXISTS `nextval`;  
  
CREATE  FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)  
  
    DETERMINISTIC  
  
BEGIN  
  
UPDATE sequence SET current_value = current_value + increment WHERE NAME = seq_name;  
  
RETURN currval(seq_name);  
  
END

-------------------------------------------------------------------
DROP FUNCTION IF EXISTS 'nextOrderCode';

DELIMITER $$

CREATE FUNCTION nextOrderCode() RETURNS VARCHAR(16)

DETERMINISTIC

  BEGIN

    DECLARE CURRENT_DATE_STR VARCHAR(16);
    DECLARE CURRENT_DATE_COUNT integer;
    DECLARE NEXT_CODE_NUM int(11);

    SELECT CURDATE()+0 INTO CURRENT_DATE_STR;

    SELECT COUNT(1) INTO CURRENT_DATE_COUNT FROM ORDER_ITEM WHERE INSTR(ITEM_CODE, CURRENT_DATE_STR) = 1;
    IF CURRENT_DATE_COUNT = 0 THEN
      UPDATE sequence SET current_value = 1 WHERE NAME = 'ORDER_ITEM';
      RETURN concat(CURRENT_DATE_STR, '0001');
    ELSE
      SET NEXT_CODE_NUM = nextval('ORDER_ITEM');
    END IF;

    IF NEXT_CODE_NUM < 10 THEN
      RETURN concat(CURRENT_DATE_STR, '000', NEXT_CODE_NUM);
    ELSE IF NEXT_CODE_NUM < 100 THEN
      RETURN concat(CURRENT_DATE_STR, '00', NEXT_CODE_NUM);
    ELSE IF NEXT_CODE_NUM < 1000 THEN
      RETURN concat(CURRENT_DATE_STR, '0', NEXT_CODE_NUM);
    ELSE
      RETURN concat(CURRENT_DATE_STR, NEXT_CODE_NUM);
    END IF;

  END $$

DELIMITER;


insert into sequence(name, current_value, increment) values('ORDER_ITEM', 100000000, 1);
insert into sequence(name, current_value, increment) values('FOOD_INFO', 100000000, 1);
insert into sequence(name, current_value, increment) values('USER_INFO', 100000000, 1);
insert into sequence(name, current_value, increment) values('ORDER_CODE', 0, 1);

insert into order_item(item_id, item_name, item_code) values(nextval("ORDER_ITEM"), 'TEST', '201608270001')

select * from sequence;
select nextval('ORDER_ITEM');

insert into food_info(food_id, food_name, food_code, food_type, food_price, create_date, status)
    values(nextval("FOOD_INFO"), '米欧泡菜拌饭', 'MIOU_PAOCAI', 1, 12, current_timestamp(), 1);

insert into food_info(food_id, food_name, food_code, food_type, food_price, create_date, status)
values(nextval("FOOD_INFO"), '米欧泡菜拌饭', 'MIOU_PAOCAI', 1, 12, current_timestamp(), 1);

insert into food_info(food_id, food_name, food_code, food_type, food_price, create_date, status)
values(nextval("FOOD_INFO"), '米欧泡菜拌饭', 'MIOU_PAOCAI', 1, 12, current_timestamp(), 1);

insert into food_info(food_id, food_name, food_code, food_type, food_price, create_date, status)
values(nextval("FOOD_INFO"), '米欧泡菜拌饭', 'MIOU_PAOCAI', 1, 12, current_timestamp(), 1);
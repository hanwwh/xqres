drop table if exists order_item;
--订单表
--status_状态：0-未支付，1-已支付，2-已打印，3-已完成，-1：无效
CREATE TABLE order_item (
	item_id  int(11) PRIMARY KEY ,
	group_id int(11),
	status integer NOT NULL,
	item_code  varchar(32),
	item_name  varchar(64) NOT NULL,
	item_price  float(8,2) NOT NULL,
	create_date timestamp,
	item_picurl  varchar(1024),
	item_details  varchar(1024),
	remark varchar(512)
);


drop table if exists order_item_food_relat;
--订单和菜品的关联表
--discount:折扣(1.0表示没打折)
--order_type:类型：1-菜，2-套餐
--food_count:该订单中对应该菜品的数量，比如一个订单中点了两份一样的米欧拌饭泡菜
create table 'order_item_food_relat'(
	item_id int(11),
	food_id int(11),
	food_count integer,
	order_type integer,
	discount float(4,2) default 1.0,
	create_date timestamp,
	remark varchar(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--alter table order_item_food_relat drop foreign key fk_item_id;
--alter table order_item_food_relat drop foreign key fk_food_id;
--alter table order_item_food_relat add constraint fk_item_id foreign key (item_id) references order_item (item_id);
--alter table order_item_food_relat add constraint fk_food_id foreign key (food_id) references food_info (food_id);

drop table if exists food_info;
--菜品表
create table food_info(
	food_id int(11) primary key,
	food_name varchar(32),
	food_code varchar(16),
	food_type integer,
	food_price float(8,2),
	food_img_url varchar(256),
	food_desc varchar(512),
	create_date timestamp,
	status integer
);


drop table if exists food_package;
--套餐表
--package_price:套餐价格
create table food_package(
	food_package_id int(11) primary key,
	food_id int(11),
	package_price float(8,2),
	package_name varchar(64),
	create_date timestamp,
	remark varchar(1024)
);
--alter table food_package drop foreign key fk_food_package_id;
--alter table food_package add constraint fk_food_package_id foreign key (food_id) references food_info (food_id);


drop table if exists user_info;
--用户信息表
--status_状态：1-注册，0-未注册
create table user_info(
	user_id int(11) primary key,
	user_name varchar(32),
	gender integer,
	age integer,
	product_no varchar(16),
	wechat_id varchar(64),
	status integer,
	create_date timestamp,
	remark varchar(1024)
);


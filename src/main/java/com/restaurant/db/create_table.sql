drop table if exists order_item;
--������
--status_״̬��0-δ֧����1-��֧����2-�Ѵ�ӡ��3-����ɣ�-1����Ч
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
--�����Ͳ�Ʒ�Ĺ�����
--discount:�ۿ�(1.0��ʾû����)
--order_type:���ͣ�1-�ˣ�2-�ײ�
--food_count:�ö����ж�Ӧ�ò�Ʒ������������һ�������е�������һ������ŷ�跹�ݲ�
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
--��Ʒ��
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
--�ײͱ�
--package_price:�ײͼ۸�
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
--�û���Ϣ��
--status_״̬��1-ע�ᣬ0-δע��
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


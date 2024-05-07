CREATE TABLE devices (
    id int not null auto_increment primary key,
    name VARCHAR(255),
    brand VARCHAR(255),
		
	created_at     timestamp default current_timestamp,
    updated_at     timestamp null default null,
    deleted_at     timestamp null default null
);
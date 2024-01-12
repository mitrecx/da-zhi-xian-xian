use dazhi;

drop table if exists dz_user;
create table dz_user
(
    id              bigint auto_increment primary key,
    login_name      varchar(32) comment '登录名',
    username        varchar(32) comment '用户名/昵称',
    password        varchar(64) comment '密码',
    email           varchar(64) comment '邮箱',
    phone           varchar(16) comment '电话号码',
    status          varchar(1) comment '0 正常; 1 锁定; 2 过期',
    delete_flag     varchar(1) comment '0 正常; 1 已删除',
    created_at      bigint,
    last_updated_at bigint,
    last_login_at   bigint
);

insert into dz_user(login_name, username, password, email, phone, status, delete_flag, created_at, last_updated_at,
                    last_login_at)
values ('admin', '大智闲闲', 'xxx', 'mitrecx@163.com', '15556928810', 0, 0, UNIX_TIMESTAMP(NOW()) * 1000,
        UNIX_TIMESTAMP(NOW()) * 1000, 0);
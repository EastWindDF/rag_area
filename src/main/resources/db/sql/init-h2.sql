create table tb_task_info
(
    id           varchar(64) primary key,
    title        varchar(64),
    description  text,
    support_unit varchar(1000),
    bus_dir      varchar(1000),
    work_goal    varchar(1000),
    creator_id   varchar(64),
    creator_name varchar(256),
    defaulted    bool      default false,
    gmt_create   timestamp default current_timestamp
);

create table tb_conv_session_info
(
    id           varchar(64) primary key,
    title        varchar(1024),
    task_id      varchar(64),
    answered     bool      default false,
    question     text,
    gmt_create   timestamp default current_timestamp,
    gmt_modified timestamp default current_timestamp
);

create table tb_sess_pri_mapping
(
    id           varchar(64) primary key,
    sess_id      varchar(64),
    pri_id       varchar(64),
    sys_code     varchar(64),
    mask_name    varchar(128),
    mask_code    varchar(128),
    liked        int       default 0,
    grade        float     default 0,
    gmt_create   timestamp default current_timestamp,
    gmt_modified timestamp default current_timestamp
);

create table tb_conv_record_info
(
    id        varchar(64) primary key,
    pri_id    varchar(64),
    question  text,
    answer    text,
    common    bool      default true,
    gmt_begin timestamp default current_timestamp,
    gmt_end   timestamp default current_timestamp
);

create table tb_chat_citation_info
(
    id         varchar(64) primary key,
    conv_id    varchar(64),
    ref_id     varchar(128),
    summary    text,
    gmt_begin  timestamp,
    duration   bigint,
    order_num  int default 0,
    call_num   varchar(128),
    called_num varchar(128),
    labels     varchar(4000)
);

create table tb_chat_star_rating
(
    id           varchar(64) primary key,
    pri_id       varchar(64),
    time_spent   int       default 0,
    content      int       default 0,
    accuracy     int       default 0,
    sensitivity  int       default 0,
    summary      int       default 0,
    tag_accuracy int       default 0,
    processing   int       default 0,
    remark       text,
    user_id      varchar(64),
    gmt_create   timestamp default current_timestamp
);
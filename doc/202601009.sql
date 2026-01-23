-- 创建任务信息表;
create table tb_task_info
(
    id           varchar(64) primary key,
    title        varchar(64),
    description  text,
    support_unit varchar(1000),
    bus_dir      varchar(1000),
    work_goal      varchar(1000),
    creator_id   varchar(64),
    creator_name varchar(256),
    defaulted    bool default false,
    gmt_create   timestamp default current_timestamp
);

comment on table tb_task_info is '任务信息表';
comment on column tb_task_info.title is '任务名称';
comment on column tb_task_info.creator_id is '创建人ID';
comment on column tb_task_info.creator_name is '创建人姓名';
comment on column tb_task_info.defaulted is '是否为默认';
comment on column tb_task_info.description is '描述';
comment on column tb_task_info.support_unit is '支撑单位';
comment on column tb_task_info.bus_dir is '业务方向';
comment on column tb_task_info.work_goal is '工作方向';

-- 创建会话信息表;
create table tb_conv_session_info
(
    id           varchar(64) primary key,
    title        varchar(1024),
    task_id      varchar(64),
    answered     bool default false,
    question     text,
    gmt_create   timestamp default current_timestamp,
    gmt_modified timestamp default current_timestamp
);

comment on table tb_conv_session_info is '会话信息表';
comment on column tb_conv_session_info.title is '会话名称';
comment on column tb_conv_session_info.task_id is '任务id';
comment on column tb_conv_session_info.question is '问题描述';
comment on column tb_conv_session_info.answered is '是否已答复';

-- 创建会话与对应模型ID映射表;
create table tb_sess_pri_mapping
(
    id          varchar(64) primary key,
    sess_id     varchar(64),
    pri_id      varchar(64),
    sys_code    varchar(64),
    mask_name   varchar(128),
    liked       int       default 0,
    grade       float default 0,
    gmt_create   timestamp default current_timestamp,
    gmt_modified timestamp default current_timestamp
);
comment on table tb_sess_pri_mapping is '会话与对应模型ID映射表';
comment on column tb_sess_pri_mapping.sess_id is '会话ID';
comment on column tb_sess_pri_mapping.pri_id is '私有会话ID';
comment on column tb_sess_pri_mapping.sys_code is '任务编码';
comment on column tb_sess_pri_mapping.mask_name is '模型名称';
comment on column tb_sess_pri_mapping.liked is '点赞数';
comment on column tb_sess_pri_mapping.grade is '评判星级';

-- 创建对话详情表;
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
comment on table tb_conv_record_info is '对话详情表';
comment on column tb_conv_record_info.pri_id is '私有ID';
comment on column tb_conv_record_info.question is '问题';
comment on column tb_conv_record_info.answer is '回答';
comment on column tb_conv_record_info.common is '是否为公共问题';
comment on column tb_conv_record_info.gmt_begin is '问答开始时间';
comment on column tb_conv_record_info.gmt_begin is '问答结束时间';


-- 创建对话附件信息表;
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
comment on table tb_chat_citation_info is '对话附件信息表';
comment on column tb_chat_citation_info.conv_id is '对话ID';
comment on column tb_chat_citation_info.ref_id is '引用ID';
comment on column tb_chat_citation_info.summary is '总结';
comment on column tb_chat_citation_info.gmt_begin is '通话开始时间';
comment on column tb_chat_citation_info.duration is '通话时长';
comment on column tb_chat_citation_info.call_num is '主叫';
comment on column tb_chat_citation_info.called_num is '被叫';
comment on column tb_chat_citation_info.labels is '标签';
comment on column tb_chat_citation_info.order_num is '排序字段';


-- 创建星评信息表;
create table tb_chat_star_rating(
    id varchar(64) primary key,
    pri_id varchar(64),
    time_spent int default 0,
    content int default 0,
    accuracy int default 0,
    sensitivity int default 0,
    summary int default 0,
    tag_accuracy int default 0,
    processing int default 0,
    remark text ,
    user_id varchar(64),
    gmt_create timestamp default current_timestamp
);

comment on table tb_chat_star_rating is '创建星评信息表';
comment on column tb_chat_star_rating.pri_id is '私有ID';
comment on column tb_chat_star_rating.time_spent is '耗时';
comment on column tb_chat_star_rating.content is '思考内容';
comment on column tb_chat_star_rating.accuracy is '回答准确度';
comment on column tb_chat_star_rating.sensitivity is '思考敏感度';
comment on column tb_chat_star_rating.summary is '引用内容摘要';
comment on column tb_chat_star_rating.tag_accuracy is '标签准确度';
comment on column tb_chat_star_rating.processing is '智能化处理';
comment on column tb_chat_star_rating.remark is '备注（文字）';
comment on column tb_chat_star_rating.user_id is '用户ID';
comment on column tb_chat_star_rating.gmt_create is '插入时间';
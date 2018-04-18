-----------------------------------------------
-- Export file for user WEIXIN               --
-- Created by yexin on 2015/3/12, 10:12:58 --
-----------------------------------------------

spool weixin.log

prompt
prompt Creating table SYS_MENU
prompt =======================
prompt
create table SYS_MENU
(
  ID         NUMBER(11) not null,
  NAME       VARCHAR2(50),
  URL        VARCHAR2(100),
  PARENTID   NUMBER(10),
  DELETED    NUMBER(1) default 0 not null,
  CREATETIME DATE,
  UPDATETIME DATE,
  RANK       NUMBER(11) default 0 not null,
  ACTIONS    VARCHAR2(500) default 0
)
;
comment on column SYS_MENU.ID
  is '主键';
comment on column SYS_MENU.NAME
  is '菜单名称';
comment on column SYS_MENU.URL
  is '系统url';
comment on column SYS_MENU.PARENTID
  is '父id 关联sys_menu.id';
comment on column SYS_MENU.DELETED
  is '是否删除,0=未删除，1=已删除';
comment on column SYS_MENU.CREATETIME
  is '创建时间';
comment on column SYS_MENU.UPDATETIME
  is '修改时间';
comment on column SYS_MENU.RANK
  is '排序';
comment on column SYS_MENU.ACTIONS
  is '注册Action 按钮|分隔';
alter table SYS_MENU
  add constraint PK_SYS_MENU_ID primary key (ID);

prompt
prompt Creating table SYS_MENU_BTN
prompt ===========================
prompt
create table SYS_MENU_BTN
(
  ID         NUMBER(11) not null,
  MENUID     NUMBER(11) not null,
  BTNNAME    VARCHAR2(30),
  BTNTYPE    VARCHAR2(30),
  ACTIONURLS VARCHAR2(250)
)
;
comment on column SYS_MENU_BTN.ID
  is '主键';
comment on column SYS_MENU_BTN.MENUID
  is '菜单id关联 sys_menu.id';
comment on column SYS_MENU_BTN.BTNNAME
  is '按钮名称';
comment on column SYS_MENU_BTN.BTNTYPE
  is '按钮类型，用于列表页显示的按钮';
comment on column SYS_MENU_BTN.ACTIONURLS
  is 'url注册，用"," 分隔 。用于权限控制UR';
alter table SYS_MENU_BTN
  add constraint PK_SYS_MENU_BTN_ID primary key (ID);

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  ID         NUMBER(12) not null,
  ROLENAME   VARCHAR2(30),
  CREATETIME DATE,
  CREATEBY   NUMBER(11),
  UPDATETIME DATE,
  UPDATEBY   NUMBER(11),
  STATE      NUMBER(1),
  DESCR      VARCHAR2(200)
)
;
comment on column SYS_ROLE.ID
  is 'id主键';
comment on column SYS_ROLE.ROLENAME
  is '角色名称';
comment on column SYS_ROLE.CREATETIME
  is '创建时间';
comment on column SYS_ROLE.CREATEBY
  is '创建人';
comment on column SYS_ROLE.UPDATETIME
  is '修改时间';
comment on column SYS_ROLE.UPDATEBY
  is '修改人';
comment on column SYS_ROLE.STATE
  is '状态0=可用 1=禁用';
comment on column SYS_ROLE.DESCR
  is '角色描述';
alter table SYS_ROLE
  add constraint PK_SYS_ROLE_ID primary key (ID);

prompt
prompt Creating table SYS_ROLE_REL
prompt ===========================
prompt
create table SYS_ROLE_REL
(
  ROLEID  NUMBER(11) not null,
  OBJID   NUMBER(11) not null,
  RELTYPE NUMBER(1)
)
;
comment on column SYS_ROLE_REL.ROLEID
  is '角色主键 sys_role.id';
comment on column SYS_ROLE_REL.OBJID
  is '关联主键 type=0管理sys_menu.id, type=1关联sys_user.id';
comment on column SYS_ROLE_REL.RELTYPE
  is '关联类型 0=菜单,1=用户';

prompt
prompt Creating table SYS_USER
prompt =======================
prompt
create table SYS_USER
(
  ID         NUMBER(11) not null,
  USERNAME   VARCHAR2(50) not null,
  PWD        VARCHAR2(50),
  NICKNAME   VARCHAR2(50),
  STATE      VARCHAR2(1) default 0 not null,
  LOGINCOUNT NUMBER(11),
  LOGINTIME  DATE,
  DELETED    NUMBER(1) default 0 not null,
  CREATETIME DATE,
  UPDATETIME DATE,
  CREATEBY   NUMBER(11),
  UPDATEBY   NUMBER(11),
  SUPERADMIN NUMBER(1) default 0 not null
)
;
comment on column SYS_USER.ID
  is 'id主键';
comment on column SYS_USER.PWD
  is '登录密码';
comment on column SYS_USER.NICKNAME
  is '昵称';
comment on column SYS_USER.STATE
  is '状态 0=可用,1=禁用';
comment on column SYS_USER.LOGINCOUNT
  is '登录总次数';
comment on column SYS_USER.LOGINTIME
  is '最后登录时间';
comment on column SYS_USER.DELETED
  is '删除状态 0=未删除,1=已删除';
comment on column SYS_USER.CREATETIME
  is '创建时间';
comment on column SYS_USER.UPDATETIME
  is '修改时间';
comment on column SYS_USER.CREATEBY
  is '创建人';
comment on column SYS_USER.UPDATEBY
  is '修改人';
comment on column SYS_USER.SUPERADMIN
  is '是否超级管理员 0= 不是，1=是';
alter table SYS_USER
  add constraint PK_SYS_USER_ID primary key (ID);

prompt
prompt Creating table WEIXIN_USER
prompt ==========================
prompt
create table WEIXIN_USER
(
  XH    NUMBER(10) not null,
  WXID  VARCHAR2(64) not null,
  ZT    VARCHAR2(2) not null,
  YLZD1 VARCHAR2(512),
  YLZD2 VARCHAR2(512),
  YLZD3 VARCHAR2(512),
  YLZD4 VARCHAR2(512),
  YLZD5 VARCHAR2(512)
)
;
comment on column WEIXIN_USER.XH
  is '序号';
comment on column WEIXIN_USER.WXID
  is '微信openid';
comment on column WEIXIN_USER.ZT
  is '状态';
comment on column WEIXIN_USER.YLZD1
  is '预留字段1';
comment on column WEIXIN_USER.YLZD2
  is '预留字段2';
comment on column WEIXIN_USER.YLZD3
  is '预留字段3';
comment on column WEIXIN_USER.YLZD4
  is '预留字段4';
comment on column WEIXIN_USER.YLZD5
  is '预留字段5';
alter table WEIXIN_USER
  add constraint PK_WEIXIN_USER_XH primary key (XH);

prompt
prompt Creating table WX_ACCOUNT
prompt =========================
prompt
create table WX_ACCOUNT
(
  ID        NUMBER(11) not null,
  NAME      VARCHAR2(200),
  TOKEN     VARCHAR2(200),
  TYPE      VARCHAR2(50),
  APPID     VARCHAR2(200),
  APPSECRET VARCHAR2(500)
)
;
comment on column WX_ACCOUNT.ID
  is '主键';
comment on column WX_ACCOUNT.NAME
  is '公众帐号名称';
comment on column WX_ACCOUNT.TOKEN
  is '公众帐号TOKEN';
comment on column WX_ACCOUNT.TYPE
  is '公众号类型';
comment on column WX_ACCOUNT.APPID
  is '公众帐号APPID';
comment on column WX_ACCOUNT.APPSECRET
  is '公众帐号APPSECRET';
alter table WX_ACCOUNT
  add constraint PK_WX_ACCOUNT_ID primary key (ID);

prompt
prompt Creating table WX_CODE
prompt ======================
prompt
create table WX_CODE
(
  ID         NUMBER(11) not null,
  NAME       VARCHAR2(50),
  CODE       VARCHAR2(100),
  VALUE      VARCHAR2(100),
  PARENTID   VARCHAR2(16),
  DELETED    NUMBER(1) default 0 not null,
  CREATETIME DATE,
  UPDATETIME DATE,
  REMARKS    VARCHAR2(100)
)
;
comment on column WX_CODE.ID
  is '主键';
comment on column WX_CODE.NAME
  is '代码名称';
comment on column WX_CODE.CODE
  is '代码';
comment on column WX_CODE.VALUE
  is '代码值';
comment on column WX_CODE.PARENTID
  is '父id 关联wx_code.id';
comment on column WX_CODE.DELETED
  is '是否删除,0=未删除，1=已删除';
comment on column WX_CODE.CREATETIME
  is '创建时间';
comment on column WX_CODE.UPDATETIME
  is '修改时间';
comment on column WX_CODE.REMARKS
  is '备注';
alter table WX_CODE
  add constraint PK_WX_CODE_ID primary key (ID);

prompt
prompt Creating sequence SEQ_SYS_MENU_BTN_ID
prompt =====================================
prompt
create sequence SEQ_SYS_MENU_BTN_ID
minvalue 1
maxvalue 99999999999
start with 33
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_SYS_MENU_ID
prompt =================================
prompt
create sequence SEQ_SYS_MENU_ID
minvalue 1
maxvalue 99999999999
start with 21
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_SYS_ROLE_ID
prompt =================================
prompt
create sequence SEQ_SYS_ROLE_ID
minvalue 1
maxvalue 999999999999
start with 14
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_SYS_ROLE_REF_ID
prompt =====================================
prompt
create sequence SEQ_SYS_ROLE_REF_ID
minvalue 1
maxvalue 99999999999
start with 11
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_SYS_USER_ID
prompt =================================
prompt
create sequence SEQ_SYS_USER_ID
minvalue 1
maxvalue 99999999999
start with 22
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_WEIXIN_USER_XH
prompt ====================================
prompt
create sequence SEQ_WEIXIN_USER_XH
minvalue 1
maxvalue 9999999999
start with 11
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_WX_ACCOUNT_ID
prompt ===================================
prompt
create sequence SEQ_WX_ACCOUNT_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_WX_CODE_ID
prompt ================================
prompt
create sequence SEQ_WX_CODE_ID
minvalue 1
maxvalue 99999999999
start with 21
increment by 1
cache 10;

spool off



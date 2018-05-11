create table if not exists sys_menu(
  id int(11) primary key auto_increment comment '主键',
  name varchar(50) comment '菜单名称',
  url varchar(100) comment '系统url',
  parentid int(11) comment '父id,关联sys_menu.id',
  deleted int(1)  default 0 not null comment '是否删除,0=未删除，1=已删除',
  createtime datetime comment '创建时间',
  updatetime datetime comment '修改时间',.
  rank int(11) default 0 not null comment '排序',
  actions varchar(500) comment '系统url'
) charset=utf8 comment='系统菜单表';

create table if not exists sys_menu_btn(
  id int(11) primary key auto_increment comment '主键',
  menuid int(11) not null comment '菜单id关联 sys_menu.id',
  btnname varchar(30) comment '按钮名称',
  btntype varchar(30) comment '按钮类型，用于列表页显示的按钮',
  actionurls varchar(250) comment 'url注册，用"," 分隔 。用于权限控制url'
) charset=utf8 comment='系统菜单按钮表';

create table if not exists sys_role(
  id int(11) primary key auto_increment comment '主键',
  rolename varchar(30) comment '角色名称',
  createtime datetime comment '创建时间',
  createby int(11) comment '创建人',
  updatetime datetime comment '修改时间',
  updateby int(11) comment '修改人',
  state int(1) comment '状态0=可用 1=禁用',
  descr varchar(200) comment '角色描述'
) charset=utf8 comment='系统角色表';

create table if not exists sys_role_rel(
  roleid int(11) not null comment '角色主键 sys_role.id',
  objid int(11) not null comment '关联主键 type=0管理sys_menu.id, type=1关联sys_user.id',
  reltype int(1) comment '关联类型 0=菜单,1=用户'
) charset=utf8 comment='角色关联表';

create table if not exists sys_user(
  id int(11) primary key auto_increment comment '主键',
  username varchar(50) not null comment '用户名',
  pwd varchar(50) comment '登录密码',
  nickname varchar(50) comment '昵称',
  state varchar(1) default '0' not null comment '状态 0=可用,1=禁用',
  logincount int(11) comment '登录总次数',
  logintime datetime comment '最后登录时间',
  deleted int(1) default 0 not null comment '删除状态 0=未删除,1=已删除',
  createtime datetime comment '创建时间',
  updatetime datetime comment '修改时间',
  createby int(11) comment '创建人',
  updateby int(11) comment '修改人',
  superadmin int(1) default 0 not null comment '是否超级管理员 0= 不是，1=是'
) charset=utf8 comment='系统用户表';

create table if not exists weixin_user(
  id int(11) primary key auto_increment comment '主键',
  wxid varchar(64) not null comment '微信id',
  phonenum varchar(32) not null comment '手机号码',
  balance double(11,3) default 0 not null comment '余额',
  moneyextracting double(11,3) default 0 not null comment '提取中金额',
  extractedmoney double(11,3) default 0 not null comment '已提取金额',
  nickname varchar(256) DEFAULT NULL,
) charset=utf8 comment='微信用户表';

create table if not exists wx_account(
  id int(11) primary key auto_increment comment '主键',
  name varchar(200) comment '公众帐号名称',
  token varchar(200) comment '公众帐号token',
  type varchar(50) comment '公众号类型',
  appid varchar(200) comment '公众帐号appid',
  appsecret varchar(500) comment '公众帐号appsecret'
) charset=utf8 comment='微信公众号账号表';

create table if not exists wx_code(
  id int(11) primary key auto_increment comment '主键',
  name varchar(50) comment '代码名称',
  code varchar(100) comment '代码',
  value varchar(100) comment '代码值',
  parentid int(11) comment '父id 关联wx_code.id',
  deleted int(1) default 0 not null comment '是否删除,0=未删除，1=已删除',
  createtime datetime comment '创建时间',
  updatetime datetime comment '修改时间',
  remarks varchar(100) comment '备注'
) charset=utf8 comment='系统代码表';

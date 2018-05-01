create table if not exists t_con_item_res(
  id int(11) primary key auto_increment comment '主键-id',
  conid int(11) not null comment '关联表id',
  type varchar(1) default '1' not null comment '类型：1-图片，2-视频。目前只考虑图片，为以后做兼容',
  path varchar(128) not null comment '资源路径',
  contype varchar(1) not null comment '关联类型：1-拍卖会表(t_e_auction)，2-拍品表(t_e_auction_item)',
  idx int(2) default 0 not null comment '序号，用于排序'
) charset=utf8 comment='拍品与资源关联表';

create table if not exists t_e_auction(
  id int(11) primary key auto_increment comment '主键-id',
  name varchar(256) not null comment '拍卖会名称',
  starttime datetime not null comment '开始时间',
  endtime datetime not null comment '结束时间',
  createtime datetime not null comment '创建时间',
  creator int(11) not null comment '创建人',
  modifytime datetime comment '修改时间',
  modifier int(11) comment '修改人',
  businessid int(11) not null comment '商家id',
  description varchar(512) comment '描述说明',
  status varchar(1) default '1' not null comment '状态：0-删除，1-正常',
  viewnum int(11) default 0 not null comment '浏览数目',
  type varchar(2) default '0' not null comment '拍卖会类型：待定'
) charset=utf8 comment='拍卖会表';

create table if not exists t_e_auction_item
(
   id                   int(11)           primary key auto_increment comment '主键-id',
   name                 varchar(256)        not null comment '拍品名称',
   type                 varchar(2)          not null comment '拍品类型：待定',
   description          varchar(512) comment '介绍描述',
   starttime            datetime                 not null comment '开始拍卖时间',
   endtime              datetime                 not null comment '结束拍卖时间',
   startprice           double(11,3)         default 0 not null comment '起拍价格',
   curprice             double(11,3)         default 0 not null comment '当前拍卖价格',
   finalprice           double(11,3)         default 0 not null comment '成交价格',
   addprice             double(11,3)         default 0 not null comment '加价最低价格',
   depositprice         double(11,3)         default 0 not null comment '保证金',
   rate                 double(6,3)          default 0 not null comment '手续费比率',
   detail               varchar(1024) comment '拍品详情',
   status               varchar(2)          default '1' not null comment '状态：0-删除，1-未审核，2-审核未通过，3-正常(审核通过)',
   isshow               varchar(1)          default '0' not null comment '是否在首页显示：0-否，1-是',
   auctionstatus        varchar(1)          default '0' not null comment '拍卖状态：0-未开始拍卖，1-正在拍卖，2-拍卖成功，3-流拍',
   auctionid            int(11)           not null comment '拍卖会id'
) charset=utf8 comment='拍卖品表';

create table if not exists t_e_bid
(
   ID                   int(11)           primary key auto_increment comment '主键-id',
   WXUSERID             int(11)           not null comment '出价人ID，关联WEIXIN_USER表',
   AUCTIONITEMID        int(11)           not null comment '拍品ID',
   BIDPRICE             double(11,3)         default 0 not null comment '出价价格',
   STATUS               varchar(1)          default '0' not null comment '状态：0-出局，1-领先',
   BIDTIME              datetime            not null comment '出价时间'
)charset=utf8 comment='拍品出价表';

create table if not exists t_e_business
(
   id                   int(11)           primary key auto_increment comment '主键-id',
   name                 varchar(256)        not null comment '商家名称',
   address              varchar(256) comment '商家地址',
   createtime           datetime                 not null comment '创建时间',
   updatetime           datetime                 not null comment '修改时间',
   telnum               varchar(32) comment '商家电话',
   logopath             varchar(128) comment '图标地址',
   status               varchar(1) not null comment '状态：0-删除,1-正常'
)charset=utf8 comment='拍卖商家表';

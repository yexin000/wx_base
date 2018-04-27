/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* 内容:      拍卖功能表创建                      */
/* Created on:     2018/4/23 21:05:32                           */
/*==============================================================*/


drop table WEIXIN_USER cascade constraints;

/*==============================================================*/
/* Table: WEIXIN_USER                                           */
/*==============================================================*/
create table WEIXIN_USER 
(
   WXID                 VARCHAR2(64)         not null,
   PHONENUM             VARCHAR2(32)         not null,
   BALANCE              NUMBER(11,3)         default 0 not null,
   MONEYEXTRACTING      NUMBER(11,3)         default 0 not null,
   EXTRACTEDMONEY       NUMBER(11,3)         default 0 not null
);

comment on table WEIXIN_USER is
'微信用户表';

comment on column WEIXIN_USER.WXID is
'微信ID';

comment on column WEIXIN_USER.PHONENUM is
'手机号码';

comment on column WEIXIN_USER.BALANCE is
'余额';

comment on column WEIXIN_USER.MONEYEXTRACTING is
'提取中金额';

comment on column WEIXIN_USER.EXTRACTEDMONEY is
'已提取金额';

/*==============================================================*/
/* Table: "t_con_item_res"                                      */
/*==============================================================*/
create table "t_con_item_res" 
(
   ID                   NUMBER(11)           not null,
   CONID                NUMBER(11)           not null,
   TYPE                 varchar2(1)          default '1' not null,
   PATH                 varchar2(128)        not null,
   CONTYPE              varchar2(1)          not null,
   "INDEX"              NUMBER(2)            default 0 not null,
   constraint PK_T_CON_ITEM_RES primary key (ID)
);

comment on table "t_con_item_res" is
'拍品与资源关联表';

comment on column "t_con_item_res".ID is
'主键-ID';

comment on column "t_con_item_res".CONID is
'关联表ID';

comment on column "t_con_item_res".TYPE is
'类型：1-图片，2-视频。目前只考虑图片，为以后做兼容';

comment on column "t_con_item_res".PATH is
'资源路径';

comment on column "t_con_item_res".CONTYPE is
'关联类型：1-拍卖会表(t_e_auction)，2-拍品表(t_e_auction_item)';

comment on column "t_con_item_res"."INDEX" is
'序号，用于排序';

/*==============================================================*/
/* Table: "t_e_auction"                                         */
/*==============================================================*/
create table "t_e_auction" 
(
   ID                   NUMBER(11)           not null,
   NAME                 varchar2(256)        not null,
   STARTTIME            DATE                 not null,
   ENDTIME              DATE                 not null,
   CREATETIME           DATE                 not null,
   CREATOR              NUMBER(11)           not null,
   MODIFYTIME           DATE,
   MODIFIER             NUMBER(11),
   BUSINESSID           NUMBER(11)           not null,
   DESCRIPTION          varchar2(512),
   STATUS               varchar2(1)          not null,
   VIEWNUM              NUMBER(11)           default 0 not null,
   TYPE                 varchar2(2)          not null,
   constraint PK_T_E_AUCTION primary key (ID)
);

comment on table "t_e_auction" is
'拍卖会表';

comment on column "t_e_auction".ID is
'主键-ID';

comment on column "t_e_auction".NAME is
'拍卖会名称';

comment on column "t_e_auction".STARTTIME is
'开始时间';

comment on column "t_e_auction".ENDTIME is
'结束时间';

comment on column "t_e_auction".CREATETIME is
'创建时间';

comment on column "t_e_auction".CREATOR is
'创建人';

comment on column "t_e_auction".MODIFYTIME is
'修改时间';

comment on column "t_e_auction".MODIFIER is
'修改人';

comment on column "t_e_auction".BUSINESSID is
'商家ID';

comment on column "t_e_auction".DESCRIPTION is
'描述说明';

comment on column "t_e_auction".STATUS is
'状态：0-删除，1-正常';

comment on column "t_e_auction".VIEWNUM is
'浏览数目';

comment on column "t_e_auction".TYPE is
'拍卖会类型：';

/*==============================================================*/
/* Table: "t_e_auction_item"                                    */
/*==============================================================*/
create table "t_e_auction_item" 
(
   ID                   NUMBER(11)           not null,
   NAME                 varchar2(256)        not null,
   TYPE                 varchar2(2)          not null,
   DESCRIPTION          varchar2(512),
   STARTTIME            DATE                 not null,
   ENDTIME              DATE                 not null,
   STARTPRICE           NUMBER(11,3)         default 0 not null,
   CURPRICE             NUMBER(11,3)         default 0 not null,
   FINALPRICE           NUMBER(11,3)         default 0 not null,
   ADDPRICE             NUMBER(11,3)         default 0 not null,
   DEPOSITPRICE         NUMBER(11,3)         default 0 not null,
   RATE                 NUMBER(3,3)          default 0 not null,
   DETAIL               varchar2(1024),
   STATUS               varchar2(2)          default '1' not null,
   ISSHOW               varchar2(1)          default '0' not null,
   AUCTIONSTATUS        varchar2(1)          default '0' not null,
   AUCTIONID            NUMBER(11)           not null,
   constraint PK_T_E_AUCTION_ITEM primary key (ID)
);

comment on table "t_e_auction_item" is
'拍卖品表';

comment on column "t_e_auction_item".NAME is
'拍品名称';

comment on column "t_e_auction_item".TYPE is
'拍品类型：';

comment on column "t_e_auction_item".DESCRIPTION is
'介绍描述';

comment on column "t_e_auction_item".STARTTIME is
'开始拍卖时间';

comment on column "t_e_auction_item".ENDTIME is
'结束拍卖时间';

comment on column "t_e_auction_item".STARTPRICE is
'起拍价格';

comment on column "t_e_auction_item".CURPRICE is
'当前拍卖价格';

comment on column "t_e_auction_item".FINALPRICE is
'成交价格';

comment on column "t_e_auction_item".ADDPRICE is
'加价最低价格';

comment on column "t_e_auction_item".DEPOSITPRICE is
'保证金';

comment on column "t_e_auction_item".RATE is
'手续费比率';

comment on column "t_e_auction_item".DETAIL is
'拍品详情';

comment on column "t_e_auction_item".STATUS is
'状态：0-删除，1-未审核，2-审核未通过，3-正常(审核通过)';

comment on column "t_e_auction_item".ISSHOW is
'是否在首页显示：0-否，1-是';

comment on column "t_e_auction_item".AUCTIONSTATUS is
'拍卖状态：0-未开始拍卖，1-正在拍卖，2-拍卖成功，3-流拍';

comment on column "t_e_auction_item".AUCTIONID is
'拍卖会ID';

/*==============================================================*/
/* Table: "t_e_bid"                                             */
/*==============================================================*/
create table "t_e_bid" 
(
   ID                   NUMBER(11)           not null,
   WXUSERID             NUMBER(11)           not null,
   AUCTIONITEMID        NUMBER(11)           not null,
   BIDPRICE             NUMBER(11,3)         default 0 not null,
   STATUS               varchar2(1)          default '0' not null,
   BIDTIME              DATE                 default sysdate not null,
   constraint PK_T_E_BID primary key (ID)
);

comment on table "t_e_bid" is
'拍品出价表';

comment on column "t_e_bid".WXUSERID is
'出价人ID，关联WEIXIN_USER表';

comment on column "t_e_bid".AUCTIONITEMID is
'拍品ID';

comment on column "t_e_bid".BIDPRICE is
'出价价格';

comment on column "t_e_bid".STATUS is
'状态：0-出局，1-领先';

comment on column "t_e_bid".BIDTIME is
'出价时间';

/*==============================================================*/
/* Table: "t_e_business"                                        */
/*==============================================================*/
create table "t_e_business" 
(
   ID                   NUMBER(11)           not null,
   NAME                 varchar2(256)        not null,
   ADDRESS              varchar2(256),
   CREATETIME           DATE                 default sysdate not null,
   UPDATETIME           DATE                 default sysdate not null,
   TELNUM               varchar2(32),
   LOGOPATH             varchar2(128),
   STATUS               varchar2(1) not null,
   constraint PK_T_E_BUSINESS primary key (ID)
);

comment on table "t_e_business" is
'拍卖商家表';

comment on column "t_e_business".ID is
'ID-主键';

comment on column "t_e_business".NAME is
'商家名称';

comment on column "t_e_business".ADDRESS is
'商家地址';

comment on column "t_e_business".CREATETIME is
'创建时间';

comment on column "t_e_business".TELNUM is
'商家电话';

comment on column "t_e_business".LOGOPATH is
'图标地址';

comment on column "t_e_business".STATUS is
'状态：0-删除,1-正常';

create sequence SEQ_WEIXIN_USER_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

create sequence SEQ_t_con_item_res_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

create sequence SEQ_t_e_auction_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

create sequence SEQ_t_e_auction_item_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

create sequence SEQ_t_e_bid_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

create sequence SEQ_t_e_business_ID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 10;

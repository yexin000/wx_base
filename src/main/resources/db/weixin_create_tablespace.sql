create tablespace users  
datafile '/home/oracle/tablespace/weixin_data.dbf  ' size 1024M  
tempfile '/home/oracle/tablespace/weixin_data_temp.dbf ' size 1024M  
autoextend on 
 default storage(  
 initial 100K,  
 next 100k,  
); 
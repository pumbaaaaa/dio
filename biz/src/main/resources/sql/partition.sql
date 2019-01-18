-- 主表OID
-- 分区列名
-- 开始值
-- 间隔；interval 类型，用于时间分区表
-- 分多少个区
-- 不迁移数据
select
  create_range_partitions(
      't_floor'::regclass,
      'reply_time',
      '2010-01-01 00:00:00'::timestamp,
      interval '1 month',
      120,
      false);

--主表OID
--一个事务批量迁移多少记录
--获得行锁失败时，休眠多久再次获取，重试60次退出任务
select
  partition_table_concurrently(
      't_floor'::regclass,
      10000,
      1.0);

--设置主表不可用
select set_enable_parent('t_floor', false);

--查看分区列表
SELECT * FROM public.show_partition_list();

--查看迁移任务
select * from pathman_concurrent_part_tasks;

--删除分区，数据移动到主表
select drop_range_partition('t_floor',false);

--删除分区，数据也删除，不迁移到主表
select drop_range_partition('t_floor',true);

--删除所有分区，数据移动到主表
select drop_partitions('t_floor'::regclass, false);

--将分区从主表的继承关系中删除, 不删数据，删除继承关系，删除约束（指定分区名，转换为普通表）
select detach_range_partition('t_floor');
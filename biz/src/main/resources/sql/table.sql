DROP TABLE IF EXISTS t_post;
CREATE TABLE IF NOT EXISTS t_post (
  id serial8,
  topic_id numeric(12),
  user_id varchar(50),
  title varchar(100),
  url varchar(50),
  replies numeric(12),
  create_time timestamp(6) default now()
  );

DROP TABLE IF EXISTS t_floor;
CREATE TABLE IF NOT EXISTS t_floor (
  id serial8,
  topic_id numeric(12),
  user_id varchar(50),
  floor numeric(12),
  content text,
  url varchar(200),
  annex varchar(200),
  hash varchar(50),
  reply_time timestamp(6) not null,
  create_time timestamp(6) default now(),
  constraint u_t_floor_hash unique (hash)
  );


DROP TABLE IF EXISTS t_user;
CREATE TABLE IF NOT EXISTS t_user (
  id serial8,
  user_id varchar(50),
  user_name varchar(30),
  create_time timestamp(6) default now()
  );


DROP TABLE IF EXISTS t_err_log;
CREATE TABLE IF NOT EXISTS t_err_log (
  id serial8,
  last_replies numeric(12),
  url varchar(50),
  stack_trace text,
  create_time timestamp(6) default now()
  );


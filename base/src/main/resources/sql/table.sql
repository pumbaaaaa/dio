DROP TABLE IF EXISTS t_post;
CREATE TABLE IF NOT EXISTS t_post (
  topic_id numeric(12),
  user_id numeric(12),
  title varchar(50),
  replies numeric(12),
  create_time timestamp(6) default now()
);

DROP TABLE IF EXISTS t_floor;
CREATE TABLE IF NOT EXISTS t_floor (
  topic_id numeric(12),
  user_id numeric(12),
  floor numeric(12),
  replyTime timestamp(6),
  content text,
  hash varchar(50),
  create_time timestamp(6) default now(),
  constraint u_t_floor_hash unique (hash)
);


DROP TABLE IF EXISTS t_user;
CREATE TABLE IF NOT EXISTS t_user (
  user_id numeric(12),
  user_name varchar(30),
  create_time timestamp(6) default now()
);

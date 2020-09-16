package com.test.dio.biz.util;

import org.springframework.stereotype.Component;

@Component
public class ModuConfProvider {

    private static final String IF_SCRIPT = "<if test=\"%s != null and %s != ''\">\nAND %s = #{%s}\n</if>\n";

    public String validScriptSql() {

        String aaa = "";
        String bbb = null;

        SQL sql = new SQL();
        sql.SELECT(bbb);
        sql.SELECT(aaa);
        sql.SELECT("reply_time b");
        sql.SELECT("content c");
        sql.FROM("t_floor");
        sql.WHERE("create_time >= current_date - interval '3 year'");
        sql.WHERE("create_time < current_date");
        sql.WHERE_IF_SCRIPT(String.format(IF_SCRIPT, "floor", "floor", "floor", "floor"));
        sql.LIMIT(100);

        System.out.println(sql.toString());
        System.out.println(sql.getScriptSql());

        return sql.toString();
    }

    public String validSql(SQL sql) {

        return sql.toString();
    }

}

package com.test.dio.biz.util;

import org.springframework.stereotype.Component;

@Component
public class ModuConfProvider {

    private static final String IF_SCRIPT = "<if test=\"%s != null and %s != ''\">\nAND %s = #{%s}\n</if>\n";

    public String validScriptSql() {

        SQL sql = new SQL();
        sql.SELECT("to_char(b.stt_date, 'yyyy-MM-dd') stt_date");
        sql.SELECT("count(a.*)");
        sql.FROM("t_floor a ");
        sql.RIGHT_OUTER_JOIN("(select generate_series(current_date, (current_date + interval '7 day'), '1 day')::date stt_date) b on date(a.create_time) = b.stt_date");
        sql.WHERE("b.stt_date >= current_date");
        sql.WHERE("b.stt_date < current_date + interval '7 day'");
        sql.GROUP_BY("to_char(b.stt_date, 'yyyy-MM-dd')");
        sql.ORDER_BY("to_char(b.stt_date, 'yyyy-MM-dd')");


        return sql.toString();
    }

    public String validSql(SQL sql) {

        return sql.toString();
    }

}

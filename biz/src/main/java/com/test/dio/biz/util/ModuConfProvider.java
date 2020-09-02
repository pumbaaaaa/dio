package com.test.dio.biz.util;

import com.test.dio.biz.util.ScriptSQL;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModuConfProvider {

    public String queryData(Map<String, String> param) throws IllegalAccessException, InstantiationException {

//        SQL sql = new SQL();
//
//        sql.SELECT("stt_time aaa, admdvs bbb");
//        sql.FROM("biz_opt_cnt_stt_d");
//        sql.WHERE("admdvs = #{regionCode}");
//        sql.GROUP_BY("admdvs");

        ScriptSQL sql = ScriptSQL.class.newInstance();
        sql.SELECT("stt_time aaa, admdvs bbb");
        sql.FROM("biz_opt_cnt_stt_d");
        sql.WHERE("admdvs = #{regionCode}");
        sql.GROUP_BY("admdvs");


        return sql.toString();
    }
}

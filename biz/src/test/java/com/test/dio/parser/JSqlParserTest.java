package com.test.dio.parser;

import com.test.dio.biz.config.BizConfig;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.StringReader;
import java.util.List;


@SpringBootTest(classes = BizConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class JSqlParserTest {


    @Test
    public void testSqlParser() throws JSQLParserException {
        String sql = "SELECT\n" +
                "\t(select admdvs_name from dim_admdvs_info) AS NAME,\n" +
                "\tWARN_DATE AS VALUE\n" +
                "FROM\n" +
                "\twarn_info_d a \n" +
                "WHERE\n" +
                "\tWARN_DATE >= CURRENT_DATE - INTERVAL 1 DAY \n" +
                "\tAND WARN_DATE < CURRENT_DATE \n" +
                "\tAND warn_kpi_type = '02' \n";

        Statement parse = new CCJSqlParserManager().parse(new StringReader(sql));
        Select select = (Select) parse;
        SelectBody selectBody = select.getSelectBody();

        PlainSelect plainSelect = (PlainSelect) selectBody;


        List<SelectItem> selectItems = plainSelect.getSelectItems();
        for (SelectItem selectItem : selectItems) {
            SelectExpressionItem item = (SelectExpressionItem) selectItem;
            Expression expression = item.getExpression();

            if (expression instanceof SubSelect) {
                SubSelect subSelect = (SubSelect) expression;
                SelectBody subSelectBody = subSelect.getSelectBody();
                System.out.println(subSelectBody);
            }
        }

    }
}

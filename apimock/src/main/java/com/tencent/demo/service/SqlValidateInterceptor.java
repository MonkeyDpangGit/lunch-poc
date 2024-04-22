package com.tencent.demo.service;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),})
public class SqlValidateInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler，进行自定义处理
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // get sql
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql().trim().toLowerCase();

        if (sql.startsWith("update") || sql.startsWith("insert")) {
            // get parameters
            List<ParameterMapping> parameterMap = boundSql.getParameterMappings();
            Map<String, Object> parameterObject = (Map<String, Object>) boundSql.getParameterObject();
            // check
            DBService.checkUpsertParameter(sql, parameterMap, parameterObject);
        }

        // 继续执行后续操作
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 设置属性，可以用来配置拦截器的行为
    }
}

package com.tencent.demo.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DBService
 *
 * @author torrisli
 * @date 2024/4/19
 * @Description: DBService
 */
@Component
public class DBService {

    public static Map<String, TableMeta> TABLE_META = new Hashtable<>();

    public static Map<String, Insert> INSERT_SQL_STATEMENT = new Hashtable<>();
    public static Map<String, Update> UPDATE_SQL_STATEMENT = new Hashtable<>();

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void init() {

        try (Connection connection = sqlSessionFactory.openSession().getConnection();) {

            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            ResultSet resultSet = metaData.getTables(catalog, null, null, new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME").toLowerCase();

                ResultSet columnsResultSet = metaData.getColumns(catalog, null, tableName, null);
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME").toLowerCase();
                    String dataType = columnsResultSet.getString("TYPE_NAME");
                    Integer columnSize = Integer.valueOf(columnsResultSet.getString("COLUMN_SIZE"));
                    Object isNullable = columnsResultSet.getObject("IS_NULLABLE");
                    String remarks = columnsResultSet.getString("REMARKS");

                    TableMeta tableMeta = new TableMeta();
                    tableMeta.setTableName(tableName);
                    tableMeta.setColumnName(columnName);
                    tableMeta.setDataType(dataType);
                    tableMeta.setColumnSize(columnSize);
                    tableMeta.setIsNullable(isNullable);
                    tableMeta.setRemarks(remarks);

                    TABLE_META.put(tableName + "." + columnName, tableMeta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查update、insert语句参数
     *
     * @param sql 表名
     * @param parameterMappingList 字段映射
     * @param parameterObject 字段值
     * @throws SQLException
     */
    public static void checkUpsertParameter(String sql, List<ParameterMapping> parameterMappingList,
            Map<String, Object> parameterObject) throws SQLException, JSQLParserException {

        if (sql.startsWith("insert")) {
            Insert statement = INSERT_SQL_STATEMENT.get(sql);
            if (statement == null) {
                statement = (Insert) CCJSqlParserUtil.parse(sql);
                INSERT_SQL_STATEMENT.put(sql, statement);
            }
            String tableName = statement.getTable().getName();

            List<Column> columns = statement.getColumns();
            ExpressionList itemsList = (ExpressionList) statement.getItemsList();
            List<Expression> insertValues = itemsList.getExpressions();
            int i = 0;
            for (int j = 0; j < columns.size(); j++) {
                Column column = columns.get(j);
                String paramName = column.getColumnName().toLowerCase();
                String paramValue = insertValues.get(j).toString();
                if ("?".equals(paramValue)) {
                    paramValue = String.valueOf(parameterObject.get(parameterMappingList.get(i).getProperty()));
                    i++;
                }
                // do check
                doCheckLength(tableName, paramName, paramValue);
            }
        }

        if (sql.startsWith("update")) {
            Update statement = UPDATE_SQL_STATEMENT.get(sql);
            if (statement == null) {
                statement = (Update) CCJSqlParserUtil.parse(sql);
                UPDATE_SQL_STATEMENT.put(sql, statement);
            }
            String tableName = statement.getTable().getName();

            ArrayList<UpdateSet> updateSets = statement.getUpdateSets();
            int i = 0;
            for (UpdateSet us : updateSets) {
                String paramName = us.getColumns().get(0).getColumnName().toLowerCase();
                String paramValue = us.getExpressions().get(0).toString();
                if ("?".equals(paramValue)) {
                    paramValue = String.valueOf(parameterObject.get(parameterMappingList.get(i).getProperty()));
                    i++;
                }
                // do check
                doCheckLength(tableName, paramName, paramValue);
            }
        }
    }

    /**
     * 检查字段长短
     *
     * @param tableName 表名
     * @param paramName 字段名
     * @param paramValue 字段值
     * @throws SQLException
     */
    private static void doCheckLength(String tableName, String paramName, String paramValue) throws SQLException {

        String key = new StringBuilder().append(tableName).append(".").append(paramName).toString().toLowerCase();

        TableMeta meta = TABLE_META.get(key);
        if (meta != null && "VARCHAR".equals(meta.getDataType())) {
            int fixColumnSize = meta.getColumnSize() * 2 / 3;
            if (paramValue != null && paramValue.length() > fixColumnSize) {
                throw new SQLException(key + " length over size, value:" + paramValue);
            }
        }
    }

    private class TableMeta {

        private String tableName;

        private String columnName;

        private String dataType;

        private Integer columnSize;

        private Object isNullable;

        private String remarks;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Integer getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(Integer columnSize) {
            this.columnSize = columnSize;
        }

        public Object getIsNullable() {
            return isNullable;
        }

        public void setIsNullable(Object isNullable) {
            this.isNullable = isNullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}

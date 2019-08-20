package com.self.wq.freemarker.domain;

/**
 * @author: wq
 * @date: 2018/9/3 13:44
 */
public class TableCol {

    private String tableName;

    private String colName;

    private String indexNum;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getIndexNum() {
        return this.indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }
}

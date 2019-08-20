package com.self.wq.freemarker.domain;

import com.self.wq.freemarker.domain.TableCol;

import java.util.List;

/**
 * @author: wq
 * @date: 2018/9/3 13:52
 */
public class FlowNode {

    private String fromFlowNodeCode;

    private String tablename;

    private String flowNodeCode;

    private String flowNodeName;

    private String flowNodeType;

    private String flowCode;

    private String flowName;

    private String joinRowKey;

    private String toRowkey;

    private String prefixStr;

    private String suffixStr;

    public String getJoinRowKey() {
        return this.joinRowKey;
    }

    public void setJoinRowKey(String joinRowKey) {
        this.joinRowKey = joinRowKey;
    }

    public String getFromFlowNodeCode() {
        return this.fromFlowNodeCode;
    }

    public void setFromFlowNodeCode(String fromFlowNodeCode) {
        this.fromFlowNodeCode = fromFlowNodeCode;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getFlowNodeCode() {
        return this.flowNodeCode;
    }

    public void setFlowNodeCode(String flowNodeCode) {
        this.flowNodeCode = flowNodeCode;
    }

    public String getFlowNodeName() {
        return this.flowNodeName;
    }

    public void setFlowNodeName(String flowNodeName) {
        this.flowNodeName = flowNodeName;
    }

    public String getFlowNodeType() {
        return this.flowNodeType;
    }

    public void setFlowNodeType(String flowNodeType) {
        this.flowNodeType = flowNodeType;
    }

    public String getFlowCode() {
        return this.flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getFlowName() {
        return this.flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getToRowkey() {
        return this.toRowkey;
    }

    public void setToRowkey(String toRowkey) {
        this.toRowkey = toRowkey;
    }

    public String getPrefixStr() {
        return this.prefixStr;
    }

    public void setPrefixStr(String prefixStr) {
        this.prefixStr = prefixStr;
    }

    public String getSuffixStr() {
        return this.suffixStr;
    }

    public void setSuffixStr(String suffixStr) {
        this.suffixStr = suffixStr;
    }
}

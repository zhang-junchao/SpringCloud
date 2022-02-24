package com.zjc.odps.service;

import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description ODPS 操作对象
 * @date : 2021/12/2 1:06 下午
 */
public class OdpsService {

    /**
     *  获取 odps 连接对象
     * @param endPoint
     * @param accessId
     * @param accessKey
     * @param project
     * @return
     */
    public static Odps getConn(String endPoint, String accessId, String accessKey, String project) {
        Account account = new AliyunAccount(accessId, accessKey);
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);
        return odps;

    }


    /**
     * 获取表信息
     * @param stmt
     * @return
     */
    public ArrayList<Map<String, String>> showTables(Odps stmt) {
        ArrayList<Map<String, String>> tablelist = new ArrayList<>();
        Tables tables = stmt.tables();
        for (Table table : tables) {
            Map<String, String> tableInfo = new HashMap<>();
            tableInfo.put("tableName", table.getName());
            tableInfo.put("remarks", table.getComment());
            tablelist.add(tableInfo);
        }
        return tablelist;
    }


    /**
     * 获取字段信息
     * @param stmt
     * @param tablename
     * @return
     * @throws OdpsException
     */
    public ArrayList<Map<String, String>> showTableFields(Odps stmt, String tablename) throws OdpsException {
        ArrayList<Map<String, String>> tableFieldlist = new ArrayList<>();
        Table t = stmt.tables().get(tablename);
        t.reload();
        TableSchema schema = t.getSchema();
        List<Column> columns = schema.getColumns();
        for (Column column : columns) {
            Map<String, String> fieldInfo = new HashMap<>();
            fieldInfo.put("columnName", column.getName());
            fieldInfo.put("remarks", column.getComment());
            tableFieldlist.add(fieldInfo);
        }
        return tableFieldlist;
    }

    public void getTableData(Odps stmt, String tablename) throws OdpsException {
        String sql = "select * from " + tablename + " limit 5;";
        Instance i = SQLTask.run(stmt, sql);
        i.waitForSuccess();
        List<Record> records = SQLTask.getResult(i);
        for (Record r : records) {
            String fielddata1 = r.getString(1);
            String fielddata = r.getString("name");
        }
    }


}

package com.test;

import com.structuer.DataBase;
import com.structuer.Table;

import java.util.ArrayList;
import java.util.List;

public class ALLDatabase {

    public static DataBase dataBase;

    public ALLDatabase(){
        dataBase=new DataBase("testBD");
    }

    public static void setDataBase(DataBase dataBase) {
        ALLDatabase.dataBase = dataBase;
    }
    public static List<Table> getTable(){
        return dataBase.getTableList();
    }
    public static List<String> getTablesName(){
        List<String> TablesName = new ArrayList<>();
        for (Table table:ALLDatabase.getTable()) {
            System.out.println(table);
            TablesName.add(table.getTableName());
        }
        return TablesName;
    }
}
/*
package com.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    public List<String> parserCommand(String sql)
    {
        List<String> list = new ArrayList<String>();
        sql=sql.trim();
        if(!sql.endsWith(";")){
            throw new DBMSException("语句结尾没有分号！");
        }
        sql=sql.toUpperCase();
        System.out.println(sql);


        if(Pattern.compile("^GRANT{1}.{1,}ON{1}.{1,}TO{1}.{1,}+").matcher(sql).find())
        {



            String db=sql.substring(sql.indexOf("ON")+2, sql.indexOf("TO")).trim();
            int temp=db.indexOf('.');

            if(temp!=-1)
            {
                list.add("授权给表命令");
                list.add(db.substring(temp+1).trim());
            }
            else
            {
                list.add("授权给数据库命令");
                list.add(db.trim());
            }

            list.add(sql.substring(sql.indexOf("GRANT")+5, sql.indexOf("ON")).trim());
            list.add(sql.substring(sql.indexOf("TO")+2,sql.indexOf(";")).trim());

        }

        if(Pattern.compile("^REVOKE{1}.{1,}ON{1}.{1,}TO{1}.{1,}+").matcher(sql).find())
        {



            String db=sql.substring(sql.indexOf("ON")+2, sql.indexOf("TO")).trim();
            int temp=db.indexOf('.');

            if(temp!=-1)
            {
                list.add("收回授权给表命令");
                list.add(db.substring(temp+1).trim());
            }
            else
            {
                list.add("收回授权给数据库命令");
                list.add(db.trim());
            }

            list.add(sql.substring(sql.indexOf("REVOKE")+5, sql.indexOf("ON")).trim());
            list.add(sql.substring(sql.indexOf("TO")+2,sql.indexOf(";")).trim());

        }


        if(Pattern.compile("^SHOW{1} {1,}DATABASES;+").matcher(sql).find())
        {
            list.add("显示所有库");
        }

        if(Pattern.compile("^DROP{1} {1,}DATABASE .{1,};+").matcher(sql).find())
        {
            list.add("删除库");
            Matcher tempDBName = Pattern.compile("DATABASE(.*);").matcher(sql);
            if(tempDBName.find())
            {
                String str = tempDBName.group().toString();
                //获取到库名
                String DBName = str.substring(str.indexOf(" ")+1,str.indexOf(";"));
                if(DBName.trim().length()>0){

                    list.add(DBName.trim());
                }else
                    throw  new DBMSException("缺少库名");

                System.out.println("库名："+ DBName);
            }
        }

        if(Pattern.compile("^DROP{1} {1,}TABLE .{1,};+").matcher(sql).find())
        {
            list.add("删除表");
            Matcher tempTblName = Pattern.compile("TABLE(.*);").matcher(sql);
            if(tempTblName.find())
            {
                String str = tempTblName.group().toString();
                //获取到表名
                String tblName = str.substring(str.indexOf(" ")+1,str.indexOf(";"));
                if(tblName.trim().length()>0){

                    list.add(tblName.trim());
                }else
                    throw  new DBMSException("缺少表名");

                System.out.println("表名："+tblName);
            }
        }
        if(Pattern.compile("^SHOW{1} {1,}TABLES;+").matcher(sql).find())
        {
            list.add("显示所有表");
        }

        if(Pattern.compile("^INSERT{1} {1,}INTO .{1,}VALUES(.{0,})+").matcher(sql).find())
        {
            System.out.println("插入语句");
            list.add("插入语句");
            Matcher tempTblName = Pattern.compile("INTO(.*)VALUES").matcher(sql);
            if(tempTblName.find())
            {
                String str = tempTblName.group().toString();
                //获取到表名
                String tblName = str.substring(str.indexOf(" ")+1,str.indexOf(" VALUES"));
                if(tblName.trim().length()>0){

                    list.add(tblName.trim());
                }else
                    throw  new DBMSException("缺少表名");

                System.out.println("表名："+tblName);
            }

            Matcher tempTblCountent = Pattern.compile("\\(.*\\)").matcher(sql);
            if(tempTblCountent.find()){


                String str = tempTblCountent.group().toString();
                String tblCountent = str.substring(str.indexOf("(")+1,str.indexOf(")"));


                //获取每个属性的对应值
                String[] countent = null;
                countent = tblCountent.split("\\,");
                for(int i=0;i<countent.length;i++){

                    list.add(countent[i]);
                }



            }

        }

        if(Pattern.compile("^DELETE{1} {1,}FROM.{1,}WHERE.{1,}+").matcher(sql).find()){

            System.out.println("删除数据语句");
            list.add("删除数据语句");
            Matcher tempTblName = Pattern.compile("FROM.{1,}WHERE").matcher(sql);
            if(tempTblName.find()){

                System.out.println(tempTblName.group());
                String str = tempTblName.group().toString();
                String tblName = str.substring(str.indexOf(" ")+1,str.indexOf(" WHERE"));
                System.out.println("表名："+tblName);
                list.add(tblName);

            }
            Matcher tempTblCountent = Pattern.compile("WHERE.{1,};").matcher(sql);
            if(tempTblCountent.find()){


                String str = tempTblCountent.group().toString();
                String tblCountent = str.substring(str.indexOf(" ")+1,str.indexOf(";"));
                System.out.println(tblCountent);

                //获取修改条件
                String[] countent = null;
                countent = tblCountent.split(" AND ");
                for(int i=0;i<countent.length;i++){
                    list.add(countent[i]);
                }
                System.out.println("条件："+ Arrays.toString(countent));

            }
        }
        if(Pattern.compile("^UPDATE{1}.{1,}SET{1}.{1,}WHERE.{1,}+").matcher(sql).find()){

            System.out.println("修改数据语句");
            list.add("修改数据语句");
            Matcher tempTblName = Pattern.compile("UPDATE {1,}.{1,} {1,}SET").matcher(sql);
            if(tempTblName.find()){

                String str = tempTblName.group().toString().trim();

                String tblName = str.substring(str.indexOf(" ")+1,str.indexOf(" SET")).trim();
                System.out.println("表名："+tblName);
                if(tblName.length()==0){
                    throw new DBMSException("无表名！");
                }else{
                    list.add(tblName);
                    System.out.println("表名："+tblName);
                }

            }else
                throw new DBMSException("语句有误！");
            Matcher tempRankName = Pattern.compile("SET {1,}.{1,} {1,}WHERE").matcher(sql);
            if(tempRankName.find()){

                System.out.println(tempRankName.group());
                String str = tempRankName.group().toString();
                String rankName = str.substring(str.indexOf(" ")+1,str.indexOf(" WHERE"));
                list.add(rankName);
                System.out.println("修改目标："+rankName);
            }else
                throw new DBMSException("语句有误！");

            Matcher tempTblCountent = Pattern.compile("WHERE {1,}.{1,};").matcher(sql);
            if(tempTblCountent.find()){


                String str = tempTblCountent.group().toString();
                String tblCountent = str.substring(str.indexOf(" ")+1,str.indexOf(";"));
                System.out.println(tblCountent);

                //获取修改条件
                String[] countent = null;
                countent = tblCountent.split(" AND ");
                for(int i=0;i<countent.length;i++){
                    list.add(countent[i]);
                }
                System.out.println("条件："+Arrays.toString(countent));

            }
            else
                throw new DBMSException("语句有误！");
        }

        if(Pattern.compile("^SELECT{1}.{1,}FROM {1}.{1,}+").matcher(sql).find()){

            System.out.println("查询数据语句");
            list.add("查询数据语句");
            if (sql.contains("WHERE")) {
                Matcher tempTblName = Pattern.compile("FROM {1,}.{1,} {1,}WHERE").matcher(sql);
                if (tempTblName.find()) {

                     System.out.println(tempTblName.group());
                    String str = tempTblName.group().toString();
                    String tblName = str.substring(str.indexOf(" ") + 1, str.indexOf(" WHERE"));
                     System.out.println("表名："+tblName);
                    list.add(tblName);
                }
                else{

                    throw new DBMSException("语句有误！");}
                Matcher tempRankName = Pattern.compile("SELECT {1,}.{1,} {1,}FROM").matcher(sql);
                if (tempRankName.find()) {

                     System.out.println(tempRankName.group());
                    String str = tempRankName.group().toString();
                    String rankName = str.substring(str.indexOf(" ") + 1, str.indexOf(" FROM"));
                     System.out.println("查询列名："+rankName);
                    list.add(rankName.trim());
                }
                else{
                    throw new DBMSException("语句有误！");}
                list.add("-");
                Matcher tempTblCountent = Pattern.compile("WHERE {1,}.{1,};").matcher(sql);
                if (tempTblCountent.find()) {

                    String str = tempTblCountent.group().toString();
                    String tblCountent = str.substring(str.indexOf(" ") + 1, str.indexOf(";"));
                     System.out.println(tblCountent);

                    // 获取修改条件
                    String[] countent = null;
                    countent = tblCountent.split(" AND ");
                    for (int i = 0; i < countent.length; i++) {

                        list.add(countent[i]);
                    }
                     System.out.println("条件："+Arrays.toString(countent));

                }else{
                    throw new DBMSException("语句有误！");}
            }else{

                Matcher tempTblName = Pattern.compile("FROM {1,}.{1,};").matcher(sql);
                if (tempTblName.find()) {

                     System.out.println(tempTblName.group());
                    String str = tempTblName.group().toString();
                    String tblName = str.substring(str.indexOf(" ") + 1, str.indexOf(";"));
                     System.out.println("表名："+tblName);
                    list.add(tblName.trim());
                }else{
                    throw new DBMSException("语句有误！");}
                Matcher tempRankName = Pattern.compile("SELECT {1,}.{1,} {1,}FROM").matcher(sql);
                if (tempRankName.find()) {

                     System.out.println(tempRankName.group());
                    String str = tempRankName.group().toString();
                    String rankName = str.substring(str.indexOf(" ") + 1, str.indexOf(" FROM"));
                     System.out.println("查询列名："+rankName);
                    list.add(rankName);
                }
                else{
                    throw new DBMSException("语句有误！");}
                list.add("-");
            }
        }

        if(Pattern.compile("^CREATE{1} {1,}DATABASE{1}.{1,};").matcher(sql).find())
        {
            System.out.println("建数据库语句");
            list.add("创建数据库语句");
            Matcher tempDBName = Pattern.compile("DATABASE.{1,}+").matcher(sql);
            if(tempDBName.find()){

                System.out.println(tempDBName.group());
                String str = tempDBName.group().toString();
                String DBName = str.substring(str.indexOf(" ")+1,str.indexOf(";"));
                System.out.println("数据库名："+DBName);
                list.add(DBName);
            }else{
                throw new DBMSException("语句有误！");}

        }
        if(Pattern.compile("^COMMIT{1}").matcher(sql).find())
        {

            list.add("事务提交");


        }
        if(Pattern.compile("^CREATE{1}.{1,}TABLE{1} {1,}.{1,} {1,}()").matcher(sql).find())
        {
            System.out.println("建表语句");
            list.add("建表语句");
            Matcher tempTBLName = Pattern.compile("TABLE{1} .{1,};").matcher(sql);
            if(tempTBLName.find()){


                String str = tempTBLName.group().toString();

                String tblName = str.substring(str.indexOf(" ")+1,str.indexOf("("));

                list.add(tblName);
            }

            Matcher tempTblCountent = Pattern.compile("\\(.*\\);").matcher(sql);
            if(tempTblCountent.find()){


                String str = tempTblCountent.group().toString();
                String tblCountent = str.substring(str.indexOf("(")+1,str.indexOf(");"));



                String[] countent = null;
                countent = tblCountent.split("\\,");
                System.out.println("值："+Arrays.toString(countent));
                for(int i=0;i<countent.length;i++){
                    list.add(countent[i]);
                }
            }

        }

        if(Pattern.compile("^USE{1} {1,}.*;").matcher(sql).find())
        {

            String s=sql.substring(3, sql.length()-1);
            s=s.trim();
            System.out.println(s);
            list.add("使用数据库");
            list.add(s);

        }
        if(Pattern.compile("^EXIT+").matcher(sql).find())
        {
            list.add("exit");
        }

        if(list.size()==0)
        {
            throw new DBMSException("命令错误，请重新输入");
        }

        return list;


    }

}
*/

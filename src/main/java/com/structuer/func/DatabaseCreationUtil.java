package com.structuer.func;

import com.parser.ast.stmt.ddl.DDLCreateDatabaseStatement;
import com.structuer.DataBase;
import com.structuer.Result;

import java.io.IOException;

public class DatabaseCreationUtil {

    /**
     * 执行数据库创建操作。
     *
     * @param createDatabaseStatement DDLCreateDatabaseStatement 对象
     */
    public static Result executeCreateDatabase(DDLCreateDatabaseStatement createDatabaseStatement) throws IOException {
        String dbName = createDatabaseStatement.getDatabaseName().getIdText();
        // 检查是否存在
        if (IbdFileHandler.findIbdFileByDatabaseName(dbName)!=null) {
            if(createDatabaseStatement.isIfNotExists()){
                System.out.println("数据库 " + dbName + " 已存在，跳过创建。");
                return new Result(null,null,null,"true");
            }else {
                System.out.println("数据库 " + dbName + " 已存在，创建失败。");
                return new Result(null,null,null,"false");
            }


        }
        // 创建数据库
        DataBase database=new DataBase(dbName);
        IbdFileHandler.saveToIbdFile(database);
        System.out.println("数据库 " + dbName + " 创建成功。");
        return new Result(null,null,null,"true");
    }
}

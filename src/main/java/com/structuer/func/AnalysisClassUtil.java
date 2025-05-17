package com.structuer.func;

import com.parser.ast.stmt.ddl.*;
import com.parser.ast.stmt.dml.*;
import com.structuer.DataBase;
import com.structuer.Result;
import com.test.ALLDatabase;

import java.io.IOException;

public class AnalysisClassUtil {

    public static Result analyzeSQLStatement(Object ast,DataBase dataBase) throws IOException {
        // 获取类名
        String className = ast.getClass().getSimpleName();

        try {
            // 使用 switch 语句处理不同类型的 SQL 语句
            switch (className) {
                // 处理 DML 更新语句
                case "DMLUpdateStatement" -> {
                    System.out.println("更新语句");
                    DMLUpdateStatement updateStatement = (DMLUpdateStatement) ast;
                    UpdateUtil util=new UpdateUtil();
                    return util.updateTable(updateStatement,dataBase);
                    // TODO: 处理逻辑...
                }
                // 处理 DML 插入语句
                case "DMLInsertStatement" -> {
                    System.out.println("插入语句");
                    DMLInsertStatement insertStatement = (DMLInsertStatement) ast;
                    return InsertUtil.executeInsert(insertStatement,dataBase);
                    // TODO: 处理逻辑...
                }
                // 处理 DML 删除语句
                case "DMLDeleteStatement" -> {
                    System.out.println("删除语句");
                    DMLDeleteStatement deleteStatement = (DMLDeleteStatement) ast;
                    return DeleteUtil.executeDelete(deleteStatement,dataBase);
                    // TODO: 处理逻辑...
                }
                // 处理 DML 查询语句
                case "DMLSelectStatement" -> {
                    System.out.println("查询语句");
                    DMLSelectStatement selectStatement = (DMLSelectStatement) ast;
                    return QueryUtil.executeSelect(selectStatement, dataBase);

                    // TODO: 处理逻辑...
                }
                // 处理 DML 调用语句
                case "DMLCallStatement" -> {
                    DMLCallStatement callStatement = (DMLCallStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DML 替换语句
                case "DMLReplaceStatement" -> {
                    DMLReplaceStatement replaceStatement = (DMLReplaceStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DML 插入替换语句
                case "DMLInsertReplaceStatement" -> {
                    DMLInsertReplaceStatement insertReplaceStatement = (DMLInsertReplaceStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DML 联合查询语句
                case "DMLSelectUnionStatement" -> {
                    DMLSelectUnionStatement selectUnionStatement = (DMLSelectUnionStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建表语句
                case "DDLCreateTableStatement" -> {
                    System.out.println("创建表语句");
                    DDLCreateTableStatement createTableStatement = (DDLCreateTableStatement) ast;
                    return TableCreationUtil.createTableFromDDL(createTableStatement,dataBase,100);
                }
                // 处理 DDL 删除表语句
                case "DDLDropTableStatement" -> {
                    DDLDropTableStatement dropTableStatement = (DDLDropTableStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 修改表语句
                case "DDLAlterTableStatement" -> {
                    DDLAlterTableStatement alterTableStatement = (DDLAlterTableStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建索引语句
                case "DDLCreateIndexStatement" -> {
                    DDLCreateIndexStatement createIndexStatement = (DDLCreateIndexStatement) ast;
                    // TODO: 处理逻辑...
                }
                //处理DDL创建数据库
                case "DDLCreateDatabaseStatement"->{
                    System.out.println("数据库创建");
                    DDLCreateDatabaseStatement createDatabaseStatement=  (DDLCreateDatabaseStatement) ast;
                    return DatabaseCreationUtil.executeCreateDatabase(createDatabaseStatement);

                }

                // 处理 DDL 删除索引语句
                case "DDLDropIndexStatement" -> {
                    DDLDropIndexStatement dropIndexStatement = (DDLDropIndexStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建视图语句
                case "DDLCreateViewStatement" -> {
                    DDLCreateViewStatement createViewStatement = (DDLCreateViewStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 修改视图语句
                case "DDLAlterViewStatement" -> {
                    DDLAlterViewStatement alterViewStatement = (DDLAlterViewStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建函数语句
                case "DDLCreateFunctionStatement" -> {
                    DDLCreateFunctionStatement createFunctionStatement = (DDLCreateFunctionStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建过程语句
                case "DDLCreateProcedureStatement" -> {
                    DDLCreateProcedureStatement createProcedureStatement = (DDLCreateProcedureStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 创建触发器语句
                case "DDLCreateTriggerStatement" -> {
                    DDLCreateTriggerStatement createTriggerStatement = (DDLCreateTriggerStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 截断表语句
                case "DDLTruncateStatement" -> {
                    DDLTruncateStatement truncateStatement = (DDLTruncateStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 重命名表语句
                case "DDLRenameTableStatement" -> {
                    DDLRenameTableStatement renameTableStatement = (DDLRenameTableStatement) ast;
                    // TODO: 处理逻辑...
                }
                // 处理 DDL 切换数据库语句
                case "DDLUseDatabaseStatement" -> {
                    DDLUseDatabaseStatement useDatabaseStatement = (DDLUseDatabaseStatement) ast;
                    if (IbdFileHandler.findIbdFileByDatabaseName(ALLDatabase.dataBase.getName()) != null) {
                        return new Result(null,null,null,"true");
                    }
                    ALLDatabase.setDataBase(null);
                    return new Result(null,null,null,"false");
                }
                // 处理解释语句
                case "ExplainStatement" -> {
                    ExplainStatement explainStatement = (ExplainStatement) ast;
                    // TODO: 处理逻辑...
                }
            }
        } catch (ClassCastException e) {
            System.err.println("强制转换失败: " + e.getMessage());
        }
        return new Result(null,null,null,"false");
    }

    public static Result analyzeSQLStatement(Object ast) throws IOException
    {
        String className = ast.getClass().getSimpleName();

        return new Result(null,null,null,"false"); // 返回false
    }

}

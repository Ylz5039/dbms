package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.structuer.func.IbdFileHandler;
import com.test.ALLDatabase;

import java.io.IOException;

public class DDLUseDatabaseStatement implements DDLStatement {
    private final Identifier databaseName;

    public DDLUseDatabaseStatement(Identifier databaseName) throws IOException {
        this.databaseName = databaseName;
        ALLDatabase.setDataBase(IbdFileHandler.loadFromIbdFile(this.getDatabaseName().getIdText()));
    }

    public Identifier getDatabaseName() {
        return databaseName;
    }

    public String toString() {
        return "DDLUseDatabaseStatement{" + "\n" +
                "databaseName=" + databaseName + "\n" +
                '}';
    }
}

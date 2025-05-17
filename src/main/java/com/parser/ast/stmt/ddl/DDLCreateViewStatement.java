package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.recognizer.mysql.syntax.MySQLDDLParser.Algorithm;
import com.parser.recognizer.mysql.syntax.MySQLDDLParser.SqlSecurity;

public class DDLCreateViewStatement implements DDLStatement {
    public boolean isOrRepalce = false;

    private final Algorithm algorithm;

    private final Expression definer;

    private final SqlSecurity sqlSecurity;

    private final Identifier view;

    public DDLCreateViewStatement(Algorithm algorithm, Expression definer, SqlSecurity sqlSecurity,
                                  Identifier view) {
        super();
        this.algorithm = algorithm;
        this.definer = definer;
        this.sqlSecurity = sqlSecurity;
        this.view = view;
    }

    public boolean isOrRepalce() {
        return isOrRepalce;
    }

    public void setOrRepalce(boolean orRepalce) {
        isOrRepalce = orRepalce;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Expression getDefiner() {
        return definer;
    }

    public SqlSecurity getSqlSecurity() {
        return sqlSecurity;
    }

    public Identifier getView() {
        return view;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateViewStatement {\n")
                .append("    isOrReplace=").append(isOrRepalce).append("\n")
                .append("    algorithm=").append(algorithm).append("\n")
                .append("    definer=").append(definer).append("\n")
                .append("    sqlSecurity=").append(sqlSecurity).append("\n")
                .append("    view=").append(view).append("\n")
                .append("}");
        return sb.toString();
    }

}

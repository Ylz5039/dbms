package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.ddl.datatype.DataType;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.condition.Characteristics;
import com.parser.util.Pair;

import java.util.List;

public class DDLCreateFunctionStatement implements DDLStatement {
    private final Expression definer;
    private final Identifier name;
    private final List<Pair<Identifier, DataType>> parameters;
    private final DataType returns;
    private final Characteristics characteristics;
    private final SQLStatement stmt;

    public DDLCreateFunctionStatement(Expression definer, Identifier name,
                                      List<Pair<Identifier, DataType>> parameters, DataType returns,
                                      Characteristics characteristics, SQLStatement stmt) {
        this.definer = definer;
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
        this.characteristics = characteristics;
        this.stmt = stmt;
    }

    public Expression getDefiner() {
        return definer;
    }

    public Identifier getName() {
        return name;
    }

    public List<Pair<Identifier, DataType>> getParameters() {
        return parameters;
    }

    public DataType getReturns() {
        return returns;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateFunctionStatement {\n")
                .append("    definer=").append(definer).append("\n")
                .append("    name=").append(name).append("\n")
                .append("    parameters=").append(parameters).append("\n")
                .append("    returns=").append(returns).append("\n")
                .append("    characteristics=").append(characteristics).append("\n")
                .append("    stmt=").append(stmt).append("\n")
                .append("}");
        return sb.toString();
    }

}

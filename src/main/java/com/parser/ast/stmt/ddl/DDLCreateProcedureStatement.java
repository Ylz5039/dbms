package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.ddl.datatype.DataType;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.condition.Characteristics;
import com.parser.util.Tuple3;

import java.util.List;

public class DDLCreateProcedureStatement implements DDLStatement {
    public enum ProcParameterType {
        IN, OUT, INOUT, NONE
    }

    private final Expression definer;
    private final Identifier name;
    private final List<Tuple3<ProcParameterType, Identifier, DataType>> parameters;
    private final Characteristics characteristics;
    private final SQLStatement stmt;

    public DDLCreateProcedureStatement(Expression definer, Identifier name,
                                       List<Tuple3<ProcParameterType, Identifier, DataType>> parameters,
                                       Characteristics characteristics, SQLStatement stmt) {
        this.definer = definer;
        this.name = name;
        this.parameters = parameters;
        this.characteristics = characteristics;
        this.stmt = stmt;
    }

    public Expression getDefiner() {
        return definer;
    }

    public Identifier getName() {
        return name;
    }

    public List<Tuple3<ProcParameterType, Identifier, DataType>> getParameters() {
        return parameters;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateProcedureStatement {\n")
                .append("    definer=").append(definer).append("\n")
                .append("    name=").append(name).append("\n")
                .append("    parameters=").append(parameters).append("\n")
                .append("    characteristics=").append(characteristics).append("\n")
                .append("    stmt=").append(stmt).append("\n")
                .append("}");
        return sb.toString();
    }

}

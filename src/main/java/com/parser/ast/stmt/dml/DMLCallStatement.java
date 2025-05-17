package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLCallStatement extends DMLStatement {
    private final Identifier procedure;
    private final List<Expression> arguments;

    public DMLCallStatement(Identifier procedure, List<Expression> arguments) {
        this.procedure = procedure;
        if (arguments == null || arguments.isEmpty()) {
            this.arguments = Collections.emptyList();
        } else if (arguments instanceof ArrayList) {
            this.arguments = arguments;
        } else {
            this.arguments = new ArrayList<Expression>(arguments);
        }
    }

    public DMLCallStatement(Identifier procedure) {
        this.procedure = procedure;
        this.arguments = Collections.emptyList();
    }

    public Identifier getProcedure() {
        return procedure;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLCallStatement {\n")
                .append("    procedure=").append(procedure).append("\n")
                .append("    arguments=").append(arguments).append("\n")
                .append("}");
        return sb.toString();
    }

}

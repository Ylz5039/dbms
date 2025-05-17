package com.parser.ast.stmt.extension;

import com.parser.ast.expression.Expression;
import com.parser.ast.stmt.SQLStatement;

import java.util.ArrayList;

public class ExecutePrepareStatement implements SQLStatement {
    private final String name;
    private final ArrayList<Expression> vars;

    public ExecutePrepareStatement(String name, ArrayList<Expression> vars) {
        this.name = name;
        this.vars = vars;
    }


    public ArrayList<Expression> getVars() {
        return vars;
    }

    public String getName() {
        return name;
    }
}

package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.VariableExpression;
import com.parser.ast.stmt.SQLStatement;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DALSetStatement implements SQLStatement {
    private final List<Pair<VariableExpression, Expression>> assignmentList;

    public DALSetStatement(List<Pair<VariableExpression, Expression>> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            this.assignmentList = Collections.emptyList();
        } else if (assignmentList instanceof ArrayList) {
            this.assignmentList = assignmentList;
        } else {
            this.assignmentList =
                    new ArrayList<Pair<VariableExpression, Expression>>(assignmentList);
        }
    }

    public List<Pair<VariableExpression, Expression>> getAssignmentList() {
        return assignmentList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DALSetStatement {\n");
        for (Pair<VariableExpression, Expression> assignment : assignmentList) {
            VariableExpression variable = assignment.getKey();
            Expression expression = assignment.getValue();
            sb.append("    ").append(variable.toString()).append(" = ").append(expression.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}

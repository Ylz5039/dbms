package com.parser.ast.expression.primary;

import com.parser.ast.expression.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RowExpression extends PrimaryExpression {
    private List<Expression> rowExprList;

    public RowExpression(List<Expression> rowExprList) {
        if (rowExprList == null || rowExprList.isEmpty()) {
            this.rowExprList = Collections.emptyList();
        } else if (rowExprList instanceof ArrayList) {
            this.rowExprList = rowExprList;
        } else {
            this.rowExprList = new ArrayList<Expression>(rowExprList);
        }
    }

    public List<Expression> getRowExprList() {
        return rowExprList;
    }

    public String toString() {
        return "RowExpression{" +
                "rowExprList=" + rowExprList +
                '}';
    }
}

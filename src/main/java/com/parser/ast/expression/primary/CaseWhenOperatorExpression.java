package com.parser.ast.expression.primary;

import com.parser.ast.expression.Expression;
import com.parser.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class CaseWhenOperatorExpression extends PrimaryExpression {
    private Expression comparee;
    private final List<Pair<Expression, Expression>> whenList;
    private Expression elseResult;

    public CaseWhenOperatorExpression(Expression comparee,
                                      List<Pair<Expression, Expression>> whenList, Expression elseResult) {
        super();
        this.comparee = comparee;
        if (whenList instanceof ArrayList) {
            this.whenList = whenList;
        } else {
            this.whenList = new ArrayList<Pair<Expression, Expression>>(whenList);
        }
        this.elseResult = elseResult;
    }

    public Expression getComparee() {
        return comparee;
    }

    public List<Pair<Expression, Expression>> getWhenList() {
        return whenList;
    }

    public Expression getElseResult() {
        return elseResult;
    }

    public void setComparee(Expression exp) {
        this.comparee = exp;
    }

    public void setElseResult(Expression exp) {
        this.elseResult = exp;
    }

    public String toString() {
        return "CaseWhenOperatorExpression{" +
                "comparee=" + comparee +
                ", whenList=" + whenList +
                ", elseResult=" + elseResult +
                '}';
    }
}

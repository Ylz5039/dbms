package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.Expression;
import com.parser.ast.stmt.compound.CompoundStatement;
import com.parser.util.Pair;
import com.parser.ast.stmt.compound.condition.SignalStatement.*;

import java.util.List;

public class GetDiagnosticsStatement implements CompoundStatement {
    public enum StatementInfoItemName {
        NUMBER, ROW_COUNT
    }
    public enum DiagnosticType {
        NONE, CURRENT, STACKED
    }

    private final DiagnosticType type;
    private final List<Pair<Expression, StatementInfoItemName>> statementItems;
    private final Expression conditionNumber;
    private final List<Pair<Expression, ConditionInfoItemName>> conditionItems;

    public GetDiagnosticsStatement(DiagnosticType type,
                                   List<Pair<Expression, StatementInfoItemName>> statementItems,
                                   Expression conditionNumber,
                                   List<Pair<Expression, ConditionInfoItemName>> conditionItems) {
        this.type = type;
        this.statementItems = statementItems;
        this.conditionNumber = conditionNumber;
        this.conditionItems = conditionItems;
    }

    public DiagnosticType getType() {
        return type;
    }

    public List<Pair<Expression, StatementInfoItemName>> getStatementItems() {
        return statementItems;
    }

    public Expression getConditionNumber() {
        return conditionNumber;
    }

    public List<Pair<Expression, ConditionInfoItemName>> getConditionItems() {
        return conditionItems;
    }

    public String toString() {
        return "GetDiagnosticsStatement{" +
                "type=" + type +
                ", statementItems=" + statementItems +
                ", conditionNumber=" + conditionNumber +
                ", conditionItems=" + conditionItems +
                '}';
    }
}

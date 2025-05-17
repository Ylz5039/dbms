package com.parser.ast.expression.primary;

import com.parser.ast.expression.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchExpression extends PrimaryExpression {
    public static enum Modifier {
        _DEFAULT, IN_BOOLEAN_MODE, IN_NATURAL_LANGUAGE_MODE, IN_NATURAL_LANGUAGE_MODE_WITH_QUERY_EXPANSION, WITH_QUERY_EXPANSION
    }

    private final List<Expression> columns;
    private Expression pattern;
    private final Modifier modifier;

    public MatchExpression(List<Expression> columns, Expression pattern, Modifier modifier) {
        if (columns == null || columns.isEmpty()) {
            this.columns = Collections.emptyList();
        } else if (columns instanceof ArrayList) {
            this.columns = columns;
        } else {
            this.columns = new ArrayList<Expression>(columns);
        }
        this.pattern = pattern;
        this.modifier = modifier;
    }

    public List<Expression> getColumns() {
        return columns;
    }

    public Expression getPattern() {
        return pattern;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public void setPattern(Expression exp) {
        this.pattern = exp;
    }

    public String toString() {
        return "MatchExpression{" +
                "columns=" + columns +
                ", pattern=" + pattern +
                ", modifier=" + modifier +
                '}';
    }
}

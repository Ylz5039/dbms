package com.parser.ast.expression.primary;

import com.parser.ast.expression.misc.QueryExpression;
import com.parser.ast.fragment.tableref.TableReference;

import java.util.Map;

public class ParamMarker extends PrimaryExpression implements TableReference, QueryExpression {
    private final int paramIndex;
    private String alias;

    public ParamMarker(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public ParamMarker(int paramIndex, String alias) {
        this.paramIndex = paramIndex;
        this.alias = alias;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int hashCode() {
        return paramIndex;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof ParamMarker) {
            ParamMarker that = (ParamMarker) obj;
            return this.paramIndex == that.paramIndex;
        }
        return false;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return parameters.get(paramIndex);
    }

    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return false;
    }

    public String toString() {
        return "ParamMarker{" +
                "paramIndex=" + paramIndex +
                ", alias='" + alias + '\'' +
                '}';
    }
}

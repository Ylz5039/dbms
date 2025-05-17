package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.stmt.SQLStatement;

import java.util.ArrayList;
import java.util.List;

public class DMLStatement implements SQLStatement {
    protected static List ensureListType(List list) {
        if (list == null || list.size() <= 0)
            return null;
        if (list instanceof ArrayList)
            return list;
        return new ArrayList(list);
    }

    protected static List<List<Expression>> checkAndConvertValuesList(
            List<List<Expression>> valuesList) {
        if (valuesList == null || valuesList.isEmpty()) {
            throw new IllegalArgumentException("argument 'valuesList' is empty");
        }
        List<List<Expression>> rst = (valuesList instanceof ArrayList) ? valuesList
                : new ArrayList<List<Expression>>(valuesList.size());
        boolean copy = rst != valuesList;
        int size = -1;
        if (copy) {
            for (List<Expression> values : valuesList) {
                if (values == null || values.size() <= 0) {
                    throw new IllegalArgumentException(
                            "argument 'valuesList' contains empty element");
                }
                if (size < 0) {
                    size = values.size();
                } else if (size != values.size()) {
                    throw new IllegalArgumentException(
                            "argument 'valuesList' contains empty elements with different size: "
                                    + size + " != " + values.size());
                }
                rst.add(ensureListType(values));
            }
            return rst;
        }
        for (int i = 0; i < valuesList.size(); ++i) {
            List<Expression> values = valuesList.get(i);
            if (values == null || values.size() <= 0) {
                throw new IllegalArgumentException("argument 'valuesList' contains empty element");
            }
            if (size < 0) {
                size = values.size();
            } else if (size != values.size()) {
                throw new IllegalArgumentException(
                        "argument 'valuesList' contains empty elements with different size: " + size
                                + " != " + values.size());
            }
            if (!(values instanceof ArrayList)) {
                valuesList.set(i, new ArrayList<Expression>(values));
            }
        }
        return rst;
    }
}

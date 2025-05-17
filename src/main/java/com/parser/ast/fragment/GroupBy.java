package com.parser.ast.fragment;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GroupBy implements ASTNode {
    private final List<Pair<Expression, SortOrder>> orderByList;
    private boolean withRollup = false;

    public GroupBy(Expression expr, SortOrder order, boolean withRollup) {
        this.orderByList = new ArrayList<Pair<Expression, SortOrder>>(1);
        this.orderByList.add(new Pair<Expression, SortOrder>(expr, order));
        this.withRollup = withRollup;
    }

    public GroupBy() {
        this.orderByList = new LinkedList<Pair<Expression, SortOrder>>();
    }

    public GroupBy addOrderByItem(Expression expr, SortOrder order) {
        orderByList.add(new Pair<Expression, SortOrder>(expr, order));
        return this;
    }

    public List<Pair<Expression, SortOrder>> getOrderByList() {
        return orderByList;
    }

    public boolean isWithRollup() {
        return withRollup;
    }

    public GroupBy setWithRollup() {
        withRollup = true;
        return this;
    }

    public String toString() {
        return "GroupBy{" +
                "orderByList=" + orderByList +
                ", withRollup=" + withRollup +
                '}';
    }
}

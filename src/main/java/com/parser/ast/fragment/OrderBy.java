package com.parser.ast.fragment;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderBy implements ASTNode {
    private final List<Pair<Expression, SortOrder>> orderByList;

    public List<Pair<Expression, SortOrder>> getOrderByList() {
        return orderByList;
    }

    public OrderBy() {
        this.orderByList = new LinkedList<Pair<Expression, SortOrder>>();
    }

    public OrderBy(Expression expr, SortOrder order) {
        this.orderByList = new ArrayList<Pair<Expression, SortOrder>>(1);
        this.orderByList.add(new Pair<Expression, SortOrder>(expr, order));
    }

    public OrderBy(List<Pair<Expression, SortOrder>> orderByList) {
        if (orderByList == null)
            throw new IllegalArgumentException("order list is null");
        this.orderByList = orderByList;
    }

    public OrderBy addOrderByItem(Expression expr, SortOrder order) {
        orderByList.add(new Pair<Expression, SortOrder>(expr, order));
        return this;
    }

    public String toString() {
        return "OrderBy{" +
                "orderByList=" + orderByList +
                '}';
    }
}

package com.parser.ast.stmt.extension;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.ddl.DDLStatement;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ExtDDLCreatePolicy implements DDLStatement {
    private final Identifier name;
    private final List<Pair<Integer, Expression>> proportion;

    public ExtDDLCreatePolicy(Identifier name) {
        this.name = name;
        this.proportion = new ArrayList<Pair<Integer, Expression>>(1);
    }

    public Identifier getName() {
        return name;
    }

    public List<Pair<Integer, Expression>> getProportion() {
        return proportion;
    }

    public ExtDDLCreatePolicy addProportion(Integer id, Expression val) {
        proportion.add(new Pair<Integer, Expression>(id, val));
        return this;
    }
}

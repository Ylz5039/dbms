package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.fragment.Limit;

public class ShowBinLogEvent extends DALShowStatement {
    private final String logName;
    private final Expression pos;
    private final Limit limit;

    public ShowBinLogEvent(String logName, Expression pos, Limit limit) {
        this.logName = logName;
        this.pos = pos;
        this.limit = limit;
    }

    public String getLogName() {
        return logName;
    }

    public Expression getPos() {
        return pos;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowBinLogEvent {\n")
                .append("    logName='").append(logName).append("',\n")
                .append("    pos=").append(pos != null ? pos.toString() : "null").append(",\n")
                .append("    limit=").append(limit != null ? limit.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }
}

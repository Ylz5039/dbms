package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralNumber;
import com.parser.ast.expression.primary.literal.LiteralString;

public class ConditionValue {
    public enum ConditionValueType {
        Unknown, ErrorCode, State, Name, Warning, NotFound, Exception
    }

    private ConditionValueType type = ConditionValueType.Unknown;
    private LiteralNumber mysqlErrorCode;
    private LiteralString sqlState;
    private Identifier conditionName;
    private boolean isWarning;
    private boolean isNotFound;
    private boolean isException;

    public ConditionValue(ConditionValueType type, Object value) {
        if (value == null) {
            return;
        }
        this.type = type;
        switch (type) {
            case ErrorCode:
                mysqlErrorCode = (LiteralNumber) value;
                break;
            case Exception:
                isException = (boolean) value;
                break;
            case Name:
                conditionName = (Identifier) value;
                break;
            case NotFound:
                isNotFound = (boolean) value;
                break;
            case State:
                sqlState = (LiteralString) value;
                break;
            case Warning:
                isWarning = (boolean) value;
                break;
            default:
                break;
        }
    }

    public ConditionValueType getType() {
        return type;
    }

    public Object getValue() {
        switch (type) {
            case ErrorCode:
                return mysqlErrorCode;
            case Exception:
                return isException;
            case Name:
                return conditionName;
            case NotFound:
                return isNotFound;
            case State:
                return sqlState;
            case Warning:
                return isWarning;
            default:
                return null;
        }
    }

    public String toString() {
        return "ConditionValue{" +
                "type=" + type +
                ", mysqlErrorCode=" + mysqlErrorCode +
                ", sqlState=" + sqlState +
                ", conditionName=" + conditionName +
                ", isWarning=" + isWarning +
                ", isNotFound=" + isNotFound +
                ", isException=" + isException +
                '}';
    }
}

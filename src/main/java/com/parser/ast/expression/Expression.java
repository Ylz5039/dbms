package com.parser.ast.expression;

import com.parser.ast.ASTNode;

import java.util.Map;

public interface Expression extends ASTNode {
    // 定义不同的操作优先级常量
    int PRECEDENCE_QUERY = 0;               // 查询语句的优先级
    int PRECEDENCE_ASSIGNMENT = 1;          // 赋值操作的优先级
    int PRECEDENCE_LOGICAL_OR = 2;          // 逻辑 OR 操作的优先级
    int PRECEDENCE_LOGICAL_XOR = 3;         // 逻辑 XOR 操作的优先级
    int PRECEDENCE_LOGICAL_AND = 4;         // 逻辑 AND 操作的优先级
    int PRECEDENCE_LOGICAL_NOT = 5;         // 逻辑 NOT 操作的优先级
    int PRECEDENCE_BETWEEN_AND = 6;         // BETWEEN ... AND 操作的优先级

    int PRECEDENCE_COMPARISION = 7;         // 比较操作的优先级

    int PRECEDENCE_ANY_ALL_SUBQUERY = 8;    // ANY/ALL 子查询的优先级
    int PRECEDENCE_BIT_OR = 8;               // 位 OR 操作的优先级
    int PRECEDENCE_BIT_AND = 10;             // 位 AND 操作的优先级
    int PRECEDENCE_BIT_SHIFT = 11;           // 位移操作的优先级
    int PRECEDENCE_ARITHMETIC_TERM_OP = 12;  // 算术项操作的优先级
    int PRECEDENCE_ARITHMETIC_FACTOR_OP = 13; // 算术因子操作的优先级
    int PRECEDENCE_BIT_XOR = 14;             // 位 XOR 操作的优先级
    int PRECEDENCE_UNARY_OP = 15;            // 一元操作的优先级
    int PRECEDENCE_BINARY = 16;              // 二元操作的优先级
    int PRECEDENCE_COLLATE = 17;             // 排序规则的优先级
    int PRECEDENCE_PRIMARY = 19;             // 主要操作的优先级

    int getPrecedence();

    Expression setCacheEvalRst(boolean cacheEvalRst);

    Object UNEVALUATABLE = new Object();

    Object evaluation(Map<? extends Object, ? extends Object> parameters);

    String originSQLStr();


}

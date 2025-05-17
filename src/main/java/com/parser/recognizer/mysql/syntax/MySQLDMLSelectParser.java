package com.parser.recognizer.mysql.syntax;

import com.parser.ast.expression.AbstractExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.fragment.GroupBy;
import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.OrderBy;
import com.parser.ast.fragment.tableref.Dual;
import com.parser.ast.fragment.tableref.TableReference;
import com.parser.ast.fragment.tableref.TableReferences;
import com.parser.ast.stmt.dml.DMLQueryStatement;
import com.parser.ast.stmt.dml.DMLSelectStatement;
import com.parser.recognizer.mysql.MySQLToken;
import com.parser.recognizer.mysql.lexer.MySQLLexer;
import com.parser.util.Pair;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

import static com.parser.recognizer.mysql.MySQLToken.*;

public class MySQLDMLSelectParser extends MySQLDMLParser {

    public MySQLDMLSelectParser(MySQLLexer lexer, MySQLExprParser exprParser) {
        super(lexer, exprParser);
        this.exprParser.setSelectParser(this);
    }

    private static enum SpecialIdentifier {
        SQL_BUFFER_RESULT, SQL_CACHE, SQL_NO_CACHE
    }

    private static final Map<String, SpecialIdentifier> specialIdentifiers =
            new HashMap<String, SpecialIdentifier>();

    static {
        specialIdentifiers.put("SQL_BUFFER_RESULT", SpecialIdentifier.SQL_BUFFER_RESULT);
        specialIdentifiers.put("SQL_CACHE", SpecialIdentifier.SQL_CACHE);
        specialIdentifiers.put("SQL_NO_CACHE", SpecialIdentifier.SQL_NO_CACHE);
    }

    private DMLSelectStatement.SelectOption selectOption() throws SQLSyntaxErrorException {
        for (DMLSelectStatement.SelectOption option = new DMLSelectStatement.SelectOption();; lexer
                .nextToken()) {
            outer: switch (lexer.token()) {
                case KW_ALL:
                    option.resultDup = DMLSelectStatement.SelectDuplicationStrategy.ALL;
                    break outer;
                case KW_DISTINCT:
                    option.resultDup = DMLSelectStatement.SelectDuplicationStrategy.DISTINCT;
                    break outer;
                case KW_DISTINCTROW:
                    option.resultDup = DMLSelectStatement.SelectDuplicationStrategy.DISTINCTROW;
                    break outer;
                case KW_HIGH_PRIORITY:
                    option.highPriority = true;
                    break outer;
                case KW_STRAIGHT_JOIN:
                    option.straightJoin = true;
                    break outer;
                case KW_SQL_SMALL_RESULT:
                    option.resultSize = DMLSelectStatement.SmallOrBigResult.SQL_SMALL_RESULT;
                    break outer;
                case KW_SQL_BIG_RESULT:
                    option.resultSize = DMLSelectStatement.SmallOrBigResult.SQL_BIG_RESULT;
                    break outer;
                case KW_SQL_CALC_FOUND_ROWS:
                    option.sqlCalcFoundRows = true;
                    break outer;
                case IDENTIFIER:
                    String optionStringUp = lexer.stringValueUppercase();
                    SpecialIdentifier specialId = specialIdentifiers.get(optionStringUp);
                    if (specialId != null) {
                        switch (specialId) {
                            case SQL_BUFFER_RESULT:
                                if (option.sqlBufferResult)
                                    return option;
                                option.sqlBufferResult = true;
                                break outer;
                            case SQL_CACHE:
                                if (option.queryCache != DMLSelectStatement.QueryCacheStrategy.UNDEF)
                                    return option;
                                option.queryCache = DMLSelectStatement.QueryCacheStrategy.SQL_CACHE;
                                break outer;
                            case SQL_NO_CACHE:
                                if (option.queryCache != DMLSelectStatement.QueryCacheStrategy.UNDEF)
                                    return option;
                                option.queryCache =
                                        DMLSelectStatement.QueryCacheStrategy.SQL_NO_CACHE;
                                break outer;
                            default:
                                option.unknownOption = true;
                                break outer;
                        }
                    }
                default:
                    option.unknownOption = true;
                    return option;
            }
        }
    }

    private List<Pair<Expression, String>> selectExprList() throws SQLSyntaxErrorException {
        int s = exprParser.lexer.getLastIndex();
        Expression expr = exprParser.expression();
        int e = exprParser.lexer.getLastIndex();
        if (expr instanceof Identifier) {
            switch (((Identifier) expr).getIdText().toUpperCase()) {
                case "TIME":
                case "DATE":
                case "TIMESTAMP":
                    if (lexer.token() == LITERAL_CHARS) {
                        ArrayList<Byte> bytes = new ArrayList<>();
                        do {
                            lexer.appendStringContent(bytes);
                        } while (lexer.nextToken() == MySQLToken.LITERAL_CHARS);
                        byte[] data = new byte[bytes.size()];
                        for (int i = 0, size = bytes.size(); i < size; i++) {
                            data[i] = bytes.get(i);
                        }
                        expr = new LiteralString(null, data, false).setCacheEvalRst(cacheEvalRst);
                    }
                    break;
            }
        } else {
            if (e - s > 0 && expr instanceof AbstractExpression) {
                ((AbstractExpression) expr).setOriginSQL(exprParser.lexer.getSQL(s + 1, e));
            }
        }
        String alias = as();
        List<Pair<Expression, String>> list;
        if (lexer.token() == PUNC_COMMA) {
            list = new LinkedList<Pair<Expression, String>>();
            list.add(new Pair<Expression, String>(expr, alias));
        } else {
            list = new ArrayList<Pair<Expression, String>>(1);
            list.add(new Pair<Expression, String>(expr, alias));
            return list;
        }
        for (; lexer.token() == PUNC_COMMA; list.add(new Pair<Expression, String>(expr, alias))) {
            lexer.nextToken();
            s = exprParser.lexer.getLastIndex();
            expr = exprParser.expression();
            e = exprParser.lexer.getLastIndex();
            if (expr instanceof Identifier) {
                switch (((Identifier) expr).getIdText().toUpperCase()) {
                    case "TIME":
                    case "DATE":
                    case "TIMESTAMP":
                        if (lexer.token() == LITERAL_CHARS) {
                            ArrayList<Byte> bytes = new ArrayList<>();
                            do {
                                lexer.appendStringContent(bytes);
                            } while (lexer.nextToken() == MySQLToken.LITERAL_CHARS);
                            byte[] data = new byte[bytes.size()];
                            for (int i = 0, size = bytes.size(); i < size; i++) {
                                data[i] = bytes.get(i);
                            }
                            expr = new LiteralString(null, data, false)
                                    .setCacheEvalRst(cacheEvalRst);
                        }
                        break;
                }
            } else {
                if (e - s > 0 && expr instanceof AbstractExpression) {
                    ((AbstractExpression) expr).setOriginSQL(exprParser.lexer.getSQL(s, e));
                }
            }
            alias = as();
        }
        return list;
    }

    public DMLSelectStatement select() throws SQLSyntaxErrorException {
        match(KW_SELECT);
        DMLSelectStatement.SelectOption option = selectOption();
        List<Pair<Expression, String>> exprList = selectExprList();
        TableReferences tables = null;
        Expression where = null;
        GroupBy group = null;
        Expression having = null;
        OrderBy order = null;
        Limit limit = null;

        boolean dual = false;
        if (lexer.token() == KW_FROM) {
            if (lexer.nextToken() == KW_DUAL) {
                lexer.nextToken();
                dual = true;
                List<TableReference> trs = new ArrayList<TableReference>(1);
                trs.add(new Dual());
                tables = new TableReferences(trs);
            } else {
                tables = tableRefs();
            }
        }
        if (lexer.token() == KW_WHERE) {
            lexer.nextToken();
            where = exprParser.expression();
        }
        if (!dual) {
            group = groupBy();
            if (lexer.token() == KW_HAVING) {
                lexer.nextToken();
                having = exprParser.expression();
            }
            order = orderBy();
            if (group != null && group.isWithRollup() && order != null) {
                throw new SQLSyntaxErrorException("Incorrect usage of CUBE/ROLLUP and order by");
            }
        }
        limit = limit();
        if (!dual) {
            switch (lexer.token()) {
                case KW_FOR:
                    lexer.nextToken();
                    match(KW_UPDATE);
                    option.lockMode = DMLSelectStatement.LockMode.FOR_UPDATE;
                    break;
                case KW_LOCK:
                    lexer.nextToken();
                    match(KW_IN);
                    matchIdentifier("SHARE");
                    matchIdentifier("MODE");
                    option.lockMode = DMLSelectStatement.LockMode.LOCK_IN_SHARE_MODE;
                    break;
                case KW_PROCEDURE:
                case KW_INTO:
                    // throw new ForbiddenFunctionException("PROCEDURE ANALYSE");
                    break;
            }
        }
        option.version = lexer.getVersion();
        return new DMLSelectStatement(option, exprList, tables, where, group, having, order, limit);
    }

    public DMLQueryStatement selectUnion(boolean isSubQuery) throws SQLSyntaxErrorException {
        DMLSelectStatement select = selectPrimary();
        DMLQueryStatement query = buildUnionSelect(select, isSubQuery);
        return query;
    }
}

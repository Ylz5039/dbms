package com.parser.recognizer.mysql.syntax;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.misc.QueryExpression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.RowExpression;
import com.parser.ast.stmt.dml.DMLReplaceStatement;
import com.parser.recognizer.mysql.lexer.MySQLLexer;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.parser.recognizer.mysql.MySQLToken.*;

public class MySQLDMLReplaceParser extends MySQLDMLInsertReplaceParser {
    public MySQLDMLReplaceParser(MySQLLexer lexer, MySQLExprParser exprParser) {
        super(lexer, exprParser);
    }

    public DMLReplaceStatement replace() throws SQLSyntaxErrorException {
        match(KW_REPLACE);
        DMLReplaceStatement.ReplaceMode mode = DMLReplaceStatement.ReplaceMode.UNDEF;
        switch (lexer.token()) {
            case KW_LOW_PRIORITY:
                lexer.nextToken();
                mode = DMLReplaceStatement.ReplaceMode.LOW;
                break;
            case KW_DELAYED:
                lexer.nextToken();
                mode = DMLReplaceStatement.ReplaceMode.DELAY;
                break;
            default:
                break;
        }
        if (lexer.token() == KW_INTO) {
            lexer.nextToken();
        }
        Identifier table = identifier();
        List<Identifier> columnNameList;
        List<RowExpression> rowList;
        QueryExpression select;

        List<Expression> tempRowValue;
        switch (lexer.token()) {
            case KW_SET:
                lexer.nextToken();
                columnNameList = new LinkedList<Identifier>();
                tempRowValue = new LinkedList<Expression>();
                for (;; lexer.nextToken()) {
                    Identifier id = identifier();
                    match(OP_EQUALS, OP_ASSIGN);
                    Expression expr = exprParser.expression();
                    columnNameList.add(id);
                    tempRowValue.add(expr);
                    if (lexer.token() != PUNC_COMMA) {
                        break;
                    }
                }
                rowList = new ArrayList<RowExpression>(1);
                rowList.add(new RowExpression(tempRowValue));
                return new DMLReplaceStatement(mode, table, columnNameList, rowList);
            case IDENTIFIER:
                if (!"VALUE".equals(lexer.stringValueUppercase())) {
                    break;
                }
            case KW_VALUES:
                lexer.nextToken();
                columnNameList = null;
                rowList = rowList();
                return new DMLReplaceStatement(mode, table, columnNameList, rowList);
            case KW_SELECT:
                columnNameList = null;
                select = select();
                return new DMLReplaceStatement(mode, table, columnNameList, select);
            case PUNC_LEFT_PAREN:
                switch (lexer.nextToken()) {
                    case PUNC_LEFT_PAREN:
                    case KW_SELECT:
                        columnNameList = null;
                        select = selectPrimary();
                        match(PUNC_RIGHT_PAREN);
                        return new DMLReplaceStatement(mode, table, columnNameList, select);
                    default:
                        break;
                }
                columnNameList = idList();
                match(PUNC_RIGHT_PAREN);
                switch (lexer.token()) {
                    case PUNC_LEFT_PAREN:
                    case KW_SELECT:
                        select = selectPrimary();
                        return new DMLReplaceStatement(mode, table, columnNameList, select);
                    case KW_VALUES:
                        lexer.nextToken();
                        break;
                    default:
                        matchIdentifier("VALUE");
                }
                rowList = rowList();
                return new DMLReplaceStatement(mode, table, columnNameList, rowList);
            default:
                break;
        }
        throw err("unexpected token for replace: " + lexer.token());
    }
}

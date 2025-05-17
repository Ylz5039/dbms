package com.parser.recognizer.mysql.lexer;

import com.parser.util.CharTypes;
import com.parser.recognizer.mysql.MySQLToken;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.List;

public class MySQLLexer {
    private static int C_STYLE_COMMENT_VERSION = 50599;

    public static int setCStyleCommentVersion(int version) {
        int v = C_STYLE_COMMENT_VERSION;
        C_STYLE_COMMENT_VERSION = version;
        return v;
    }

    private final static byte EOI = 0x1A;

    protected final byte[] sql;

    protected final int eofIndex;

    protected int curIndex = -1;

    protected byte ch;

    private MySQLToken token;

    private MySQLToken tokenCache;

    private MySQLToken tokenCache2;

    private int paramIndex = 0;

    protected final static ThreadLocal<byte[]> sbufRef = new ThreadLocal<byte[]>();

    protected byte[] sbuf;

    private byte[] stringValue;

    private byte[] stringValueUppercase;

    protected int version;

    public int getVersion() {
        return version;
    }

    protected void updateStringValue(final byte[] src, final int srcOffset, final int len) {

        stringValue = Arrays.copyOfRange(src, srcOffset, srcOffset + len);
        final int end = srcOffset + len;
        boolean lowerCase = false;
        int srcIndex = srcOffset;
        for (; srcIndex < end; ++srcIndex) {
            byte c = src[srcIndex];
            if (c >= 'a' && c <= 'z') {
                lowerCase = true;
                if (srcIndex > srcOffset) {
                    System.arraycopy(src, srcOffset, sbuf, 0, srcIndex - srcOffset);
                }
                break;
            }
        }
        if (lowerCase) {
            for (int destIndex = srcIndex - srcOffset; destIndex < len; ++destIndex) {
                byte c = src[srcIndex++];
                if (c >= 'a' && c <= 'z') {
                    sbuf[destIndex] = (byte) (c - 32);
                } else {
                    sbuf[destIndex] = c;
                }
            }
            stringValueUppercase = Arrays.copyOfRange(sbuf, 0, len);
        } else {
            stringValueUppercase = Arrays.copyOfRange(src, srcOffset, srcOffset + len);
        }
    }

    public MySQLLexer(byte[] sql) throws SQLSyntaxErrorException {
        // 当文字的字符缓冲区未分配空间，进行分配空间
        if ((this.sbuf = sbufRef.get()) == null) {
            this.sbuf = new byte[1024];
            sbufRef.set(this.sbuf);
        }
        // 当SQL语句byte最后一位不是空格
        if (CharTypes.isWhitespace(sql[sql.length - 1])) {
            this.sql = sql;
        } else {
            this.sql = new byte[sql.length + 1];
            System.arraycopy(sql, 0, this.sql, 0, sql.length);
        }
        // 标记sql语句结束游标
        this.eofIndex = this.sql.length - 1;
        // 标记sql语句结束标记
        this.sql[this.eofIndex] = MySQLLexer.EOI;
        // 切换下一个SQL语句字符
        scanChar();
        // 下一个词法（Token）
        nextToken();
    }

    /**
     * 格式化SQL语句并传给带参语法解析器初始化
     * @param sql 待分析的SQL语句
     * @throws SQLSyntaxErrorException 语句异常抛出
     */
    public MySQLLexer(String sql) throws SQLSyntaxErrorException {
        this(fromSQL2Bytes(sql));
    }

    /**
     * 格式化SQL语句方法
     * @param sql 待处理的SQL语句
     * @return 处理后的SQL语句
     */
    private static byte[] fromSQL2Bytes(String sql) {
        if (CharTypes.isWhitespace(sql.charAt(sql.length() - 1))) {
            return sql.getBytes();
        }
        byte[] bytes = sql.getBytes();
        byte[] chars = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, chars, 0, bytes.length);
        chars[chars.length - 1] = ' ';
        return chars;
    }

    protected MySQLKeywords keywods = MySQLKeywords.DEFAULT_KEYWORDS;

    /**
     * @param token must be a keyword
     */
    public final void addCacheToke(MySQLToken token) {
        if (tokenCache != null) {
            tokenCache2 = token;
        } else {
            tokenCache = token;
        }
    }

    public final MySQLToken token() {
        if (tokenCache2 != null) {
            return tokenCache2;
        }
        if (tokenCache != null) {
            return tokenCache;
        }
        return token;
    }

    public final int getCurrentIndex() {
        return this.curIndex;
    }

    public final byte[] getSQL() {
        return sql;
    }

    public String getSQL(int start, int end) {
        byte[] c = new byte[end - start];
        System.arraycopy(sql, start, c, 0, c.length);
        return new String(c);
    }

    public int getOffsetCache() {
        return offsetCache;
    }

    public int getSizeCache() {
        return sizeCache;
    }

    public int paramIndex() {
        return paramIndex;
    }

    protected final byte scanChar() {
        return ch = sql[++curIndex];
    }

    protected final byte scanChar(int skip) {
        return ch = sql[curIndex += skip];
    }

    protected final boolean hasChars(int howMany) {
        return curIndex + howMany <= eofIndex;
    }

    protected final boolean eof() {
        return curIndex >= eofIndex;
    }

    private MySQLToken nextTokenInternal() throws SQLSyntaxErrorException {
        switch (ch) {
            case '0':
                switch (sql[curIndex + 1]) {
                    case 'x':
                        scanChar(2);
                        scanHexaDecimal(false);
                        return token;
                    case 'b':
                        scanChar(2);
                        scanBitField(false);
                        return token;
                }
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                scanNumber();
                return token;
            case '.':
                if (CharTypes.isDigit(sql[curIndex + 1])) {
                    scanNumber();
                } else {
                    scanChar();
                    token = MySQLToken.PUNC_DOT;
                }
                return token;
            case '\'':
            case '"':
                scanString();
                return token;
            case 'n':
            case 'N':
                if (sql[curIndex + 1] == '\'') {
                    scanChar();
                    scanString();
                    token = MySQLToken.LITERAL_NCHARS;
                    return token;
                }
                scanIdentifier();
                return token;
            case 'x':
            case 'X':
                if (sql[curIndex + 1] == '\'') {
                    scanChar(2);
                    scanHexaDecimal(true);
                    return token;
                }
                scanIdentifier();
                return token;
            case 'b':
            case 'B':
                if (sql[curIndex + 1] == '\'') {
                    scanChar(2);
                    scanBitField(true);
                    return token;
                }
                scanIdentifier();
                return token;
            case '@':
                if (sql[curIndex + 1] == '@') {
                    scanSystemVariable();
                    return token;
                }
                scanUserVariable();
                return token;
            case '?':
                scanChar();
                token = MySQLToken.QUESTION_MARK;
                ++paramIndex;
                return token;
            case '(':
                scanChar();
                token = MySQLToken.PUNC_LEFT_PAREN;
                return token;
            case ')':
                scanChar();
                token = MySQLToken.PUNC_RIGHT_PAREN;
                return token;
            case '[':
                scanChar();
                token = MySQLToken.PUNC_LEFT_BRACKET;
                return token;
            case ']':
                scanChar();
                token = MySQLToken.PUNC_RIGHT_BRACKET;
                return token;
            case '{':
                scanChar();
                token = MySQLToken.PUNC_LEFT_BRACE;
                return token;
            case '}':
                scanChar();
                token = MySQLToken.PUNC_RIGHT_BRACE;
                return token;
            case ',':
                scanChar();
                token = MySQLToken.PUNC_COMMA;
                return token;
            case ';':
                scanChar();
                token = MySQLToken.PUNC_SEMICOLON;
                return token;
            case ':':
                if (sql[curIndex + 1] == '=') {
                    scanChar(2);
                    token = MySQLToken.OP_ASSIGN;
                    return token;
                }
                scanChar();
                token = MySQLToken.PUNC_COLON;
                return token;
            case '=':
                scanChar();
                token = MySQLToken.OP_EQUALS;
                return token;
            case '~':
                scanChar();
                token = MySQLToken.OP_TILDE;
                return token;
            case '*':
                if (inCStyleComment && sql[curIndex + 1] == '/') {
                    inCStyleComment = false;
                    inCStyleCommentIgnore = false;
                    scanChar(2);
                    token = MySQLToken.PUNC_C_STYLE_COMMENT_END;
                    return token;
                }
                scanChar();
                token = MySQLToken.OP_ASTERISK;
                return token;
            case '-':
                scanChar();
                token = MySQLToken.OP_MINUS;
                return token;
            case '+':
                scanChar();
                token = MySQLToken.OP_PLUS;
                return token;
            case '^':
                scanChar();
                token = MySQLToken.OP_CARET;
                return token;
            case '/':
                scanChar();
                token = MySQLToken.OP_SLASH;
                return token;
            case '%':
                scanChar();
                token = MySQLToken.OP_PERCENT;
                return token;
            case '&':
                if (sql[curIndex + 1] == '&') {
                    scanChar(2);
                    token = MySQLToken.OP_LOGICAL_AND;
                    return token;
                }
                scanChar();
                token = MySQLToken.OP_AMPERSAND;
                return token;
            case '|':
                if (sql[curIndex + 1] == '|') {
                    scanChar(2);
                    token = MySQLToken.OP_LOGICAL_OR;
                    return token;
                }
                scanChar();
                token = MySQLToken.OP_VERTICAL_BAR;
                return token;
            case '!':
                if (sql[curIndex + 1] == '=') {
                    scanChar(2);
                    token = MySQLToken.OP_NOT_EQUALS;
                    return token;
                }
                scanChar();
                token = MySQLToken.OP_EXCLAMATION;
                return token;
            case '>':
                switch (sql[curIndex + 1]) {
                    case '=':
                        scanChar(2);
                        token = MySQLToken.OP_GREATER_OR_EQUALS;
                        return token;
                    case '>':
                        scanChar(2);
                        token = MySQLToken.OP_RIGHT_SHIFT;
                        return token;
                    default:
                        scanChar();
                        token = MySQLToken.OP_GREATER_THAN;
                        return token;
                }
            case '<':
                switch (sql[curIndex + 1]) {
                    case '=':
                        if (sql[curIndex + 2] == '>') {
                            scanChar(3);
                            token = MySQLToken.OP_NULL_SAFE_EQUALS;
                            return token;
                        }
                        scanChar(2);
                        token = MySQLToken.OP_LESS_OR_EQUALS;
                        return token;
                    case '>':
                        scanChar(2);
                        token = MySQLToken.OP_LESS_OR_GREATER;
                        return token;
                    case '<':
                        scanChar(2);
                        token = MySQLToken.OP_LEFT_SHIFT;
                        return token;
                    default:
                        scanChar();
                        token = MySQLToken.OP_LESS_THAN;
                        return token;
                }
            case '`':
                scanIdentifierWithAccent();
                return token;
            default:
                if (CharTypes.isIdentifierChar(ch)) {
                    scanIdentifier();
                } else if (eof()) {
                    token = MySQLToken.EOF;
                    curIndex = eofIndex;
                    // tokenPos = curIndex;
                } else {
                    throw err("unsupported character: " + ch);
                }
                return token;
        }
    }

    /**
     * 切换下一个Token命令
     * @return
     * @throws SQLSyntaxErrorException SQL语句异常抛出
     */
    public MySQLToken nextToken() throws SQLSyntaxErrorException {
        lastIndex = curIndex;
        if (tokenCache2 != null) {
            tokenCache2 = null;
            return tokenCache;
        }
        if (tokenCache != null) {
            tokenCache = null;
            return token;
        }
        if (token == MySQLToken.EOF) {
            throw new SQLSyntaxErrorException(
                    "eof for sql is already reached, cannot get new token");
        }
        MySQLToken t;
        do {
            skipSeparator();
            t = nextTokenInternal();
        } while (inCStyleComment && inCStyleCommentIgnore
                || MySQLToken.PUNC_C_STYLE_COMMENT_END == t);
        return t;
    }

    protected int lastIndex;

    protected boolean inCStyleComment;

    protected boolean inCStyleCommentIgnore;

    protected int offsetCache;

    protected int sizeCache;

    protected void scanUserVariable() throws SQLSyntaxErrorException {
        if (ch != '@')
            throw err("first char must be @");
        offsetCache = curIndex;
        sizeCache = 1;

        boolean dq = false;
        switch (scanChar()) {
            case '"':
                dq = true;
            case '\'':
                loop1: for (++sizeCache;; ++sizeCache) {
                    switch (scanChar()) {
                        case '\\':
                            ++sizeCache;
                            scanChar();
                            break;
                        case '"':
                            if (dq) {
                                ++sizeCache;
                                if (scanChar() == '"') {
                                    break;
                                }
                                break loop1;
                            }
                            break;
                        case '\'':
                            if (!dq) {
                                ++sizeCache;
                                if (scanChar() == '\'') {
                                    break;
                                }
                                break loop1;
                            }
                            break;
                    }
                }
                break;
            case '`':
                loop1: for (++sizeCache;; ++sizeCache) {
                    switch (scanChar()) {
                        case '`':
                            ++sizeCache;
                            if (scanChar() == '`') {
                                break;
                            }
                            break loop1;
                    }
                }
                break;
            default:
                for (; CharTypes.isIdentifierChar(ch) || ch == '.'; ++sizeCache) {
                    scanChar();
                }
        }

        stringValue = Arrays.copyOfRange(sql, offsetCache, offsetCache + sizeCache);
        token = MySQLToken.USR_VAR;
    }

    protected void scanSystemVariable() throws SQLSyntaxErrorException {
        if (ch != '@' || sql[curIndex + 1] != '@')
            throw err("first char must be @@");
        offsetCache = curIndex + 2;
        sizeCache = 0;
        scanChar(2);
        if (ch == '`') {
            for (++sizeCache;; ++sizeCache) {
                if (scanChar() == '`') {
                    ++sizeCache;
                    if (scanChar() != '`') {
                        break;
                    }
                }
            }
        } else {
            for (; CharTypes.isIdentifierChar(ch); ++sizeCache) {
                scanChar();
            }
        }
        updateStringValue(sql, offsetCache, sizeCache);
        token = MySQLToken.SYS_VAR;
    }

    protected void scanString() throws SQLSyntaxErrorException {
        boolean dq = false;
        if (ch == '\'') {
        } else if (ch == '"') {
            dq = true;
        } else {
            throw err("first char must be \" or '");
        }

        offsetCache = curIndex;
        int size = 1;
        sbuf[0] = '\'';
        if (dq) {
            loop: while (true) {
                switch (scanChar()) {
                    case '\'':
                        pubByte((byte) '\\', size++);
                        pubByte((byte) '\'', size++);
                        break;
                    case '\\':
                        pubByte((byte) '\\', size++);
                        pubByte(scanChar(), size++);
                        continue;
                    case '"':
                        if (sql[curIndex + 1] == '"') {
                            pubByte((byte) '"', size++);
                            scanChar();
                            continue;
                        }
                        pubByte((byte) '\'', size++);
                        scanChar();
                        break loop;
                    default:
                        if (eof()) {
                            throw err("unclosed string");
                        }
                        pubByte(ch, size++);
                        continue;
                }
            }
        } else {
            loop: while (true) {
                switch (scanChar()) {
                    case '\\':
                        pubByte((byte) '\\', size++);
                        pubByte(scanChar(), size++);
                        continue;
                    case '\'':
                        if (sql[curIndex + 1] == '\'') {
                            pubByte((byte) '\\', size++);
                            pubByte(scanChar(), size++);
                            continue;
                        }
                        pubByte((byte) '\'', size++);
                        scanChar();
                        break loop;
                    default:
                        if (eof()) {
                            throw err("unclosed string");
                        }
                        pubByte(ch, size++);
                        continue;
                }
            }
        }

        sizeCache = size;
        stringValue = Arrays.copyOfRange(sbuf, 0, size);
        token = MySQLToken.LITERAL_CHARS;
    }

    protected final void pubByte(byte ch, int index) {
        if (index >= sbuf.length) {
            byte[] newsbuf = new byte[sbuf.length * 2];
            System.arraycopy(sbuf, 0, newsbuf, 0, sbuf.length);
            sbuf = newsbuf;
        }
        sbuf[index] = ch;
    }

    protected void scanHexaDecimal(boolean quoteMode) throws SQLSyntaxErrorException {
        offsetCache = curIndex;
        for (; CharTypes.isHex(ch); scanChar());

        sizeCache = curIndex - offsetCache;
        if (quoteMode) {
            if (ch != '\'') {
                throw err("invalid char for hex: " + ch);
            }
            scanChar();
        } else if (CharTypes.isIdentifierChar(ch)) {
            scanIdentifierFromNumber(offsetCache - 2, sizeCache + 2);
            return;
        }

        token = MySQLToken.LITERAL_HEX;
    }

    protected void scanBitField(boolean quoteMode) throws SQLSyntaxErrorException {
        offsetCache = curIndex;
        for (; ch == '0' || ch == '1'; scanChar()) ;
        sizeCache = curIndex - offsetCache;
        if (quoteMode) {
            if (ch != '\'') {
                throw err("invalid char for bit: " + ch);
            }
            scanChar();
        } else if (CharTypes.isIdentifierChar(ch)) {
            scanIdentifierFromNumber(offsetCache - 2, sizeCache + 2);
            return;
        }

        token = MySQLToken.LITERAL_BIT;
        stringValue = Arrays.copyOfRange(sql, offsetCache, offsetCache + sizeCache);
    }

    protected void scanNumber() throws SQLSyntaxErrorException {
        offsetCache = curIndex;
        sizeCache = 1;
        final boolean fstDot = ch == '.';
        boolean dot = fstDot;
        boolean sign = false;
        int state = fstDot ? 1 : 0;

        for (; scanChar() != MySQLLexer.EOI; ++sizeCache) {
            switch (state) {
                case 0:
                    if (CharTypes.isDigit(ch)) {
                    } else if (ch == '.') {
                        dot = true;
                        state = 1;
                    } else if (ch == 'e' || ch == 'E') {
                        state = 3;
                    } else if (CharTypes.isIdentifierChar(ch)) {
                        scanIdentifierFromNumber(offsetCache, sizeCache);
                        return;
                    } else {
                        token = MySQLToken.LITERAL_NUM_PURE_DIGIT;
                        return;
                    }
                    break;
                case 1:
                    if (CharTypes.isDigit(ch)) {
                        state = 2;
                    } else if (ch == 'e' || ch == 'E') {
                        state = 3;
                    } else if (CharTypes.isIdentifierChar(ch) && fstDot) {
                        sizeCache = 1;
                        ch = sql[curIndex = offsetCache + 1];
                        token = MySQLToken.PUNC_DOT;
                        return;
                    } else {
                        token = MySQLToken.LITERAL_NUM_MIX_DIGIT;
                        return;
                    }
                    break;
                case 2:
                    if (CharTypes.isDigit(ch)) {
                    } else if (ch == 'e' || ch == 'E') {
                        state = 3;
                    } else if (CharTypes.isIdentifierChar(ch) && fstDot) {
                        sizeCache = 1;
                        ch = sql[curIndex = offsetCache + 1];
                        token = MySQLToken.PUNC_DOT;
                        return;
                    } else {
                        token = MySQLToken.LITERAL_NUM_MIX_DIGIT;
                        return;
                    }
                    break;
                case 3:
                    if (CharTypes.isDigit(ch)) {
                        state = 5;
                    } else if (ch == '+' || ch == '-') {
                        sign = true;
                        state = 4;
                    } else if (fstDot) {
                        sizeCache = 1;
                        ch = sql[curIndex = offsetCache + 1];
                        token = MySQLToken.PUNC_DOT;
                        return;
                    } else if (!dot) {
                        if (CharTypes.isIdentifierChar(ch)) {
                            scanIdentifierFromNumber(offsetCache, sizeCache);
                        } else {
                            updateStringValue(sql, offsetCache, sizeCache);
                            MySQLToken tok = keywods.getKeyword(stringValueUppercase);
                            token = tok == null ? MySQLToken.IDENTIFIER : tok;
                        }
                        return;
                    } else {
                        throw err("invalid char after '.' and 'e' for as part of number: " + ch);
                    }
                    break;
                case 4:
                    if (CharTypes.isDigit(ch)) {
                        state = 5;
                        break;
                    } else if (fstDot) {
                        sizeCache = 1;
                        ch = sql[curIndex = offsetCache + 1];
                        token = MySQLToken.PUNC_DOT;
                    } else if (!dot) {
                        ch = sql[--curIndex];
                        --sizeCache;
                        updateStringValue(sql, offsetCache, sizeCache);
                        MySQLToken tok = keywods.getKeyword(stringValueUppercase);
                        token = tok == null ? MySQLToken.IDENTIFIER : tok;
                    } else {
                        throw err("expect digit char after SIGN for 'e': " + ch);
                    }
                    return;
                case 5:
                    if (CharTypes.isDigit(ch)) {
                        break;
                    } else if (CharTypes.isIdentifierChar(ch)) {
                        if (fstDot) {
                            sizeCache = 1;
                            ch = sql[curIndex = offsetCache + 1];
                            token = MySQLToken.PUNC_DOT;
                        } else if (!dot) {
                            if (sign) {
                                ch = sql[curIndex = offsetCache];
                                scanIdentifierFromNumber(curIndex, 0);
                            } else {
                                scanIdentifierFromNumber(offsetCache, sizeCache);
                            }
                        } else {
                            token = MySQLToken.LITERAL_NUM_MIX_DIGIT;
                        }
                    } else {
                        token = MySQLToken.LITERAL_NUM_MIX_DIGIT;
                    }
                    return;
            }
        }
        switch (state) {
            case 0:
                token = MySQLToken.LITERAL_NUM_PURE_DIGIT;
                return;
            case 1:
                if (fstDot) {
                    token = MySQLToken.PUNC_DOT;
                    return;
                }
            case 2:
            case 5:
                token = MySQLToken.LITERAL_NUM_MIX_DIGIT;
                return;
            case 3:
                if (fstDot) {
                    sizeCache = 1;
                    ch = sql[curIndex = offsetCache + 1];
                    token = MySQLToken.PUNC_DOT;
                } else if (!dot) {
                    updateStringValue(sql, offsetCache, sizeCache);
                    MySQLToken tok = keywods.getKeyword(stringValueUppercase);
                    token = tok == null ? MySQLToken.IDENTIFIER : tok;
                } else {
                    throw err("expect digit char after SIGN for 'e': " + ch);
                }
                return;
            case 4:
                if (fstDot) {
                    sizeCache = 1;
                    ch = sql[curIndex = offsetCache + 1];
                    token = MySQLToken.PUNC_DOT;
                } else if (!dot) {
                    ch = sql[--curIndex];
                    --sizeCache;
                    updateStringValue(sql, offsetCache, sizeCache);
                    MySQLToken tok = keywods.getKeyword(stringValueUppercase);
                    token = tok == null ? MySQLToken.IDENTIFIER : tok;
                } else {
                    throw err("expect digit char after SIGN for 'e': " + ch);
                }
                return;
        }
    }

    /**
     * 从数字扫描标识符（ID）
     * NOTE: {@link MySQLToken#IDENTIFIER id} dosn't include <code>'.'</code> for sake of
     * performance issue (based on <i>shaojin.wensj</i>'s design). However, it is not convenient for
     * MySQL compatibility. e.g. <code>".123f"</code> will be regarded as <code>".123"</code> and
     * <code>"f"</code> in MySQL, but in this {@link MySQLLexer}, it will be <code>"."</code> and
     * <code>"123f"</code> because <code>".123f"</code> may be part of <code>"db1.123f"</code> and
     * <code>"123f"</code> is the table name.
     *
     * @param initSize how many char has already been consumed
     */
    private void scanIdentifierFromNumber(int initOffset, int initSize)
            throws SQLSyntaxErrorException {
        offsetCache = initOffset;
        sizeCache = initSize;
        for (; CharTypes.isIdentifierChar(ch); ++sizeCache) {
            scanChar();
        }
        updateStringValue(sql, offsetCache, sizeCache);
        MySQLToken tok = keywods.getKeyword(stringValueUppercase);
        token = tok == null ? MySQLToken.IDENTIFIER : tok;
    }

    protected void scanIdentifier() throws SQLSyntaxErrorException {
        if (ch == '$') {
            if (scanChar() == '{') {
                scanPlaceHolder();
            } else {
                scanIdentifierFromNumber(curIndex - 1, 1);
            }
        } else {
            scanIdentifierFromNumber(curIndex, 0);
        }
    }

    protected void scanPlaceHolder() throws SQLSyntaxErrorException {
        offsetCache = curIndex + 1;
        sizeCache = 0;
        for (scanChar(); ch != '}' && !eof(); ++sizeCache) {
            scanChar();
        }
        if (ch == '}')
            scanChar();
        updateStringValue(sql, offsetCache, sizeCache);
        token = MySQLToken.PLACE_HOLDER;
    }

    protected void scanIdentifierWithAccent() throws SQLSyntaxErrorException {
        offsetCache = curIndex;
        for (; scanChar() != MySQLLexer.EOI;) {
            if (ch == '`' && scanChar() != '`') {
                break;
            }
        }
        updateStringValue(sql, offsetCache, sizeCache = curIndex - offsetCache);
        token = MySQLToken.IDENTIFIER;
    }

    protected void skipSeparator() {
        while (!eof()) {
            for (; CharTypes.isWhitespace(ch); scanChar());

            switch (ch) {
                case '#': // MySQL specified
                    for (; scanChar() != '\n';) {
                        if (eof()) {
                            return;
                        }
                    }
                    scanChar();
                    continue;
                case '/':
                    if (hasChars(2) && '*' == sql[curIndex + 1]) {
                        boolean commentSkip;
                        if ('!' == sql[curIndex + 2]) {
                            scanChar(3);
                            inCStyleComment = true;
                            inCStyleCommentIgnore = false;
                            commentSkip = false;
                            // MySQL use 5 digits to indicate version. 50508 means
                            // MySQL 5.5.8
                            if (hasChars(5) && CharTypes.isDigit(ch)
                                    && CharTypes.isDigit(sql[curIndex + 1])
                                    && CharTypes.isDigit(sql[curIndex + 2])
                                    && CharTypes.isDigit(sql[curIndex + 3])
                                    && CharTypes.isDigit(sql[curIndex + 4])) {
                                int version = ch - '0';
                                version *= 10;
                                version += sql[curIndex + 1] - '0';
                                version *= 10;
                                version += sql[curIndex + 2] - '0';
                                version *= 10;
                                version += sql[curIndex + 3] - '0';
                                version *= 10;
                                version += sql[curIndex + 4] - '0';
                                scanChar(5);
                                this.version = version;
                                if (version > C_STYLE_COMMENT_VERSION) {
                                    inCStyleCommentIgnore = true;
                                }
                            }
                            skipSeparator();
                        } else {
                            scanChar(2);
                            commentSkip = true;
                        }

                        if (commentSkip) {
                            for (int state = 0; !eof(); scanChar()) {
                                if (state == 0) {
                                    if ('*' == ch) {
                                        state = 1;
                                    }
                                } else {
                                    if ('/' == ch) {
                                        scanChar();
                                        break;
                                    } else if ('*' != ch) {
                                        state = 0;
                                    }
                                }
                            }
                            continue;
                        }
                    }
                    return;
                case '-':
                    if (hasChars(3) && '-' == sql[curIndex + 1]
                            && CharTypes.isWhitespace(sql[curIndex + 2])) {
                        scanChar(3);
                        for (; !eof(); scanChar()) {
                            if ('\n' == ch) {
                                scanChar();
                                break;
                            }
                        }
                        continue;
                    }
                default:
                    return;
            }
        }
    }

    protected SQLSyntaxErrorException err(String msg) throws SQLSyntaxErrorException {
        String errMsg = msg + ". " + toString();
        throw new SQLSyntaxErrorException(errMsg);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append('@').append(hashCode()).append('{');
        String sqlLeft = new String(sql, curIndex, sql.length - curIndex);
        sb.append("curIndex=").append(curIndex).append(", ch=").append(ch).append(", token=")
                .append(token).append(", sqlLeft=").append(sqlLeft).append(", sql=")
                .append(new String(sql));
        sb.append('}');
        return sb.toString();
    }

    public Number integerValue() {

        if (sizeCache < 10 || sizeCache == 10 && (sql[offsetCache] < '2'
                || sql[offsetCache] == '2' && sql[offsetCache + 1] == '0')) {
            int rst = 0;
            int end = offsetCache + sizeCache;
            for (int i = offsetCache; i < end; ++i) {
                rst = (rst << 3) + (rst << 1);
                rst += sql[i] - '0';
            }
            return rst;
        } else if (sizeCache < 19 || sizeCache == 19 && sql[offsetCache] < '9') {
            long rst = 0;
            int end = offsetCache + sizeCache;
            for (int i = offsetCache; i < end; ++i) {
                rst = (rst << 3) + (rst << 1);
                rst += sql[i] - '0';
            }
            return rst;
        } else {
            return new BigInteger(new String(sql, offsetCache, sizeCache), 10);
        }
    }

    public BigDecimal decimalValue() {
        return new BigDecimal(new String(sql, offsetCache, sizeCache));
    }

    public void appendStringContent(List<Byte> bytes) {
        for (int i = 1, size = sizeCache - 1; i < size; i++) {
            bytes.add(sbuf[i]);
        }
    }

    public byte[] getStringContent() {
        return Arrays.copyOfRange(sbuf, 1, sizeCache - 1);
    }

    public final String stringValue() {
        return new String(stringValue);
    }

    public final byte[] byteValue() {
        return stringValue;
    }

    public final String stringValueUppercase() {
        return new String(stringValueUppercase);
    }

    public int getLastIndex() {
        return lastIndex;
    }

}

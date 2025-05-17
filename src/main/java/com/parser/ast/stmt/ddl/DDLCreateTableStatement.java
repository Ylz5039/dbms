package com.parser.ast.stmt.ddl;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.ddl.ColumnDefinition;
import com.parser.ast.fragment.ddl.TableOptions;
import com.parser.ast.fragment.ddl.index.IndexColumnName;
import com.parser.ast.fragment.ddl.index.IndexDefinition;
import com.parser.ast.stmt.dml.DMLQueryStatement;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DDLCreateTableStatement implements DDLStatement {

    public enum SelectOption {
        IGNORED, REPLACE
    }

    private final boolean temporary;

    private final boolean ifNotExists;

    private final Identifier table;

    private final List<Pair<Identifier, ColumnDefinition>> colDefs;

    private IndexDefinition primaryKey;

    private final List<Expression> checks;

    private TableOptions tableOptions;

    private Pair<SelectOption, DMLQueryStatement> select;

    public DDLCreateTableStatement(boolean temporary, boolean ifNotExists, Identifier table) {
        this.table = table;
        this.temporary = temporary;
        this.ifNotExists = ifNotExists;
        this.colDefs = new ArrayList<Pair<Identifier, ColumnDefinition>>(4);
        this.checks = new ArrayList<Expression>(1);
    }

    public DDLCreateTableStatement setTableOptions(TableOptions tableOptions) {
        this.tableOptions = tableOptions;
        return this;
    }

    public DDLCreateTableStatement addColumnDefinition(Identifier colName, ColumnDefinition def) {
        colDefs.add(new Pair<Identifier, ColumnDefinition>(colName, def));
        return this;
    }

    public DDLCreateTableStatement setPrimaryKey(IndexDefinition def) {
        primaryKey = def;
        return this;
    }

    public DDLCreateTableStatement addCheck(Expression check) {
        checks.add(check);
        return this;
    }

    public Pair<SelectOption, DMLQueryStatement> getSelect() {
        return select;
    }

    public void setSelect(SelectOption option, DMLQueryStatement select) {
        this.select =
                new Pair<DDLCreateTableStatement.SelectOption, DMLQueryStatement>(option, select);
    }

    public boolean isTemporary() {
        return temporary;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public Identifier getTable() {
        return table;
    }

    public List<Pair<Identifier, ColumnDefinition>> getColDefs() {
        return colDefs;
    }

    public IndexDefinition getPrimaryKey() {
        return primaryKey;
    }

    public List<Expression> getChecks() {
        return checks;
    }

    public static class ForeignKeyDefinition implements ASTNode {
        public enum REFERENCE_OPTION {
            RESTRICT, CASCADE, SET_NULL, NO_ACTION
        }

        private final Identifier indexName;
        private final List<IndexColumnName> columns;
        private final Identifier referenceTable;
        private final List<IndexColumnName> referenceColumns;
        private REFERENCE_OPTION onDelete;
        private REFERENCE_OPTION onUpdate;
        private Identifier symbol;

        public ForeignKeyDefinition(Identifier indexName, List<IndexColumnName> columns,
                                    Identifier referenceTable, List<IndexColumnName> referenceColumns) {
            this.indexName = indexName;
            this.columns = columns;
            this.referenceTable = referenceTable;
            this.referenceColumns = referenceColumns;
        }

        public REFERENCE_OPTION getOnDelete() {
            return onDelete;
        }

        public void setOnDelete(REFERENCE_OPTION onDelete) {
            this.onDelete = onDelete;
        }

        public REFERENCE_OPTION getOnUpdate() {
            return onUpdate;
        }

        public void setOnUpdate(REFERENCE_OPTION onUpdate) {
            this.onUpdate = onUpdate;
        }

        public Identifier getSymbol() {
            return symbol;
        }

        public void setSymbol(Identifier symbol) {
            this.symbol = symbol;
        }

        public Identifier getIndexName() {
            return indexName;
        }

        public List<IndexColumnName> getColumns() {
            return columns;
        }

        public Identifier getReferenceTable() {
            return referenceTable;
        }

        public List<IndexColumnName> getReferenceColumns() {
            return referenceColumns;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ForeignKeyDefinition {\n")
                    .append("    indexName=").append(indexName).append("\n")
                    .append("    columns=").append(columns).append("\n")
                    .append("    referenceTable=").append(referenceTable).append("\n")
                    .append("    referenceColumns=").append(referenceColumns).append("\n")
                    .append("    onDelete=").append(onDelete).append("\n")
                    .append("    onUpdate=").append(onUpdate).append("\n")
                    .append("    symbol=").append(symbol).append("\n")
                    .append("}");
            return sb.toString();
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateTableStatement {\n")
                .append("    temporary=").append(temporary).append("\n")
                .append("    ifNotExists=").append(ifNotExists).append("\n")
                .append("    table=").append(table).append("\n")
                .append("    colDefs=\n");

        if (colDefs != null) {
            for (Object colDef : colDefs) {
                sb.append("        ").append(colDef).append("\n");
            }
        } else {
            sb.append("        null\n");
        }

        sb.append("    primaryKey=").append(primaryKey).append("\n")
                .append("    checks=").append(checks).append("\n")
                .append("    tableOptions=").append(tableOptions).append("\n")
                .append("    select=").append(select).append("\n")
                .append("}");

        return sb.toString();
    }


}

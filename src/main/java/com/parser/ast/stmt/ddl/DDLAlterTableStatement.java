package com.parser.ast.stmt.ddl;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.OrderBy;
import com.parser.ast.fragment.ddl.ColumnDefinition;
import com.parser.ast.fragment.ddl.TableOptions;
import com.parser.ast.fragment.ddl.index.IndexColumnName;
import com.parser.ast.fragment.ddl.index.IndexDefinition;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DDLAlterTableStatement implements DDLStatement {
    public static interface AlterSpecification extends ASTNode {
    }

    public static class AddColumn implements AlterSpecification {
        private final Identifier columnName;
        private final ColumnDefinition columnDefine;
        private final boolean first;
        private final Identifier afterColumn;

        public AddColumn(Identifier columnName, ColumnDefinition columnDefine,
                         Identifier afterColumn) {
            this.columnName = columnName;
            this.columnDefine = columnDefine;
            this.afterColumn = afterColumn;
            this.first = afterColumn == null;
        }

        public AddColumn(Identifier columnName, ColumnDefinition columnDefine) {
            this.columnName = columnName;
            this.columnDefine = columnDefine;
            this.afterColumn = null;
            this.first = false;
        }

        public Identifier getColumnName() {
            return columnName;
        }

        public ColumnDefinition getColumnDefine() {
            return columnDefine;
        }

        public boolean isFirst() {
            return first;
        }

        public Identifier getAfterColumn() {
            return afterColumn;
        }

        public String toString() {
            return "AddColumn{" +
                    "columnName=" + columnName +
                    ", columnDefine=" + columnDefine +
                    ", first=" + first +
                    ", afterColumn=" + afterColumn +
                    '}';
        }
    }

    public static class AddColumns implements AlterSpecification {
        private final List<Pair<Identifier, ColumnDefinition>> columns;

        public AddColumns() {
            this.columns = new ArrayList<Pair<Identifier, ColumnDefinition>>(2);
        }

        public AddColumns addColumn(Identifier name, ColumnDefinition colDef) {
            this.columns.add(new Pair<Identifier, ColumnDefinition>(name, colDef));
            return this;
        }

        public List<Pair<Identifier, ColumnDefinition>> getColumns() {
            return columns;
        }

        public String toString() {
            return "AddColumns{" +
                    "columns=" + columns +
                    '}';
        }
    }

    public static class AddIndex implements AlterSpecification {
        private final Identifier indexName;
        private final IndexDefinition indexDef;

        public AddIndex(Identifier indexName, IndexDefinition indexDef) {
            this.indexName = indexName;
            this.indexDef = indexDef;
        }

        public Identifier getIndexName() {
            return indexName;
        }

        public IndexDefinition getIndexDef() {
            return indexDef;
        }

        public String toString() {
            return "AddIndex{" +
                    "indexName=" + indexName +
                    ", indexDef=" + indexDef +
                    '}';
        }
    }

    public static class AddPrimaryKey implements AlterSpecification {
        private final Identifier symbol;
        private final IndexDefinition indexDef;

        public AddPrimaryKey(Identifier symbol, IndexDefinition indexDef) {
            this.symbol = symbol;
            this.indexDef = indexDef;
        }

        public AddPrimaryKey(Pair<Identifier, IndexDefinition> pair) {
            symbol = pair.getKey();
            indexDef = pair.getValue();
        }

        public Identifier getIndexName() {
            return symbol;
        }

        public IndexDefinition getIndexDef() {
            return indexDef;
        }

        public String toString() {
            return "AddPrimaryKey{" +
                    "symbol=" + symbol +
                    ", indexDef=" + indexDef +
                    '}';
        }
    }

    public static class AddForeignKey implements AlterSpecification {

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

        public AddForeignKey(Identifier indexName, List<IndexColumnName> columns,
                             Identifier referenceTable, List<IndexColumnName> referenceColumns) {
            this.indexName = indexName;
            this.columns = columns;
            this.referenceTable = referenceTable;
            this.referenceColumns = referenceColumns;
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

        public String toString() {
            return "AddForeignKey{" +
                    "indexName=" + indexName +
                    ", columns=" + columns +
                    ", referenceTable=" + referenceTable +
                    ", referenceColumns=" + referenceColumns +
                    ", onDelete=" + onDelete +
                    ", onUpdate=" + onUpdate +
                    ", symbol=" + symbol +
                    '}';
        }
    }

    public static class AddUniqueKey implements AlterSpecification {
        private final Identifier indexName;
        private final IndexDefinition indexDef;

        public AddUniqueKey(Identifier indexName, IndexDefinition indexDef) {
            this.indexDef = indexDef;
            this.indexName = indexName;
        }

        public Identifier getIndexName() {
            return indexName;
        }

        public IndexDefinition getIndexDef() {
            return indexDef;
        }

        public String toString() {
            return "AddUniqueKey{" +
                    "indexName=" + indexName +
                    ", indexDef=" + indexDef +
                    '}';
        }
    }

    public static class AddFullTextIndex implements AlterSpecification {
        private final Identifier indexName;
        private final IndexDefinition indexDef;

        public AddFullTextIndex(Identifier indexName, IndexDefinition indexDef) {
            this.indexDef = indexDef;
            this.indexName = indexName;
        }

        public Identifier getIndexName() {
            return indexName;
        }

        public IndexDefinition getIndexDef() {
            return indexDef;
        }

        public String toString() {
            return "AddFullTextIndex{" +
                    "indexName=" + indexName +
                    ", indexDef=" + indexDef +
                    '}';
        }
    }

    public static class AddSpatialIndex implements AlterSpecification {
        private final Identifier indexName;
        private final IndexDefinition indexDef;

        public AddSpatialIndex(Identifier indexName, IndexDefinition indexDef) {
            this.indexDef = indexDef;
            this.indexName = indexName;
        }

        public Identifier getIndexName() {
            return indexName;
        }

        public IndexDefinition getIndexDef() {
            return indexDef;
        }

        public String toString() {
            return "AddSpatialIndex{" +
                    "indexName=" + indexName +
                    ", indexDef=" + indexDef +
                    '}';
        }
    }

    public static class AlterColumnDefaultVal implements AlterSpecification {
        private final Identifier columnName;
        private final Expression defaultValue;
        private final boolean dropDefault;

        public AlterColumnDefaultVal(Identifier columnName, Expression defaultValue) {
            this.columnName = columnName;
            this.defaultValue = defaultValue;
            this.dropDefault = false;
        }

        public AlterColumnDefaultVal(Identifier columnName) {
            this.columnName = columnName;
            this.defaultValue = null;
            this.dropDefault = true;
        }

        public Identifier getColumnName() {
            return columnName;
        }

        public Expression getDefaultValue() {
            return defaultValue;
        }

        public boolean isDropDefault() {
            return dropDefault;
        }
    }

    public static class ChangeColumn implements AlterSpecification {
        private final Identifier oldName;
        private final Identifier newName;
        private final ColumnDefinition colDef;
        private final boolean first;
        private final Identifier afterColumn;

        public ChangeColumn(Identifier oldName, Identifier newName, ColumnDefinition colDef,
                            Identifier afterColumn) {
            this.oldName = oldName;
            this.newName = newName;
            this.colDef = colDef;
            this.first = afterColumn == null;
            this.afterColumn = afterColumn;
        }

        /**
         * without column position specification
         */
        public ChangeColumn(Identifier oldName, Identifier newName, ColumnDefinition colDef) {
            this.oldName = oldName;
            this.newName = newName;
            this.colDef = colDef;
            this.first = false;
            this.afterColumn = null;
        }

        public Identifier getOldName() {
            return oldName;
        }

        public Identifier getNewName() {
            return newName;
        }

        public ColumnDefinition getColDef() {
            return colDef;
        }

        public boolean isFirst() {
            return first;
        }

        public Identifier getAfterColumn() {
            return afterColumn;
        }
    }

    // | MODIFY [COLUMN] col_name column_definition [FIRST | AFTER col_name]
    public static class ModifyColumn implements AlterSpecification {
        private final Identifier colName;
        private final ColumnDefinition colDef;
        private final boolean first;
        private final Identifier afterColumn;

        public ModifyColumn(Identifier colName, ColumnDefinition colDef, Identifier afterColumn) {
            this.colName = colName;
            this.colDef = colDef;
            this.first = afterColumn == null;
            this.afterColumn = afterColumn;
        }

        public ModifyColumn(Identifier colName, ColumnDefinition colDef) {
            this.colName = colName;
            this.colDef = colDef;
            this.first = false;
            this.afterColumn = null;
        }

        public Identifier getColName() {
            return colName;
        }

        public ColumnDefinition getColDef() {
            return colDef;
        }

        public boolean isFirst() {
            return first;
        }

        public Identifier getAfterColumn() {
            return afterColumn;
        }
    }

    // | DROP [COLUMN] col_name
    public static class DropColumn implements AlterSpecification {
        private final Identifier colName;

        public DropColumn(Identifier colName) {
            this.colName = colName;
        }

        public Identifier getColName() {
            return colName;
        }
    }

    // | DROP PRIMARY KEY
    public static class DropPrimaryKey implements AlterSpecification {
    }

    // | DROP FOREIGN KEY
    public static class DropForeignKey implements AlterSpecification {
    }

    // | DROP {INDEX|KEY} index_name
    public static class DropIndex implements AlterSpecification {
        private final Identifier indexName;

        public DropIndex(Identifier indexName) {
            this.indexName = indexName;
        }

        public Identifier getIndexName() {
            return indexName;
        }
    }

    // @ZC.CUI | ORDER BY col_name [, col_name] ...
    public static class OrderByColumns implements AlterSpecification {
        private final List<Identifier> columns;

        public OrderByColumns() {
            this.columns = new ArrayList<>();
        }

        public OrderByColumns addColumns(Identifier column) {
            this.columns.add(column);
            return this;
        }

        public List<Identifier> getColumns() {
            return this.columns;
        }
    }

    public static class ExchangePartition implements AlterSpecification {
        private final Identifier partition;
        private final Identifier dstTable;

        public ExchangePartition(Identifier partition, Identifier dstTable) {
            this.partition = partition;
            this.dstTable = dstTable;
        }

        public Identifier getPartition() {
            return partition;
        }

        public Identifier getDstTable() {
            return dstTable;
        }
    }

    public static class AnalyzePartition implements AlterSpecification {
        private boolean isALL = false;
        private final List<Identifier> partitions;

        public AnalyzePartition(boolean isAll, List<Identifier> partitions) {
            this.isALL = isAll;
            this.partitions = partitions;
        }

        public boolean isALL() {
            return isALL;
        }

        public void setALL(boolean isALL) {
            this.isALL = isALL;
        }

        public List<Identifier> getPartitions() {
            return partitions;
        }
    }

    // CHECK PARTITION {partition_names | ALL}
    public static class CheckPartition implements AlterSpecification {
        private boolean isALL = false;
        private final List<Identifier> partitions;

        public CheckPartition(boolean isAll, List<Identifier> partitions) {
            this.isALL = isAll;
            this.partitions = partitions;
        }

        public boolean isALL() {
            return isALL;
        }

        public void setALL(boolean isALL) {
            this.isALL = isALL;
        }

        public List<Identifier> getPartitions() {
            return partitions;
        }
    }

    public static class OptimizePartition implements AlterSpecification {
        private boolean isALL = false;
        private final List<Identifier> partitions;

        public OptimizePartition(boolean isAll, List<Identifier> partitions) {
            this.isALL = isAll;
            this.partitions = partitions;
        }

        public boolean isALL() {
            return isALL;
        }

        public void setALL(boolean isALL) {
            this.isALL = isALL;
        }

        public List<Identifier> getPartitions() {
            return partitions;
        }
    }

    public static class RepairPartition implements AlterSpecification {
        private boolean isALL = false;
        private final List<Identifier> partitions;

        public RepairPartition(boolean isAll, List<Identifier> partitions) {
            this.isALL = isAll;
            this.partitions = partitions;
        }

        public boolean isALL() {
            return isALL;
        }

        public void setALL(boolean isALL) {
            this.isALL = isALL;
        }

        public List<Identifier> getPartitions() {
            return partitions;
        }
    }

    public static class PartitionFunction implements AlterSpecification {
        public PartitionFunction() {}
    }

    public static class ReNameIndex implements AlterSpecification {
        private final Identifier renameFromOld;
        private final Identifier renameToNew;

        public ReNameIndex(Identifier renameFromOld, Identifier renameToNew) {
            this.renameFromOld = renameFromOld;
            this.renameToNew = renameToNew;
        }

        public Identifier getRenameFromOld() {
            return renameFromOld;
        }

        public Identifier getRenameToNew() {
            return renameToNew;
        }

    }

    public static class WithValidation implements AlterSpecification {
        private boolean isWith;

        public WithValidation(boolean with) {
            this.isWith = with;
        }

        public boolean isWith() {
            return this.isWith;
        }
    }

    // | DISABLE KEYS
    // | ENABLE KEYS
    // | RENAME [TO] new_tbl_name
    // | ORDER BY col_name [, col_name] ...
    // | CONVERT TO CHARACTER SET charset_name [COLLATE collation_name]
    // | DISCARD TABLESPACE
    // | IMPORT TABLESPACE
    // | FORCE
    // /// | ADD [CONSTRAINT [symbol]] FOREIGN KEY [index_name]
    // (index_col_name,...) reference_definition
    // /// | DROP FOREIGN KEY fk_symbol
    // /// | ADD PARTITION (partition_definition)
    // /// | DROP PARTITION partition_names
    // /// | TRUNCATE PARTITION {partition_names | ALL }
    // /// | COALESCE PARTITION number
    // /// | REORGANIZE PARTITION partition_names INTO (partition_definitions)
    // /// | ANALYZE PARTITION {partition_names | ALL }
    // /// | CHECK PARTITION {partition_names | ALL }
    // /// | OPTIMIZE PARTITION {partition_names | ALL }
    // /// | REBUILD PARTITION {partition_names | ALL }
    // /// | REPAIR PARTITION {partition_names | ALL }
    // /// | REMOVE PARTITIONING

    // ADD, ALTER, DROP, and CHANGE can be multiple

    public enum Algorithm {
        DEFAULT, INPLACE, COPY
    }

    public enum Lock {
        DEFAULT, NONE, SHARED, EXCLUSIVE
    }

    private final boolean ignore;
    private final Identifier table;
    private TableOptions tableOptions;
    private final List<AlterSpecification> alters;
    private boolean disableKeys;
    private boolean enableKeys;
    private boolean discardTableSpace;
    private boolean importTableSpace;
    private boolean force;
    private Identifier renameTo;
    private Algorithm algorithm;
    private Lock lock;
    private Pair<Identifier, Identifier> convertCharset;

    private OrderBy orderBy;

    public DDLAlterTableStatement(boolean ignore, Identifier table) {
        this.ignore = ignore;
        this.table = table;
        this.alters = new ArrayList<DDLAlterTableStatement.AlterSpecification>(1);
    }

    public DDLAlterTableStatement addAlterSpecification(AlterSpecification alter) {
        alters.add(alter);
        return this;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public Identifier getTable() {
        return table;
    }

    public TableOptions getTableOptions() {
        return tableOptions;
    }

    public void setTableOptions(TableOptions tableOptions) {
        this.tableOptions = tableOptions;
    }

    public List<AlterSpecification> getAlters() {
        return alters;
    }

    public boolean isDisableKeys() {
        return disableKeys;
    }

    public void setDisableKeys(boolean disableKeys) {
        this.disableKeys = disableKeys;
    }

    public boolean isEnableKeys() {
        return enableKeys;
    }

    public void setEnableKeys(boolean enableKeys) {
        this.enableKeys = enableKeys;
    }

    public boolean isDiscardTableSpace() {
        return discardTableSpace;
    }

    public void setDiscardTableSpace(boolean discardTableSpace) {
        this.discardTableSpace = discardTableSpace;
    }

    public boolean isImportTableSpace() {
        return importTableSpace;
    }

    public void setImportTableSpace(boolean importTableSpace) {
        this.importTableSpace = importTableSpace;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public Identifier getRenameTo() {
        return renameTo;
    }

    public void setRenameTo(Identifier renameTo) {
        this.renameTo = renameTo;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Pair<Identifier, Identifier> getConvertCharset() {
        return convertCharset;
    }

    public void setConvertCharset(Pair<Identifier, Identifier> convertCharset) {
        this.convertCharset = convertCharset;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLAlterTableStatement {\n")
                .append("    ignore=").append(ignore).append("\n")
                .append("    table=").append(table).append("\n")
                .append("    tableOptions=").append(tableOptions).append("\n")
                .append("    alters=").append(alters).append("\n")
                .append("    disableKeys=").append(disableKeys).append("\n")
                .append("    enableKeys=").append(enableKeys).append("\n")
                .append("    discardTableSpace=").append(discardTableSpace).append("\n")
                .append("    importTableSpace=").append(importTableSpace).append("\n")
                .append("    force=").append(force).append("\n")
                .append("    renameTo=").append(renameTo).append("\n")
                .append("    algorithm=").append(algorithm).append("\n")
                .append("    lock=").append(lock).append("\n")
                .append("    convertCharset=").append(convertCharset).append("\n")
                .append("    orderBy=").append(orderBy).append("\n")
                .append("}");
        return sb.toString();
    }

}

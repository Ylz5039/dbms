# dbms
Lightweight DBMS (Java)
A lightweight database management system implemented in Java, featuring a custom key-value container, finite state machine for command parsing, and multi-threaded socket communication. The client offers a user-friendly GUI for SQL-like command input and result display. Supports data persistence and concurrent client access with thread-safe operations.

  /\_/\  
 ( o.o )   < Lightweight DBMS
  > ^ <     ~ 线程安全 · SQL-like · 持久化

com
├── parser # 语法分析模块
│ ├── ast # 抽象语法树（AST）
│ │ ├── expression # 表达式节点
│ │ │ ├── arithmetic # 算术表达式
│ │ │ ├── bit # 位运算表达式
│ │ │ ├── comparison # 比较表达式
│ │ │ ├── logical # 逻辑表达式
│ │ │ ├── misc # 其他表达式
│ │ │ ├── primary # 基本表达式
│ │ │ │ ├── function # 函数调用
│ │ │ │ └── literal # 字面量
│ │ │ ├── string # 字符串表达式
│ │ │ └── type # 类型表达式
│ │ ├── fragment # 语法片段
│ │ │ ├── ddl # 数据定义语言片段
│ │ │ │ ├── datatype # 数据类型定义
│ │ │ │ └── index # 索引定义
│ │ │ └── tableref # 表引用
│ │ └── stmt # 各类语句节点
│ │ ├── compound # 复合语句
│ │ │ ├── condition # 条件语句
│ │ │ ├── cursors # 游标控制
│ │ │ └── flowcontrol # 控制流
│ │ ├── dal # 数据访问语言
│ │ ├── ddl # 数据定义语言
│ │ ├── dml # 数据操作语言
│ │ ├── extension # 扩展语句
│ │ └── mts # 多线程控制
│ ├── recognizer # SQL 语法识别器
│ │ └── mysql # MySQL 语法支持
│ │ ├── lexer # 词法分析器
│ │ └── syntax # 语法分析器
│ └── util # 工具类集合
└── structuer # 数据结构模块

com
├── parser                // 处理语法分析的主要包
│   ├── ast               // 抽象语法树（AST）相关
│   │   ├── expression    // 表达式相关
│   │   │   ├── arithmetic // 算术表达式
│   │   │   ├── bit       // 位运算表达式
│   │   │   ├── comparison // 比较表达式
│   │   │   ├── logical    // 逻辑表达式
│   │   │   ├── misc      // 其他类型表达式
│   │   │   ├── primary    // 基本表达式
│   │   │   │   ├── function // 函数表达式
│   │   │   │   └── literal  // 字面量表达式
│   │   │   ├── string     // 字符串表达式
│   │   │   └── type       // 类型表达式
│   │   ├── fragment       // 语法片段
│   │   │   ├── ddl        // 数据定义语言
│   │   │   │   ├── datatype // 数据类型定义
│   │   │   │   └── index   // 索引定义
│   │   │   └── tableref    // 表引用
│   │   └── stmt           // 语句相关
│   │       ├── compound    // 复合语句
│   │       │   ├── condition // 条件语句
│   │       │   ├── cursors   // 游标语句
│   │       │   └── flowcontrol // 控制流语句
│   │       ├── dal          // 数据访问语言
│   │       ├── ddl          // 数据定义语言
│   │       ├── dml          // 数据操作语言
│   │       ├── extension     // 扩展语句
│   │       └── mts          // 多线程语句
│   ├── recognizer          // 语法识别器
│   │   ├── mysql           // MySQL特定的识别器
│   │   │   ├── lexer       // 词法分析器
│   │   │   └── syntax      // 语法分析器
│   └── util                // 工具类
└── structuer               // 数据结构相关

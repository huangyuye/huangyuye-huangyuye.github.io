#### 开发工具

vscode & webstorm均可，这里使用了vscode

#### 配置

多人合作 or 个人项目，代码规范都是很重要的。遵循代码规范能够很大程度避免基本语法错误，也保证了代码的可读性。所以以下会说明代码规范神器—eslint插件。首先我们先来说说什么是eslint

- eslint

  - ECMASCRIPT/JAVASCRIPT 代码中识别和报告"模式匹配"的工具，目标是保证代码的一致性和避免错误。
  - ECMASCRIPT：在万维网上往往被称为JavaScript或JScript，可以将它理解为是JavaScript的一个标准,但实际上后两者是ECMA-262标准的实现和扩展

- eslint插件安装：

  - settings

    - 文件 > 首选项 > 设置
    - 配置如下

    ```
    
    ```

  - .eslintrc.js：

    - 根据该文件配置的eslint规则来检查和做一些简单的fix。（可以把适合自己团队的代码规范统一制定好并上传到至npm）

- eslint-plugin-vue 新版本默认支持vue eslint语法检查

- 配置文件说明：

  - jsconfig.json
    - 目录中存在jsconfig.json文件表示该目录是JavaScript项目的根目录;jsconfig.json文件指定根文件和JavaScript语言服务提供的功能选项;文件本身可以选择列出属于项目的文件，要从项目中排除的文件，以及编译器选项
      - “exclude”属性：告诉js语言服务哪些文件是什么文件，而不是源代码的一部分。（您将要排除由构建过程生成的文件（例如，dist目录））
      - 使用“包含”属性：可以使用include属性（glob模式）显式设置项目中的文件。如果不存在include属性，则默认为包含目录和子目录中的所有文件。
      - compilerOptions：配置JavaScript语言支持（baseUrl&paths(可以使用webpack别名)）
    - 如果没有使用该文件，所有js文件之间没有共同的项目上下文
    - 如果使用了该文件，表示该目录是JavaScript项目的根目录
    - 参考链接：https://www.jianshu.com/p/b0ec870ddfdf
  - .eslintrc.js
    - eslint代码检查配置
      - npm install eslint --save
      - eslint --init # 初始化配置文件
    - vscode安装 eslint插件，并配置用户变量以配置eslint校验的开关等配置，符合检查的文件类型，会根据.eslintrc.js配置的规则进行检查
  - .editconfig（用来帮助开发者定义和维护代码风格（行尾结束符、缩进风格等））
    - 该文件用来定义一些格式化规则（此规则并不会被vscode直接解析）
    - vscode安装插件，插件去读取第一步创建的editorconfig文件中定义的规则，并覆盖user/workspace settings中的对应配置
    - npm安装editorconfig依赖包主要是因为EditorConfig依赖于editorconfig包，不安装的可能会导致EditorConfig无法正常解析我们在第一步定义的editorconfig文件
    - 打开需要格式化的文件并手动格式化代码（shift+alt+f）：让经过EditorConfig扩展覆盖后的user/workspace settings生效
  - babel.config.js
    - 安装相关的包：npm i @babel/core @babel/preset-env babel-loader -D
    - 配置 webpack，编译哪些文件时需要使用babel加载器
    - 配置加载器加载规则，即该文件的作用
    - 如果想以编程的方式创建配置，需要编译 node_modules 中的内容那就使用 babel.config.js,如果只是一个简单的包的静态配置就使用，.babelrc
  - jest.config.js
    - jest是进行Javascript单元测试的工具
    - npm安装 jest
    - 两种配置方式：
      - 在package.json添加配置项"jest" : { 配置项 }
      - jest init命令在根目录创建 jest.config.js文件，并添加配置项module.exports = { 配置项 }
  - postcss.config.js
    - 给postcss用的，优化css
  - package.json
    - 定义了这个项目所需要的各种模块，以及项目的配置信息（比如名称、版本、许可证等元数据）。npm install命令根据这个配置文件，自动下载所需的模块，也就是配置项目所需的运行和开发环境。（**类似于maven的pom文件**）
    - scripts字段：指定了运行脚本命令的npm命令行缩写
    - dependencies字段，devDependencies字段：定了项目运行/开发所依赖的模块
    - package.json文件可以手工编写，也可以使用npm init命令自动生成。
    - 如果一个模块不在package.json文件之中，可以单独安装这个模块，并使用相应的参数，将其写入package.json文件之中。如$ npm install express --save / --save-dev
    - 参考链接：http://javascript.ruanyifeng.com/nodejs/packagejson.html
  - vue.config.js
    - vue项目配置文件
    - 是一个可选的配置文件，如果项目的根目录中存在这个文件，那么它会被 @vue/cli-service 自动加载。你也可以使用 package.json 中的 vue 字段。

- 安装插件，配置用户变量来提供代码格式化功能：https://segmentfault.com/q/1010000014829026?utm_source=tag-newest

  ```
  - Vetur : 语法高亮等功能
  - ESlint : 代码风格检测
  - Prettier formatter for Visual Studio Code: 为了配合 ESlint
  vue 的模板格式化：
  "vetur.format.defaultFormatter.html":"js-beautify-html"
  vue 模板的 eslint 校验
      "eslint.validate": [
          "javascript",
          "javascriptreact",
          "html",
          "vue",
          {
              "language": "html",
              "autoFix": true
          }
      ]
  Prettier 设置去除不必要空格："prettier.semi": false
  Prettier 设置格式化后"变'："prettier.singleQuote": true
  在 vscode 中排除 node_modules 等文件夹：
  "files.exclude": {
      "**/.git": true,
      "**/.svn": true,
      "**/.hg": true,
      "**/CVS": true,
      "**/.DS_Store": true,
      "**/node_modules": true,
      "**/package-lock.json": true,
  },
  以新行结束
  "html.format.endWithNewline": false
  
  完整的 vue 相关用户设置如下
  "files.exclude": {
      "**/.git": true,
      "**/.svn": true,
      "**/.hg": true,
      "**/CVS": true,
      "**/.DS_Store": true,
      "**/node_modules": true,
      "**/package-lock.json": true
  },
  "vetur.format.defaultFormatter.html": "js-beautify-html",
  "editor.formatOnSave": true,
  "prettier.semi": false,
  "prettier.singleQuote": true,
  "html.format.endWithNewline": true,
  "eslint.validate": [
      "javascript",
      "javascriptreact",
      "html",
      "vue",
      {
          "language": "html",
          "autoFix": true
      }
  ]
  ```

- vscode代码格式化：

  - 参考链接：
    - https://www.cnblogs.com/benbentu/p/9661998.html
    - https://www.jianshu.com/p/f15b67c94c78

- 关于编辑工具中的eslint插件和webpack安装的插件区别：

  - webpack是静态模块打包器，是在编译时期按照规则校验用的，报错了会停止编译；vscode是提示给使用者看的，方便提示报错与修复。
  - 参考链接：https://segmentfault.com/q/1010000015852118

- 关于babel

  - Babel 是一个工具链，主要用于将 ECMAScript 2015+ 版本的代码转换为向后兼容的 JavaScript 语法，以便能够运行在当前和旧版本的浏览器或其他环境中。
  - 配置文件：babel.config.js / .babelrc.js
    - 配置参数-plugins
    - 配置参数-presets(一组Babel插件)

- vscode插件列表：

  | 名称                                                      | 简述                                                         |
  | --------------------------------------------------------- | ------------------------------------------------------------ |
  | Auto Close Tag                                            | 自动闭合HTML标签                                             |
  | Auto Rename Tag                                           | 修改HTML标签时，自动修改匹配的标签                           |
  | Auto Import                                               | import提示                                                   |
  | Beautify css/sass/scss/less                               | css/sass/less格式化 （个人装了Beautify、 Beautify css/sass/scss/less、vue-Beautify）需要使用配置文件 |
  | Chinese (Simplified) Language Pack for Visual Studio Code | 设置中文包/配置中文语言                                      |
  | color info                                                | 小窗口显示颜色值，rgb,hsl,cmyk,hex等等                       |
  | eslint                                                    | es语法检测工具，需要配置配置文件                             |
  | editor config for vscode                                  | editorconfig插件以及点editorconfig配置文件（统一代码风格工具） |
  | git blame                                                 | 在状态栏显示当前行的Git信息                                  |
  | git history                                               | 查看git log                                                  |
  | HTML/js Snippets                                          | 快速代码模板(类似idea代码模板)                               |
  | intelliSense for CSS class names in HTML                  | 把项目中 css 文件里的名称智能提示在 html 中                  |
  | Indenticator                                              | 缩进高亮                                                     |
  | npm / npm Intellisense(智能感知)                          | 导入模块时，提示已安装模块名称                               |
  | Path Intellisense                                         | 另一个路径完成提示                                           |
  | vetur                                                     | 目前比较好的Vue语法高亮                                      |
  | vscode-fileheader                                         | 自动生成头部注释 ctrl+alt+i(**需要配置内容**)                |
  | saas                                                      | ==                                                           |
  | vscode-icons                                              | vscode 文件图标：文件=>首先项=>文件图标主题=>选中VSCode Icons |

  - 参考链接：https://github.com/varHarrie/varharrie.github.io/issues/10
  - 已使用的标签：

  ```
  1. Auto Close Tag
  2. Auto Import
  3. Auto Rename Tag
  4. Chinese (Simplified) Language Pack for Visual Studio Code
  5. VSCode Color Info
  6. VS Code ESLint extension
  7. Git Blame、Git History
  8. Indenticator
  9. koroFileHeader
  10. Manta's Stylus Supremacy
  11. Path Intellisense
  12. Prettier Formatter for Visual Studio Code
  13. vetur
  14. vscode-icons
  ```

### vscode使用

#### 用户设置

```
{

    "workbench.editor.focusRecentEditorAfterClose": false,

    "terminal.external.osxExec": "iTerm.app",

    "editor.tabSize": 2,
    "editor.fontLigatures": true,
    // "editor.formatOnSave": true,
    "vetur.validation.template": true,
    // 使用插件格式化 html
    "vetur.format.defaultFormatter.html": "js-beautify-html",
    // 格式化插件的配置
    "vetur.format.defaultFormatterOptions": {
        "js-beautify-html": {
            // 属性强制折行对齐
            "wrap_attributes": "force-aligned"
        }
    },
    "files.associations": {
        "*.vue": "vue"
    },
    "emmet.syntaxProfiles": {
        "vue-html": "html",
        "vue": "html"
    },
    "emmet.includeLanguages": {
        "vue-html": "html",
        "wxml": "html"
    },
    "git.autofetch": true,
    "git.confirmSync": false,
    // 添加 vue 支持
    "eslint.validate": [
        "javascript",
        "javascriptreact",
        "html",
        {
            "language": "vue",
            "autoFix": true
        }
    ],
    "javascript.validate.enable": true,


    "explorer.confirmDragAndDrop": true,

    "fileheader.Author": "HISUN",
    "fileheader.LastModifiedBy": "HISUN",
    "breadcrumbs.enabled": true,

    "javascript.updateImportsOnFileMove.enabled": "always",
    "javascript.implicitProjectConfig.experimentalDecorators": true,

    "html.format.indentInnerHtml": true,
    "html.format.endWithNewline": true,
    "html-css-class-completion.enableEmmetSupport": true,
    "window.zoomLevel": 0,
    "[javascript]": {
        "editor.defaultFormatter": "HookyQR.beautify"
    },
    "files.autoSave": "off",
    "workbench.sideBar.location": "right",
}
```

|      |      |
| ---- | ---- |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
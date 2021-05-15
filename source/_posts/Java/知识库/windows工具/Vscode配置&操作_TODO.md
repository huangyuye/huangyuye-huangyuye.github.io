**参考链接**

https://www.jianshu.com/p/fd945e8e099d

**配置步骤**

文件 》 设置 》 首选项：区分用户区和工作区

**插件安装**

**基础插件**

1、汉化包

插件：Chinese(Simplified) Language Pack for Visual Stidio Code 中文汉化包

配置：按快捷键Ctrl+Shift+P调出命令面板，输入Configure Display Language，选择zh-ch，然后重启vs code

2、Auto Close Tag 自动闭合标签

3、Auto Rename Tag 尾部闭合标签同步修改

4、Bracket Pair Colorizer 用不同颜色高亮显示匹配的括号

5、Highlight Matching Tag 高亮显示匹配标签

6、Vscode-icons VSCode 文件图标

7、TODO Highlight 高亮

使用：输入TODO & FIXME 文本

8、Code Spell Checker 单词拼写检查

9、Improt Cost 成本提示

说明：这个插件可以在你导入工具包的时候提示这个包的体积，如果体积过大就需要考虑压缩包，为后期上线优化做准备。

10、GitLens 查看Git信息

说明：将光标移到代码行上，即可显示当前行最近的commit信息和作者

11、Bookmarks 书签

说明：对代码进行书签标记，通过快捷键实现快速跳转到书签位置。

使用：

12、koroFileHeader

**配置：**参考 [https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE](https://github.com/OBKoro1/koro1FileHeader/wiki/配置)

// 文件头部注释  "fileheader.customMade": {    "Descripttion": "",    "Author": "yuye.huang",    "Date": "Do not edit",    "LastEditors": "yuye.huang",    "LastEditTime": "Do not Edit"  },  // 函数注释  "fileheader.cursorMode": {    // "name":"",    // "test":"test font",    "msg": "",    "param": "",    "return": ""  },

**使用：**

文件头部添加注释:

window：ctrl+alt+i,mac：ctrl+cmd+i

在光标处添加函数注释:

window：ctrl+alt+t,mac：ctrl+cmd+t

**扩展插件**

1、Vscode-element-helper

说明：使用element-ui库的可以安装这个插件，编写标签时自动提示element标签名称。

2、Version Lens 工具包版本信息

说明：在package.json中显示你下载安装的npm工具包的版本信息，同时会告诉你当前包的最新版本。

3、Vetur VUE语言包

说明：Vetur对VUE提供了很好的语言支持，没有安装该插件之前之前编写后缀名为.vue的文件时代码是白色的

使用：安装插件后编写vue文件输入s，按Tab键就可以自动补全模版。

4、**Settings Sync VSCode设置同步到Gist**

说明：有时候我们到了新公司或者换了新电脑需要配置新的开发环境，这时候一个一个下载插件，再重新配置vs code就非常麻烦而且你还不一定记得那么全面，通过这个插件我们可以将当前vs code中的配置上传到Gist，之后再通过Gist下载，就可以将所有配置同步到新环境中了。

使用：

1. 在Github首页点击头像，选择Settings进入设置页面。
2. 点击左侧侧边栏Developer settings，
3. 进入开发者设置。选择Personal access tokens，点击右侧Generate new token。
4. 填写token名称，在下方勾选gist。
5. 点击下方的Generate token按钮生成一个新的token。将生成的新的token保存下来。
6. 在vscode中安装Settings Sync插件，输入Ctrl+Shift+p输入Sync，选择更新/上传配置。
7. 将github中生成的token输入，点击回车。
8. 在控制台中自动生成一串id,之后便可以通过token和id进行配置同步。
9. 输入Ctrl+Shift+p输入Sync，选择下载配置，输入token和id即可同步下载。
10. 可以在配置文件中选择是否自动上传和下载

**常用配置**

1、关闭标签介绍信息（延时）

配置："editor.hover.delay": 5000,

2、自动折行

说明：设置代码根据编辑器窗口大小自动折行

配置："editor.wordWrap": "on",

3、字体设置

配置：

 // 一款适合代码显示的字体包（需要将字体包下载到本地）

 "editor.fontFamily": "Source Code Pro, 'Source Code Pro'",

 // 设置代码字体大小

 "editor.fontSize": 15,

4、自动保存

配置："files.autoSave": "off",

选项：

off：关闭自动保存。

afterDelay：当文件修改后的时间超过"Files：Auto Save Delay"中配置的值时自动进行保存。

onFocusChange：编辑器失去焦点时自动保存更新后的文件。

onWindowChange：窗口失去焦点时自动保存更新后的文件。

5、关闭代码提示

配置："editor.quickSuggestions": { "other": false, "comments": false, "strings": false }

6、// 控制可以打开多个标签页

  "workbench.editor.enablePreview": false,

**快捷键**

**编辑器与窗口管理**

Ctrl+Shift+P: 打开命令面板。

Ctrl+Shift+N: 新建窗口。

Ctrl+Shift+W: 关闭窗口。

切分窗口：Ctrl+1/Ctrl+3/Ctrl+3

Ctrl+H：最小化窗口

Ctrl+B：显示/隐藏侧边栏

Ctrl+"+/-"：放大/缩小界面

**文件操作**

Ctrl+N：新建文件

Ctrl+W：关闭文件

Ctrl+Tab：文件切换

**格式调整**

Ctrl+C/Ctrl+V：复制或剪切当前行/当前选中内容

Alt+Up/Down：向上/下移动一行

Shift+Alt+Up//Down：向上/下复制一行

Ctrl+Delete：删除当前行

Shift+Alt+Left/Right：从光标开始向左/右选择内容

**代码编辑**

Ctrl+D：选中下一个相同内容

Ctrl+Shift+L：选中所有相同内容

Ctrl+F：查找内容

Ctrl+Shit+F：在整个文件夹中查找内容

**不同语言环境配置**

**vue**

参考链接：

https://segmentfault.com/a/1190000014796012

https://www.cnblogs.com/benbentu/p/9661998.html

https://www.jianshu.com/p/f15b67c94c78

https://blog.csdn.net/userkang/article/details/84302629

配置文件：

\- Vetur : 语法高亮等功能 - ESlint : 代码风格检测 - Prettier formatter **for** Visual Studio Code: 为了配合 ESlint vue 的模板格式化： "vetur.format.defaultFormatter.html":"js-beautify-html" vue 模板的 eslint 校验    "eslint.validate": [        "javascript",        "javascriptreact",        "html",        "vue",        {            "language": "html",            "autoFix": **true**        }    ] Prettier 设置去除不必要空格："prettier.semi": **false** Prettier 设置格式化后"变'："prettier.singleQuote": true 在 vscode 中排除 node_modules 等文件夹： "files.**exclude**": {    "**/.git": true,    "**/.svn": true,    "**/.hg": true,    "**/CVS": true,    "**/.DS_Store": true,    "**/node_modules": true,    "**/package-**lock**.json": true, }, 以新行结束 "html.**format**.endWithNewline": false 完整的 vue 相关用户设置如下 "files.**exclude**": {    "**/.git": true,    "**/.svn": true,    "**/.hg": true,    "**/CVS": true,    "**/.DS_Store": true,    "**/node_modules": true,    "**/package-**lock**.json": true }, "vetur.**format**.defaultFormatter.html": "js-beautify-html", "editor.formatOnSave": true, "prettier.semi": false, "prettier.singleQuote": true, "html.**format**.endWithNewline": true, "eslint.**validate**": [    "javascript",    "javascriptreact",    "html",    "vue",    {        "**language**": "html",        "autoFix": true    } ]

**小程序**

参考链接：

https://www.jianshu.com/p/4b849843c3b9

安装插件：

1、minapp

2、wechat-snippet

3、wxml

4、wechat-snippet

**Wepy**

参考链接：

https://www.cnblogs.com/cisum/p/9852571.html

安装插件Vetur后打开.wpy文件，在右下角点击“纯文本”选择".wpy的配置文件关联"选择"Vue"后，语法变为高亮： **files.associations**

// 配置语言的文件关联(如: "*.extension": "html")。这些关联的优先级高于已安装语言的默认关联。

"files.associations": {

​    "*.vue": "vue",

​    "*.wpy": "vue",

​    "*.wxss": "postcss",

​    "*.tpl": "vue",

​    "*.md@xxx": "markdown",

​    "*.wepy": "vue",

​    "*.cjson": "jsonc",

​    "*.wxs": "javascript"

  },

// 添加emmet语法支持 wxml 文件(当然也可以配置支持vue文件)

"emmet.includeLanguages": {

​    "wxml": "html"

  },

  // "emmet.syntaxProfiles": {

  //   "vue-html": "html",

  //   "vue": "html"

  // },

"minapp-vscode.disableAutoConfig": true,

**格式化插件**

参考链接：

[**https://www.jianshu.com/p/e73f1b429788?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation**](https://www.jianshu.com/p/e73f1b429788?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)

https://blog.csdn.net/weixin_34413065/article/details/86133692

**wpy的文件都是用vue的高亮规则：**

"files.associations": {

​        "*.vue": "vue",

​        "*.wpy": "vue",

​        "*.wxml": "html",

​        "*.wxss": "css"

​    },

​    "emmet.syntaxProfiles": {

​        "vue-html": "html",

​        "vue": "html"

​    },

**wpy-beautify & vetur语法高亮 &****vscode-wxml 语法支持及代码片段**

**vetur：**

说明：插件的使用需要修改下vs-code对于文件格式的判断才能使用不同的插件，点击文件右下角可以修改文件格式并应用于所有该格式的文件

wpy-beautify插件配置：

wpy-beautify插件使用：

{

​      "key": "ctrl+shift+6",          

​      "command": "wpyBeautify.format",

​      "when": "editorTextFocus && !editorReadonly" 

​    }

**最新配置**

**用户空间**

{

  "editor.fontSize": 12,

  // 一个制表符等于的空格数。该设置在 `editor.detectIndentation` 启用时根据文件内容进行重写。

  "editor.tabSize": 2,

  // 自动保存

  "files.autoSave": "off",

  // 控制编辑器是否应呈现缩进参考线

  "editor.renderIndentGuides": true,

  // 当打开文件时，将基于文件内容检测 "editor.tabSize" 和 "editor.insertSpaces"。

  "editor.detectIndentation": false,

  "window.zoomLevel": 1,

  "editor.wordWrap": "on",

  "workbench.iconTheme": "vscode-icons",

  "editor.wordSeparators": "`~!@#$%^&*()=+[{]}\\|;:'\",.<>/?。",

  "editor.minimap.enabled": true,

  // 配置 glob 模式以排除文件和文件夹。

  "files.exclude": {

​    "**/.git": true,

​    "**/.svn": true,

​    "**/.hg": true,

​    "**/CVS": true,

​    "**/.DS_Store": true,

​    "**/.DS_Store": true,

​    // "**/node_modules": true,

​    "**/bower_components": true

​    // "**/dist": true

  },

  // 配置语言的文件关联(如: "*.extension": "html")。这些关联的优先级高于已安装语言的默认关联。

  "files.associations": {

​      "*.vue": "vue",

​      "*.wpy": "vue",

​      "*.wxss": "postcss",

​      "*.tpl": "vue",

​      "*.md@xxx": "markdown",

​      "*.wepy": "vue"

  },

  // 关闭代码提示 // 控制键入时是否应自动显示建议

  "editor.quickSuggestions": {

​    "other": true,

​    "comments": true,
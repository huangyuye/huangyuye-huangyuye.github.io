

[解决PowerShell 安装oh-my-posh很慢](https://blog.csdn.net/pi31415926535x/article/details/108331325)：



```
if (!(Test-Path -Path $PROFILE )) { New-Item -Type File -Path $PROFILE -Force }
notepad $PROFILE
```



```
# Import Modules
Import-Module posh-git
Import-Module oh-my-posh

# Set Theme (Agnoster
Set-Theme Paradox

# Set MenuComplete
Set-PSReadlineKeyHandler -Key Tab -Function MenuComplete
```




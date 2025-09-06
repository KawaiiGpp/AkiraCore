# Akira Core
这是 Akira 系列 Spigot 插件开发的必要基础，  
可提供开发过程中常用的基类、封装类、Utils类等实用工具。  
目前基于`Spigot API 1.20`开发，作为前置插件使用。
> 后续可能会开发 Kotlin 版本，届时当前版本可能停止维护。  
> 未来的 Kotlin 版本可能会使用 Paper Spigot 作为核心。
<br>

## 已实现功能
- 提供`AkiraPlugin`作为插件主类基类，内置默认行为；
- 提供`Manager`类方便对成员的统一管理；
- 提供与配置文件相关的管理工具：`ConfigFile`与`ConfigManager`；
- 提供大量实用的静态方法在`CommonUtils`、`BukkitUtils`等类中；
- 提供简易GUI框架：由`Gui`、`GuiItem`、`GuiManager`等类组成；
- 提供升级系统的基础逻辑：`LevelData`；
- 提供简易全息文本显示框架：`Hologram`、`HologramManager`；
- 提供物品管理工具：`ItemBuilder`、`ItemTagEditor`。
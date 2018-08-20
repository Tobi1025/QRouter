# QRouter
Android 路由框架，实现页面跳转的统一管理并与Activity解耦

项目里只需维护一张路由表(IPageRouterTable)即可。
## 功能
* `链式调用，简单粗暴，一行代码实现页面跳转`
* 可以通过`Map与key-value两种方式`实现页面间的`参数传递`
    * `Map方式`，直接传入已封装了Map的序列化对象JumpDataModel即可
    * `key-value方式`，需要在本地路由表里注明所需要传递的参数的键名key
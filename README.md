# QRouter
Android 路由框架，实现页面跳转的统一管理并与Activity解耦

项目里只需维护一张路由表(IPageRouterTable)即可。
## 功能
* 封装了`隐示跳转、显示跳转`两种跳转方式
* `链式调用，简单粗暴，一行代码实现页面跳转`
* 可以通过`Map与URI拼接两种方式`实现页面间的`参数传递`
    * `Map方式`，直接传入已封装了Map的序列化对象JumpDataModel即可(为了提高复用性，显示跳转统一采用Map方式传参)
    * `URI拼接方式`，需要在本地路由表方法参数注解里注明所需要传递的参数的键名key

## 注意
隐示路由跳转优先从路由表相应的配置的方法注解里获取Action、Scheme、Host、Port、Path的值，
如果没获取到，再从RouterBuilder里获取(builder里的值是通过代码设置的，如:builder.Scheme(..)、builder.host(..)等)。
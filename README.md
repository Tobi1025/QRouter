# QRouter
Android 路由框架，实现页面跳转的统一管理并与Activity解耦

通过注解在编译时完成对所有Activity的注册，不影响性能。
## 功能
* 封装了`隐示跳转(Web跳转)、显示跳转(原生跳转)`两种跳转方式
* `链式调用，简单粗暴，一行代码实现页面跳转`
* 内部通过`Map`实现页面间的`参数传递`
    * `Map方式`，直接传入已封装了Map的序列化对象JumpDataModel

## 注意
* 隐示路由跳转优先从路由表相应的配置的方法注解里获取Action、Scheme、Host、Port的值，
如果没获取到，再从RouterBuilder里获取(builder里的值是通过代码设置的，如:builder.Scheme(..)、builder.host(..)等)
* 可以在Application中通过RouterBuilder.init()函数设置默认的Scheme、Host。
* 隐示跳转(Web跳转)的path必须是以"/"开头，显示跳转(原生跳转)无要求
## 使用方式
* 显示(原生跳转)
    ``` java
    RouterBuilder.getBuilder()
            .putStringExtra(SecondActivity.userName, "origin")
            .putStringExtra(SecondActivity.userAge, "22")
            .startOriginUri(MainActivity.this, "commonRouterActivity");

     //对应的Activity
     @RouterUri(path = "commonRouterActivity")
     public class CommonRouterActivity extends AppCompatActivity{

     }
     ```
* 隐示(Web跳转)
    ``` java
    RouterBuilder.getBuilder()
            .putStringExtra(SecondActivity.userName, "web")
            .putStringExtra(SecondActivity.userAge, "18")
            .startWebUri(MainActivity.this, "/jumpSecondActivity");

    //对应的Activity
    @RouterUri(path = "/jumpSecondActivity")
    public class SecondActivity extends AppCompatActivity{

    }
    ```
* 获取参数
    ``` java
    JumpDataModel jumpDataModel = JumpDataModel.getInstance();
    //获取String类型参数
    jumpDataModel.getStringExtra("key")
    //获取int类型参数
    jumpDataModel.getIntExtra("key")
    //获取boolean类型参数
    jumpDataModel.getBooleanExtra("key")
    //获取指定任意类型参数
    jumpDataModel.getObjectExtra("key", HashMap.class)
    ```
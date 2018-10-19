# QRouter
Android 路由框架，实现页面跳转的统一管理并与Activity解耦

通过注解在编译时完成对所有Activity的注册，不影响性能。
## 功能
* 封装了`隐示跳转(Web跳转)、显示跳转(原生跳转)`两种跳转方式
* `链式调用，简单粗暴，一行代码实现页面跳转`
* 内部通过`Map`实现页面间的`参数传递`
    * `Map方式`，直接传入已封装了Map的序列化对象JumpDataModel
* 可以添加路由拦截器，在跳转前处理一些逻辑，如：登录判断、定位获取等
## 注意
* 隐示路由跳转优先从路由表相应的配置的方法注解里获取Action、Scheme、Host、Port的值，
如果没获取到，再从RouterBuilder里获取(builder里的值是通过代码设置的，如:builder.Scheme(..)、builder.host(..)等)
* 可以在Application中通过RouterBuilder.init()函数设置默认的Scheme、Host。
* 隐示跳转(Web跳转)的path必须是以"/"开头，显示跳转(原生跳转)无要求
* 自定义路由拦截器中，最后一定不要忘了callback.onNext();
## 使用方式
* Application中完成注册,指定默认的Scheme与host
    ``` java
    RouterBuilder.register(Cons.scheme,Cons.host);
    ```
* 显示(原生跳转)
    ``` java
    RouterBuilder.getBuilder()
                            .putStringExtra(CommonRouterActivity.userName, "str-origin")
                            .putStringExtra(CommonRouterActivity.userAge, "str-22")
                            .putIntExtra("intKey1", 1)
                            .putIntExtra("intKey2", 2)
                            .putBooleanExtra("booleanKey1", true)
                            .putBooleanExtra("booleanKey2", false)
                            .putObjectExtra("objKey1", map1)
                            .putObjectExtra("objKey2", map2)
                            .startOriginUri(MainActivity.this, "commonRouterActivity");

     //对应的Activity
     @RouterUri(path = "commonRouterActivity")
     public class CommonRouterActivity extends AppCompatActivity{
                                    .
                                    .
                                    .
        JumpDataModel jumpDataModel = JumpDataModel.getInstance();
        String name = jumpDataModel.getStringExtra(userName);
        String age = jumpDataModel.getStringExtra(userAge);
        int intValue1 = jumpDataModel.getIntExtra("intKey1", -1);
        int intValue2 = jumpDataModel.getIntExtra("intKey2", -1);
        boolean bValue1 = jumpDataModel.getBooleanExtra("booleanKey1", false);
        boolean bValue2 = jumpDataModel.getBooleanExtra("booleanKey2", false);
        HashMap map1 = jumpDataModel.getObjectExtra("objKey1", HashMap.class);
        HashMap map2 = jumpDataModel.getObjectExtra("objKey2", HashMap.class);

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
* 可以添加一个或多个路由拦截器。继承UriInterceptor，实现intercept()拦截方法即可。
    ``` java
    public class LocationInterceptor implements UriInterceptor {
        @Override
        public void intercept(@NonNull Context context, @NonNull UriCallback callback) {
            //TODO 可进行跳转前的一些逻辑判断,如获取位置信息
            Log.e("Interceptor", "定位拦截器执行");
            callback.onNext();
        }
    }

    public class LoginInterceptor implements UriInterceptor {
        @Override
        public void intercept(@NonNull Context context, @NonNull UriCallback callback) {
            //TODO 可进行跳转前的一些逻辑判断,如判断登录状态
            Log.e("Interceptor","登录拦截器执行");
            callback.onNext();
        }
    }

    //对应Activity
    @RouterUri(path = "commonRouterActivity", interceptors = {LoginInterceptor.class,LocationInterceptor.class})
    public class CommonRouterActivity extends AppCompatActivity {

    }
    ```


# QRouter
Android 路由框架，实现页面跳转的统一管理并与Activity解耦

通过自定义注解处理器在编译时完成对所有Activity的注册，不影响性能。
## 功能
* 封装了`隐示跳转(Web跳转)、显示跳转(原生跳转)`两种跳转方式
* `链式调用，简单粗暴，一行代码实现页面跳转`
* 内部通过`Bundle`实现页面间的`参数传递`,支持任何类型的参数，调用时与Android原生api一样，有种熟悉的感觉
* 支持startActivityForResult
* 支持设置Activity切换动画
* 支持设置Intent的Flags
* 可以添加一个或多个路由拦截器，在跳转前处理一些逻辑，如：登录判断、定位获取等
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
                            .putExtra(CommonRouterActivity.userName, "str-origin")
                            .activityRequestCode(1001)
                            .overridePendingTransition(R.anim.enter,R.anim.exit)
                            .setIntentFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .startOriginUri(MainActivity.this, "commonRouterActivity");

     //对应的Activity
     @RouterUri(path = "commonRouterActivity")
     public class CommonRouterActivity extends AppCompatActivity{
                                    .
                                    .
                                    .
       String userName =  intent.getStringExtra();//与原生api一样
     }
     ```
* 隐示(Web跳转)
    ``` java
    RouterBuilder.getBuilder()
            .putExtra(SecondActivity.userName, "web")
            .startWebUri(MainActivity.this, "/jumpSecondActivity");

    //对应的Activity
    @RouterUri(path = "/jumpSecondActivity")
    public class SecondActivity extends AppCompatActivity{

    }
    ```
* 获取参数
    ``` java
    String userName =  intent.getStringExtra();//与原生api一样
    ```
* 可以添加一个或多个路由拦截器。继承UriInterceptor，实现intercept()拦截方法即可。
    ``` java
    public class LocationInterceptor implements UriInterceptor {
        @Override
        public void intercept(@NonNull Context context,Bundle bundle, @NonNull UriCallback callback) {
            //TODO 可进行跳转前的一些逻辑判断,如获取位置信息
            Log.e("Interceptor", "定位拦截器执行");
            if (bundle != null) {
               Log.e(TAG, "userName = " + bundle.getString("user_name"));
            }
            callback.onNext();
        }
    }

    public class LoginInterceptor implements UriInterceptor {
        @Override
        public void intercept(@NonNull Context context, Bundle bundle,@NonNull UriCallback callback) {
            //TODO 可进行跳转前的一些逻辑判断,如判断登录状态
            Log.e("Interceptor","登录拦截器执行");
            if (bundle != null) {
                Log.e(TAG, "userName = " + bundle.getString("user_name"));
            }
            callback.onNext();
        }
    }

    //对应Activity
    @RouterUri(path = "commonRouterActivity", interceptors = {LoginInterceptor.class,LocationInterceptor.class})
    public class CommonRouterActivity extends AppCompatActivity {

    }
    ```
* 混淆配置
    ``` java
    -keep class com.personal.joefly.interfaces.**{*;}
    -dontwarn com.personal.joefly.interfaces.**

    -keep class com.personal.joefly.compile.**{*;}
    -dontwarn com.personal.joefly.compile.**

    -keep class com.personal.joefly.qrouter.uri.**{*;}
    -dontwarn com.personal.joefly.qrouter.uri.**

    -keep class com.personal.joefly.qrouter.api.**{*;}
    -dontwarn com.personal.joefly.qrouter.api.**
    ```


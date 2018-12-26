# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/qiaojingfei/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#Qrouter 路由混淆配置
-keep class com.personal.joefly.interfaces.**{*;}
-dontwarn com.personal.joefly.interfaces.**

-keep class com.personal.joefly.compile.**{*;}
-dontwarn com.personal.joefly.compile.**

-keep class com.personal.joefly.qrouter.uri.**{*;}
-dontwarn com.personal.joefly.qrouter.uri.**

-keep class com.personal.joefly.qrouter.api.**{*;}
-dontwarn com.personal.joefly.qrouter.api.**
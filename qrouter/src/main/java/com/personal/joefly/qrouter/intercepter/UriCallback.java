package com.personal.joefly.qrouter.intercepter;

/**
 * description: 拦截器拦截完成后的回调
 * author: qiaojingfei
 * date: 2018/10/17 下午6:27
*/
public interface UriCallback  {

    /**
     * 处理完成，继续后续流程。
     */
    void onNext();

    /**
     * 处理完成，终止分发流程。
     *
     */
    void onComplete();
}

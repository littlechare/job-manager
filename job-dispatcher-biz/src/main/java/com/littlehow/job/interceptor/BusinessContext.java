package com.littlehow.job.interceptor;

/**
 * 用于存放业务唯一标识的上下文
 * littlehow 2018/4/6
 */
public class BusinessContext {
    private static final ThreadLocal<String> businessContext = new ThreadLocal<>();

    static void setBusinessId(String businessId) {
        businessContext.set(businessId);
    }

    public static String getBusinessId() {
        return businessContext.get();
    }

    static void removeBusinessId() {
        businessContext.remove();
    }
}

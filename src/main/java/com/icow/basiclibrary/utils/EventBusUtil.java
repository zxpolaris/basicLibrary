package com.icow.basiclibrary.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具类
 *
 * @author Xun.Zhang
 * @version 1.0
 */
public class EventBusUtil {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void sendEvent(BusEvent baseBusEvent) {
        EventBus.getDefault().post(baseBusEvent);
    }
    public static void sendEvent(MessageEvent baseBusEvent) {
        EventBus.getDefault().post(baseBusEvent);
    }
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendStickyEvent(BusEvent baseBusEvent) {
        EventBus.getDefault().postSticky(baseBusEvent);
    }

}

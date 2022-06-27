package com.vison.wonfu;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);;

    public App() {
        log.info("app init..");
    }

    public static  String userTokenPrefix = "user:";

    public static String operatorPrefix = "operator:";
}

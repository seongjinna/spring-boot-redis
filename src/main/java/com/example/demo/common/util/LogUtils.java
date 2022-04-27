package com.example.demo.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {
    public static void logStackTrace(Exception e) {
        log.error("Error Message: {}", e.getMessage());
        StackTraceElement[] elements = e.getStackTrace();
        for (StackTraceElement element : elements) {
            log.error(element.toString());
        }
    }
}

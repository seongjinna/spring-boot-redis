package com.example.demo.common.advice;

import com.example.demo.common.exception.Message;
import lombok.Getter;

public enum Errors {

    E0000 ("0000", "성공", "Success"),
    E9999 ("9999", "서비스 오류 안내", "알 수 없는 오류가 발생했습니다.\n잠시 후 다시 시도해 주세요."),
    BAD_REQUST_RAPAM("0400", "서비스 오류 안내", "잘못된 요청입니다. \n잠시 후 다시 시도해 주세요.");

    @Getter
    String code;

    @Getter
    Message message;

    Errors(String code, String title, String content) {
        this.code = code;
        message = Message.builder()
                .title(title)
                .content(content)
                .build();
    }

    Errors(String code, String msg) {
        this.code = code;

        String title = "";
        String content = "";

        if (msg.contains("||")) {
            String[] msgs = msg.split("\\|\\|");
            title = msgs[0];
            content = msgs[1];
        }
        else {
            title = msg;
            content = msg;
        }

        message = Message.builder()
                .title(title)
                .content(content)
                .build();
    }
}

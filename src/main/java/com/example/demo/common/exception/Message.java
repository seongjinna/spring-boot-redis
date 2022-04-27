package com.example.demo.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String title;
    private String content;
}

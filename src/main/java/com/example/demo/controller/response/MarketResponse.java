package com.example.demo.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MarketResponse {

    private List<Market> markets;

    @Data
    @Builder
    public static class Market {
        String market;

        String koreanName;

        String englishName;

        String marketWarning;
    }
}

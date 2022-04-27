package com.example.demo.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class MarketInfoResponseDto {

    // 업비트에서 제공중인 시장 정보
    private String market;

    // 거래 대상 암호화폐 한글명
    private String korean_name;

    // 거래 대상 암호화폐 영문명
    private String english_name;

    // 유의 종목 여부(NONE: 해당 사항 없음, CAUTION: 투자유의)
    private String market_warning;
}

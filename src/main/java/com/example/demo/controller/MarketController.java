package com.example.demo.controller;

import com.example.demo.common.exception.BizException;
import com.example.demo.controller.response.MarketResponse;
import com.example.demo.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    /**
     * 마켓 정보 조회
     */
    @Operation(summary = "마켓 정보 조회", description = "Repository 방식으로 마켓 정보 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping(value = "/list")
    public MarketResponse getMarketInfo() throws BizException {
        return marketService.getMarketInfo();
    }

    /**
     * 마켓 정보 조회
     */
    @Operation(summary = "마켓 정보 조회", description = "RedisTemplate 방식으로 마켓 정보 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping(value = "/list2")
    public MarketResponse getMarketInfoWithRedisTemplate() throws BizException {
        return marketService.getMarketInfoWithRedisTemplate();
    }
}

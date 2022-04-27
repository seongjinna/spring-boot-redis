package com.example.demo.service;

import com.example.demo.common.exception.BizException;
import com.example.demo.common.util.RedisUtils;
import com.example.demo.controller.response.MarketResponse;
import com.example.demo.entity.redis.MarketInfoCacheDto;
import com.example.demo.entity.redis.MarketInfoTemplateCacheDto;
import com.example.demo.repository.MarketInfoCacheRepository;
import com.example.demo.service.dto.MarketInfoResponseDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketInfoCacheRepository marketInfoCacheRepository;
    private final Gson gson = new Gson();
    private final RedisUtils redisUtils;

    @Async
    public void createMarketInfoCacheDto() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.upbit.com/v1/market/all?isDetails=true"))
                    .header("Accept", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());

            List<MarketInfoResponseDto> marketInfoResponseDtoListList = gson.fromJson(response.body(), new TypeToken<List<MarketInfoResponseDto>>(){
            }.getType());
            log.info("marketInfoResponseDtoListList: {}", marketInfoResponseDtoListList);

            List<MarketInfoCacheDto> marketList = new ArrayList<>();

            for (MarketInfoResponseDto market : marketInfoResponseDtoListList) {
                marketList.add(MarketInfoCacheDto.builder()
                                .id(market.getMarket())
                                .market(market.getMarket())
                                .koreanName(market.getKorean_name())
                                .englishName(market.getEnglish_name())
                                .marketWarning(market.getMarket_warning())
                                .build());
            }

            marketInfoCacheRepository.saveAll(marketList);
            redisUtils.setRedisData("market-info-template", marketList, 10);
        } catch (IOException e) {
            log.warn(e.getMessage());
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
    }

    public MarketResponse getMarketInfo() throws BizException {

        MarketResponse marketResponse = new MarketResponse();
        marketResponse.setMarkets(new ArrayList<>());

        for (MarketInfoCacheDto marketInfoCacheDto : marketInfoCacheRepository.findAll()) {
            marketResponse.getMarkets().add(MarketResponse.Market.builder()
                            .market(marketInfoCacheDto.getMarket())
                            .koreanName(marketInfoCacheDto.getKoreanName())
                            .englishName(marketInfoCacheDto.getEnglishName())
                            .marketWarning(marketInfoCacheDto.getMarketWarning())
                            .build());
        }

        return marketResponse;
    }

    public MarketResponse getMarketInfoWithRedisTemplate() throws BizException {

        MarketResponse marketResponse = new MarketResponse();
        marketResponse.setMarkets(new ArrayList<>());

        String marketInfo = (String) redisUtils.getRedisData("market-info-template");
        log.debug("marketInfo: {}", marketInfo);

        if (StringUtils.hasLength(marketInfo)) {
            List<MarketInfoTemplateCacheDto> marketInfoTemplateCacheDtoList = gson.fromJson(marketInfo, new TypeToken<List<MarketInfoTemplateCacheDto>>() {
            }.getType());

            marketInfoTemplateCacheDtoList.forEach(marketInfoTemplateCacheDto -> {
                marketResponse.getMarkets().add(MarketResponse.Market.builder()
                        .market(marketInfoTemplateCacheDto.getMarket())
                        .koreanName(marketInfoTemplateCacheDto.getKoreanName())
                        .englishName(marketInfoTemplateCacheDto.getEnglishName())
                        .marketWarning(marketInfoTemplateCacheDto.getMarketWarning())
                        .build());
            });
        }
        return marketResponse;
    }
}

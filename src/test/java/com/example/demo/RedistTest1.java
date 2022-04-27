package com.example.demo;

import com.example.demo.domain.Point;
import com.example.demo.domain.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedistTest1 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @DisplayName("레디스 기본 등록 조회 기능")
    @Test
    public void 기본_등록_조회기능() {
        // given
        String id = "seongjin";
        LocalDateTime refreshTime = LocalDateTime.of(2022, 5, 2, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        // when
        pointRedisRepository.save(point);

        // then
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertEquals(savedPoint.getAmount(), 1000L);
        assertEquals(savedPoint.getRefreshTime(), refreshTime);
    }
}

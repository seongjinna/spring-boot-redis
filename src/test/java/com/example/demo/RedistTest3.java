package com.example.demo;

import com.example.demo.domain.Point;
import com.example.demo.domain.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "job.name=b")
public class RedistTest3 {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @DisplayName("레디스 수정 기능")
    @Test
    public void 수정기능() {
        // given
        String id = "seongjin";
        LocalDateTime refreshTime = LocalDateTime.of(2022, 5, 2, 0, 0);
        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build());

        // when
        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2022, 6, 1, 0, 0));
        pointRedisRepository.save(savedPoint);

        // then
        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertEquals(refreshPoint.getAmount(), 2000L);
    }
}

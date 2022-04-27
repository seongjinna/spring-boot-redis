package com.example.demo.repository;

import com.example.demo.entity.redis.MarketInfoCacheDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketInfoCacheRepository extends CrudRepository<MarketInfoCacheDto, String> {
}

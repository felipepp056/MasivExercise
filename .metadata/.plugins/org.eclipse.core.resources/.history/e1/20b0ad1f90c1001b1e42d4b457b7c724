package dev.abel.springbootredis.repository;

import dev.abel.springbootredis.domain.Roullete;

import java.util.Map;

public interface RedisRepository {
    Map<String, Roullete> findAll();
    Roullete findById(String id);
    void save(Roullete roullete);
    void delete(String id);
    String getId(Roullete roullete);
}

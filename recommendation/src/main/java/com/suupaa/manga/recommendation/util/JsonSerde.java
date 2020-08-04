package com.suupaa.manga.recommendation.util;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class JsonSerde<T> implements Serializer<T>, Deserializer<T>, Serde<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> objectClass;

    public JsonSerde(Class<T> objectClass) {
        this.objectClass = objectClass;
    }

    @SneakyThrows
    @Override
    public T deserialize(String topic, byte[] data) {
        return objectMapper.readValue(data, objectClass);
    }

    @SneakyThrows
    @Override
    public byte[] serialize(String topic, T data) {
        return objectMapper.writeValueAsBytes(data);
    }

    @Override
    public Serializer<T> serializer() {
        return this;
    }

    @Override
    public Deserializer<T> deserializer() {
        return this;
    }

    @Override
    public void close() {
        // nothing to close
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to configure
    }
}

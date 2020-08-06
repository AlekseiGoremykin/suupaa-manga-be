package com.suupaa.manga.recommendation.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("app-config.trending")
public class TrendingConfig {

    private Duration windowSize;
    private Duration windowAdvanceBy;

}

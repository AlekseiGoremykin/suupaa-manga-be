package com.suupaa.manga.recommendation.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("app-config.trending")
public class TrendingConfig {

    private Duration windowSize;
    private Duration windowAdvanceBy;

}

package com.suupaa.manga.recommendation.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import com.suupaa.manga.recommendation.config.TrendingConfig;
import com.suupaa.manga.recommendation.listener.model.MangaRate;
import com.suupaa.manga.recommendation.listener.model.Top5RatedAggregator;
import com.suupaa.manga.recommendation.util.RecommendationConstants;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class RecommendationService {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Autowired
    private TrendingConfig config;

    public Flux<MangaRate> mostRatedAllTime() {
        ReadOnlyKeyValueStore<String, Top5RatedAggregator> store = interactiveQueryService.getQueryableStore(
                RecommendationConstants.TOP_5_RATED_MANGA_STORE_NAME, QueryableStoreTypes.keyValueStore());
        Top5RatedAggregator aggregator = store.get(RecommendationConstants.TOP_5_RATED_MANGA_RECORD_KEY);


        return aggregator == null ? Flux.empty() : Flux.fromIterable(aggregator);
    }


    public Flux<MangaRate> trending() {
        ReadOnlyWindowStore<String, MangaRate> store = interactiveQueryService.getQueryableStore(
                RecommendationConstants.TRENDING_NOW, QueryableStoreTypes.windowStore());
        final KeyValueIterator<Windowed<String>, MangaRate> rates =
                store.fetchAll(Instant.now().minus(config.getWindowSize()), Instant.now());

        Set<MangaRate> mangaRates = new TreeSet<>();
        Set<String> usedKeys = new HashSet<>();
        while (rates.hasNext()) {
            final KeyValue<Windowed<String>, MangaRate> next = rates.next();
            if (usedKeys.contains(next.key.key())) {
                continue;
            }
            log.info("Value for {} is taken from period from {} to {}",
                    next.key.key(), next.key.window().startTime(), next.key.window().endTime());

            mangaRates.add(next.value);
            usedKeys.add(next.key.key());
        }

        return Flux.fromIterable(mangaRates);
    }
}

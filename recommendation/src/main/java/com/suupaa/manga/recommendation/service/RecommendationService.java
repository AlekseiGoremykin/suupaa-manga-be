package com.suupaa.manga.recommendation.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.suupaa.manga.recommendation.config.TrendingConfig;
import com.suupaa.manga.recommendation.controller.dto.MangaRating;
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

    public Flux<MangaRating> mostRatedAllTime() {
        ReadOnlyKeyValueStore<String, Top5RatedAggregator> store = null;

        HostInfo hostInfo = interactiveQueryService.getHostInfo(RecommendationConstants.TOP_5_RATED_MANGA_STORE_NAME,
                RecommendationConstants.TOP_5_RATED_MANGA_RECORD_KEY, Serdes.String().serializer());

        if (interactiveQueryService.getCurrentHostInfo().equals(hostInfo)) {
            log.info("Serving from same host: " + hostInfo);
            try {
                store = interactiveQueryService.getQueryableStore(
                        RecommendationConstants.TOP_5_RATED_MANGA_STORE_NAME, QueryableStoreTypes.keyValueStore());
            } catch (InvalidStateStoreException e) {
                log.info("State store {} on the instance {}:{} is not ready",
                        RecommendationConstants.TOP_5_RATED_MANGA_STORE_NAME, hostInfo.host(), hostInfo.port());
            }
            Top5RatedAggregator aggregator = store.get(RecommendationConstants.TOP_5_RATED_MANGA_RECORD_KEY);
            final Flux<MangaRate> result = aggregator == null ? Flux.empty() : Flux.fromIterable(aggregator);
            return result.map(MangaRating::from);
        } else {
            //find the store from the proper instance.
            log.info("Served from different host: " + hostInfo);
            return getFromHost(hostInfo, "recommendations/top-5-rated-all-time");
        }
    }

    public Flux<MangaRating> trending() {
        List<HostInfo> hostInfo = interactiveQueryService.getAllHostsInfo(RecommendationConstants.TRENDING_NOW);

        final Set<Flux<MangaRating>> allTrending = hostInfo.stream()
                .filter(info -> !info.equals(interactiveQueryService.getCurrentHostInfo()))
                .map(info -> getFromHost(info, "recommendations/trending"))
                .collect(Collectors.toSet());

        if (!hostInfo.contains(interactiveQueryService.getCurrentHostInfo())) {
            return Flux.concat(allTrending);
        }

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

        return Flux.concat(allTrending).concatWith(Flux.fromIterable(mangaRates).map(MangaRating::from));
    }

    private Flux<MangaRating> getFromHost(HostInfo hostInfo, String uri) {
        return WebClient.create(String.format("http://%s:%d", hostInfo.host(), hostInfo.port()))
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MangaRating.class);
    }


}

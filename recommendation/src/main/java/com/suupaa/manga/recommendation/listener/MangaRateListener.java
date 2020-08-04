package com.suupaa.manga.recommendation.listener;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.suupaa.manga.events.RateEvent;
import com.suupaa.manga.recommendation.config.TrendingConfig;
import com.suupaa.manga.recommendation.listener.model.MangaRate;
import com.suupaa.manga.recommendation.listener.model.Top5RatedAggregator;
import com.suupaa.manga.recommendation.util.JsonSerde;
import com.suupaa.manga.recommendation.util.RecommendationConstants;

import lombok.extern.slf4j.Slf4j;


@Component
@EnableBinding(MangaRateListener.MangaRateBinding.class)
@Slf4j
public class MangaRateListener {

    @Autowired
    private TrendingConfig config;

    @StreamListener
    public void process(@Input(RecommendationConstants.MANGA_RATE_INPUT_STREAM) KStream<String, RateEvent> rateEvents) {
        JsonSerde<Top5RatedAggregator> topAggregatorSerde = new JsonSerde<>(Top5RatedAggregator.class);
        JsonSerde<MangaRate> mangaRateSerde = new JsonSerde<>(MangaRate.class);

        final KGroupedStream<String, MangaRate> mangaRatesByMangaId = rateEvents.mapValues(RateEvent::getRate)
                .mapValues(MangaRate::new)
                .groupByKey();

        mangaRatesByMangaId
                .reduce(MangaRate::add, Materialized.with(Serdes.String(), mangaRateSerde))
                .groupBy((mangaId, rate) -> KeyValue.pair(RecommendationConstants.TOP_5_RATED_MANGA_RECORD_KEY, rate),
                        Grouped.with(Serdes.String(), mangaRateSerde))
                .aggregate(
                        Top5RatedAggregator::new,
                        (key, value, aggregator) -> aggregator.add(value),
                        (key, value, aggregator) -> aggregator.remove(value),
                        Materialized.<String, Top5RatedAggregator, KeyValueStore<Bytes, byte[]>>as(RecommendationConstants.TOP_5_RATED_MANGA_STORE_NAME)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(topAggregatorSerde)
                          );

        mangaRatesByMangaId
                .windowedBy(TimeWindows.of(config.getWindowSize()).advanceBy(config.getWindowAdvanceBy()))
                .reduce(
                        MangaRate::add,
                        Materialized.<String, MangaRate, WindowStore<Bytes, byte[]>>as(RecommendationConstants.TRENDING_NOW)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(mangaRateSerde)
                       );
    }

    interface MangaRateBinding {
        @Input(RecommendationConstants.MANGA_RATE_INPUT_STREAM)
        KStream<?, ?> inputStream();
    }
}

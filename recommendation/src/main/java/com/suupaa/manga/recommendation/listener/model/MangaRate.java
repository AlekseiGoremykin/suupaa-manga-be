package com.suupaa.manga.recommendation.listener.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
public class MangaRate implements Serializable, Comparable<MangaRate> {

    @EqualsAndHashCode.Include
    private String mangaId;
    private long count;
    private long sum;

    public MangaRate(String mangaId, long rate) {
        this(mangaId, 1L, rate);
    }

    private MangaRate(String mangaId, long count, long rate) {
        this.mangaId = mangaId;
        this.count = count;
        this.sum = rate;
    }

    public MangaRate add(long rate) {
        final long sum = this.sum + rate;
        final long count = this.count + Long.signum(rate);
        return new MangaRate(mangaId, count, sum);
    }

    public MangaRate add(MangaRate mangaRate) {
        final long sum = this.sum + mangaRate.sum;
        final long count = this.count + mangaRate.count;
        return new MangaRate(mangaId, count, sum);
    }

    public double average() {
        return (double) sum / count;
    }

    @Override
    public int compareTo(MangaRate o) {
        return Double.compare(o.average(), this.average());
    }
}

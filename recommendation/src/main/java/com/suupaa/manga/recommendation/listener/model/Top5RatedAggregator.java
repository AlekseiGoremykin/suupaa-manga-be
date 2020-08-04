package com.suupaa.manga.recommendation.listener.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Top5RatedAggregator implements Serializable, Iterable<MangaRate> {

    private SortedSet<MangaRate> rates = new TreeSet<>();

    private int size = 5;

    public Top5RatedAggregator add(MangaRate rate) {
        rates.add(rate);

        if (rates.size() > size) {
            rates.remove(rates.last());
        }

        return this;
    }

    public Top5RatedAggregator remove(MangaRate rate) {
        rates.remove(rate);
        return this;
    }

    @Override
    public Iterator<MangaRate> iterator() {
        return rates.iterator();
    }
}

package com.suupaa.manga.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateEvent {
    private long rate;
    private long timestamp;
}

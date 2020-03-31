package io.remedymatch.shared;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Component
public class GeoDatenCache {

    public final Map<String, Double> geoDatenMap = new WeakHashMap<>();

}

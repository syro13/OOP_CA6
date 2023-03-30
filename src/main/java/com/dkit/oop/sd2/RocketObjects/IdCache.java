package com.dkit.oop.sd2.RocketObjects;

import java.util.HashSet;

public class IdCache {
    private HashSet<Integer> cache;
    public IdCache() {
        cache = new HashSet<Integer>();
    }

    public boolean contains(int id) {
        return cache.contains(id);
    }

    public void add(int id) {
        cache.add(id);
    }

    public void remove(int id) {
        cache.remove(id);
    }
}

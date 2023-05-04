package com.dkit.oop.sd2.Filters;

import com.dkit.oop.sd2.DTOs.Rockets;

import java.util.Comparator;

    public class SortByCapacity implements Comparator<Rockets> {
        @Override
        public int compare(Rockets rockets, Rockets t1) {
            return Integer.compare(rockets.getPayload_capacity(), t1.getPayload_capacity());
        }
    }

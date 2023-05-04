package com.dkit.oop.sd2.Filters;

import com.dkit.oop.sd2.DTOs.Rockets;

import java.util.Comparator;

public class FilterByCapacity implements Comparator<Rockets> {
    private int min, max;
    public FilterByCapacity(int min, int max){
        this.min = min;
        this.max = max;
    }
    @Override
    public int compare(Rockets o1, Rockets o2) {
        if(o1.getPayload_capacity() > min && o1.getPayload_capacity() < max || o2.getPayload_capacity() > min && o2.getPayload_capacity() < max){
            return Integer.compare(o1.getPayload_capacity(), o2.getPayload_capacity());
        }
        return 0;
    }
}

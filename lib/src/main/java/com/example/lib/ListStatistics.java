package com.example.lib;

import java.util.ArrayList;
import java.util.List;

public class ListStatistics {
    private List<Statistics> statistics;

    public ListStatistics() {
        this.statistics = new ArrayList<>();
    }

    public ListStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public Statistics getSpecificStatistics(int position){
        return this.statistics.get(position);
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    public void addStatistics(Statistics statistics){
        this.statistics.add(statistics);
    }

    public void removeStatistics(Statistics statistics){
        this.statistics.remove(statistics);
    }

    public int getSize(){
        return this.statistics.size();
    }
}

package com.documentsimilarity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
 *Implementation of IDF referenced from 
 * https://medium.com/@adityamdk/tf-idf-implementation-in-java-f6c4d1d97e3b
 * https://titanwolf.org/Network/Articles/Article?AID=4280d712-e00c-43f7-acea-6dc375b65c2d
 * */

public class IDF {
	
	
    private final List<List<String>> paper = new ArrayList<>();
    private final Map<String, Double> weights = new HashMap<>();

    public void addPaper(List<String> p) {
    	paper.add(p);
    }

    public void weightCalculation() {
        for(List<String> Papers : paper) {
        	//getDistinct
            List<String> words = Papers.stream().distinct().collect(Collectors.toList()); 
            for(String w : words) {
                double wt = weights.getOrDefault(w, 0.0);
                wt++;
                weights.put(w, wt);
            }
        }

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            weights.put(entry.getKey(), Math.log10(entry.getValue() / paper.size()));
        }
    }

    public double getValue(String val) {
        return weights.getOrDefault(val, 0.0);
    }

    public List<String> getPapers() {
        List<String> similarPapers = new ArrayList<>();

        for(Map.Entry<String, Double> w : weights.entrySet()) {
            if(w.getValue() != 0.0) {
            	similarPapers.add(w.getKey());
            }
        }

        Collections.sort(similarPapers);

        return similarPapers;
    }
}

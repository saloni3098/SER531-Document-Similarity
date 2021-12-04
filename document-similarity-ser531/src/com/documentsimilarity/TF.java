package com.documentsimilarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Code referenced from :
 * https://www.geeksforgeeks.org/calculate-the-frequency-of-each-word-in-the-given-string/
 * https://www.baeldung.com/java-word-frequency
 * */

public class TF {
	private final Map<String, Double> wordFrequency = new HashMap<>();
    private double count;

    public void addWords(List<String> word) {
        for(String w : word) {
            double f = wordFrequency.getOrDefault(w, 0.0);
            f++;
            wordFrequency.put(w, f);
        }

        count = 0.0;
        for(double f : wordFrequency.values()) {
            count = count +  f;
        }
    }

    public double getFrequency(String term) {
        double frequency = wordFrequency.getOrDefault(term, 0.0);
        if(frequency != 0.0) {
            return wordFrequency.get(term) / count;
        } else {
            return 0.0;
        }
    }

}

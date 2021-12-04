package com.documentsimilarity;

import java.util.*;

/*
 *Implementation of TFIDF referenced from 
 * https://medium.com/@adityamdk/tf-idf-implementation-in-java-f6c4d1d97e3b
 * https://titanwolf.org/Network/Articles/Article?AID=4280d712-e00c-43f7-acea-6dc375b65c2d
 * */

final class TFIDF {
	private final Map<String, List<String>> papers = new HashMap<>();
    private final Map<String, double[]> hash = new HashMap<>();

    public void addDocument(String documentId, List<String> words) {
        papers.put(documentId, words);
    }

    public void tfidfCalculation() {
        IDF idf = new IDF();
        for(List<String> p : papers.values()) {
            idf.addPaper(p);
        }
        idf.weightCalculation();
        List<String> dim = idf.getPapers();
        for(Map.Entry<String, List<String>> document : papers.entrySet()) {            
            TF tf = new TF();
            tf.addWords(document.getValue());           
            double[] x = new double[dim.size()];           
            for(int i = 0; i < dim.size(); i++) {
                String t = dim.get(i);
                x[i] = tf.getFrequency(t) * idf.getValue(t);
            }
            hash.put(document.getKey(), x);
        }
    }

    public int size() {
        return papers.size();
    }

    public double[] getByID(String documentId) {
        return hash.get(documentId);
    }

    public Map<String, double[]> gethash() {
        return hash;
    }	
}

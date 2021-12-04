package com.documentsimilarity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DocumentSimilarity {

	
	private Tokenizer dsToken;
    private TFIDF tfIdf = new TFIDF();
    private Map<String, double[]> similarityValue = null;

    
    /*
     * Code referenced from:
     * http://www.java2s.com/example/java-utility-method/cosine-similarity-index-0.html
     * https://github.com/awangdev/LintCode/blob/master/Java/Cosine%20Similarity.java
     * */
    
    private double cosineSimilarity(double[] a, double[] b) {
        double mul = 0.0;
        double X = 0.0;
        double Y = 0.0;
        for (int i = 0; i < a.length; i++) {
            mul = mul + ( a[i] * b[i]);
            X = X +  Math.pow(a[i], 2);
            Y = Y +  Math.pow(b[i], 2);
        }
        return mul / (Math.sqrt(X) * Math.sqrt(Y));
    }
	
	

    public DocumentSimilarity() {
        this(new TokenizerImplementation());
    }

    public DocumentSimilarity(Tokenizer tk) {
        this.dsToken = tk;
    }

  
   
    public void addDocument(String id, String text) {
        if(similarityValue == null) {
            tfIdf.addDocument(id, dsToken.assignTokens(text));
        } else {
            throw new IllegalStateException("Research paper cannot be added now!");
        }
    }
   
    public void calculate() {
        tfIdf.tfidfCalculation();
        this.similarityValue = tfIdf.gethash();

        
        dsToken = null;
        tfIdf = null;
    }

   
    public List<String> getSimilarDocuments(String id, int limit) {
        double[] docs = similarityValue.get(id);
        if(docs == null) {
            throw new IllegalArgumentException("This Research Paper doesnot exist");
        }

        Map<String, Double> sim = new HashMap<>();

        for(Map.Entry<String, double[]> v : similarityValue.entrySet()) {
            if(!v.getKey().equals(id)) {
            	sim.put(v.getKey(), cosineSimilarity(docs, v.getValue()));
            }
        }

        List<String> similarDocs = new ArrayList<>();
        for(int i = 0; i < limit && !sim.isEmpty(); i++) {

            String idSimilar = "";
            double similarPaper = 0.0;

            for(Map.Entry<String, Double> s : sim.entrySet()) {
                if(s.getValue() >= similarPaper) {
                    idSimilar = s.getKey();
                    similarPaper = s.getValue();
                }
            }

            similarDocs.add(idSimilar);
            sim.remove(idSimilar);
        }

        return similarDocs;
    }


}

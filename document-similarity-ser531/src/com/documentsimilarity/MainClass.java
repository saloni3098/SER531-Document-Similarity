package com.documentsimilarity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;




/*Code referenced from :
 * https://www.tabnine.com/code/java/methods/org.apache.poi.xssf.usermodel.XSSFWorkbook/write
 * */


public class MainClass {

	static String serviceEndPoint = "http://localhost:3030/DocumentSimilarity/query";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass load = new MainClass();

		Scanner in = new Scanner(System.in);
		DocumentSimilarity ds = new DocumentSimilarity();
		//System.out.println("Enter the research paper to which you want to find similar papers");
		//String paper = in.nextLine();
		//System.out.println("Enter the number of research papers you want to find");
		//int papernum = in.nextInt();
			try {
			FileInputStream fp = new FileInputStream(new File("src/com/documentsimilarity/FinalData.xlsx"));		
	/*
	 * Code referenced from:
	 * https://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
	 * 
	 * */
			XSSFWorkbook workbook = new XSSFWorkbook(fp);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			int count=0;
			while (rowIterator.hasNext() && count<4250) {
				count++;
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
	
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					//System.out.println(row.getCell(0).toString());
						ds.addDocument(row.getCell(0).toString(), row.getCell(3).toString());
				}
			}
			fp.close();
		} 
		catch (IOException e) {
		}
		System.out.println("Enter your choice\n1. Search similar papers by DOI.\n2. Search similar papers by keywords.");
		int choice=in.nextInt();
		List<String> similarDocs = new ArrayList<String>();
		if(choice==1) {
			System.out.println("Enter the DOI of research paper to which you want to find similar papers");
			String paper = in.nextLine();
			System.out.println("Enter the number of research papers you want to find");	
			int papernum = in.nextInt();
			ds.calculate();
			similarDocs = ds.getSimilarDocuments(paper, papernum);
		}
		else if(choice==2) {
				System.out.println("Enter all keywords. (Comma separated) ");
				String keywordString = in.nextLine();
				ds.addDocument("user_keywords", keywordString);
				System.out.println("Enter the number of research papers you want to find");	
				int papernum = in.nextInt();
				ds.calculate();
				similarDocs = ds.getSimilarDocuments("user_keywords", papernum);
		}
		for(String s : similarDocs) {	
			String strQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX ds: <http://www.semanticweb.org/haritej/ontologies/2021/11/untitled-ontology-28#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "\n"
				+ "SELECT ?DOI ?title\n"
				+ "WHERE {\n"
				+ "  ?temp1 rdf:type ds:Data;\n"
				+ "        ds:hasTitleValue ?title;\n"
				+ "        ds:hasIDValue ?DOI;\n"
				+ "		ds:hasIDValue \""+s+"\".}";
			load.loadHumanClasses(serviceEndPoint, strQuery);
			}
		 //ds.calculate();
		 		/*List<String> sDocs = ds.getSimilarDocuments(paper, papernum);
				System.out.println("Similar Research Papers");
				int i = 0;
		 		for(String s : sDocs) {
		     		System.out.println(s);
					 i++;
		 		}	*/
	}

	private void loadHumanClasses(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
		ResultSet results = q.execSelect();
		ResultSetFormatter.out(System.out, results);
	}


}

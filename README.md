# README #

This README would normally document whatever steps are necessary to get your application up and running.

# SER531-Document-Similarity
 
The goal of this project is to find the similarity between the computer science research papers.

## DataSet

We collected the data set from kaggle.com and cleaned the data with respect to computer science research paper.

## Prerequisites

1. Maven
2. Java 8+
3. Visual Studio Code

## Installation

1. Import Maven Project
2. Add required dependencies
    1. Apache Jena
    2. Apache poi-ooxml
3. Download apache jena fuseki from this link,
   https://jena.apache.org/download/ 
   Extract the the zip file.
   
   You will find a jar file named “fuseki-server.jar”. 
   
   Run the following query 
   java -jar fuseki-server.jar --update --mem /DocumentSimilarity
   
   Go to following link, 
   http://localhost:3030 so that you can see that Fuseki is up and running.
   
   You can add .owl file over there via upload files tab and write queries in query tab.
   
4.. Build Maven project so that build should get successful without any errors.

## Running an application

Run the main.java class to run the application.

## Video Link

You can find the video demonstration of our project in the following link,

https://youtu.be/a9JpaFeUjXI

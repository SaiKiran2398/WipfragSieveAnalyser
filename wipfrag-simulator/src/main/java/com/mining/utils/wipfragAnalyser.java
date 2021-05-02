package com.mining.utils;

import java.util.Map;

public class wipfragAnalyser {
    public static void main(String[] args) throws Exception {
        String address = "D:\\Fragmentation4.jpg";

        //Sieve analysis of the image.
        Map<Integer,Double> resultSet = SieveAnalysis.analyse(address);

        //Plotting the graph.
        GraphPlotter graph = new GraphPlotter(resultSet);
        graph.process();

        //Displaying the graph.
        graph.display();
    }
}

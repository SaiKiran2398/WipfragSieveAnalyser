package com.mining.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SieveAnalysis {
    public static Map<Integer, Double> analyse(String fileAddress) throws Exception {
        //Evaluating contour areas.
        ContourAreaEvaluator evaluator = new ContourAreaEvaluator(fileAddress);
        evaluator.process();
        List<Double> contourAreas = evaluator.getContourAreas();

        //Sorting the list.
        Collections.sort(contourAreas);

        //processing the dataset.
        return processDataSet(contourAreas);
    }

    private static Map<Integer, Double> processDataSet(List<Double> contourAreas){
        Map<Integer,Double> resultSet = new HashMap<>();
        resultSet.put(0,0.0);

        List<Double> minimumSievePassingSize = contourAreas.stream()
                                                        .map(Math::sqrt)
                                                        .collect(Collectors.toList());

        int sieveSize = 1,rocksCount = 0;

        for(double rockSize : minimumSievePassingSize){
            if(sieveSize > 10)
                break;

            if(rockSize > sieveSize){
                double passingPercentage = ((double) rocksCount/minimumSievePassingSize.size()) * 100;
                resultSet.put(sieveSize,passingPercentage);
                sieveSize += 2;
            }
            rocksCount++;
        }

        sieveSize = 20;
        rocksCount = 0;

        for(double rockSize : minimumSievePassingSize){
            if(sieveSize == 100)
                break;

            if(rockSize > sieveSize){
                double passingPercentage = ((double) rocksCount/minimumSievePassingSize.size()) * 100;
                resultSet.put(sieveSize,passingPercentage);
                sieveSize += 20;
            }
            rocksCount++;
        }

        rocksCount = 0;

        for(double rockSize : minimumSievePassingSize){
            if(sieveSize > 1000)
                break;

            if(rockSize > sieveSize){
                double passingPercentage = ((double) rocksCount/minimumSievePassingSize.size()) * 100;
                resultSet.put(sieveSize,passingPercentage);
                sieveSize += 100;
            }
            rocksCount++;
        }

        return resultSet;
    }
}

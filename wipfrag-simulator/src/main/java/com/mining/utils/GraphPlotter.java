package com.mining.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class GraphPlotter {
    private DefaultCategoryDataset line_chart_dataset = null;
    private Map<Integer,Double> resultSet;

    GraphPlotter(Map<Integer,Double> resultSet){
        line_chart_dataset = new DefaultCategoryDataset();
        this.resultSet = resultSet;
    }

    public void process() throws Exception {
        TreeMap<Integer,Double> sortedResultSet = new TreeMap<>();
        sortedResultSet.putAll(resultSet);

        for(Map.Entry<Integer,Double> entry : sortedResultSet.entrySet()){
            line_chart_dataset.addValue(entry.getValue(), "Passing %", entry.getKey());
        }

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Sieve Analysis","Sieve size(in mm)",
                "Passing Percentage",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true,true,false);

        int width = 1000;    /* Width of the image */
        int height = 480;   /* Height of the image */
        File lineChart = new File( "D:\\SieveAnalysisChart3.jpeg" );
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
    }

    public void display(){
        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon("D:\\SieveAnalysisChart3.jpeg");
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

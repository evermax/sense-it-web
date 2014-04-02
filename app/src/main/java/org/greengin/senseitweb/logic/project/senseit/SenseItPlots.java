package org.greengin.senseitweb.logic.project.senseit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.greengin.senseitweb.logic.project.senseit.transformations.SenseItProcessedSeriesVariable;
import org.greengin.senseitweb.utils.TimeValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class SenseItPlots {

    static final Font titleFont = new Font("SansSerif", Font.BOLD, 12);
    static final Font labelFont = new Font("SansSerif", Font.PLAIN, 10);

    public static byte[] createPlot(SenseItProcessedSeriesVariable data) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        StringBuffer units = new StringBuffer();
        String title = "";

        if (data != null) {
            title = data.name;
            if (data.values != null && data.values.size() > 0) {
                int nv = data.values.firstElement().v.length;
                long t0 = data.values.firstElement().t;

                for (int i = 0; i < nv; i++) {
                    XYSeries series = new XYSeries(" " + (i + 1));
                    for (TimeValue v : data.values) {
                        series.add(v.t - t0, v.v[i]);
                    }
                    dataset.addSeries(series);
                }


                for (Entry<String, Integer> entry : data.units.entrySet()) {
                    if (entry.getValue() > 0) {
                        units.append(entry.getKey());
                        if (entry.getValue() > 1) {
                            units.append(entry.getValue()).append(" ");
                        }
                    }
                }
                boolean first = true;
                for (Entry<String, Integer> entry : data.units.entrySet()) {
                    if (entry.getValue() < 0) {
                        if (first) {
                            units.append("/");
                            first = false;
                        }
                        units.append(entry.getKey());
                        if (entry.getValue() < -1) {
                            units.append(-entry.getValue()).append(" ");
                        }
                    }
                }
            }
        }

        JFreeChart chart = ChartFactory.createXYLineChart(title, "Time", units.toString(), dataset, PlotOrientation.VERTICAL, false, false, false);
        chart.getTitle().setFont(titleFont);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getDomainAxis().setLabelFont(labelFont);
        plot.getRangeAxis().setLabelFont(labelFont);

        plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));

        Color gridLinesColor = new Color(200, 200, 200);
        Stroke gridLinesStroke = new BasicStroke(1f);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(gridLinesColor);
        plot.setDomainGridlineStroke(gridLinesStroke);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(gridLinesColor);
        plot.setRangeGridlineStroke(gridLinesStroke);

        plot.setBackgroundAlpha(.0f);

        chart.setPadding(new RectangleInsets(0, 0, 0, 0));

        BufferedImage objBufferedImage = chart.createBufferedImage(360, 240);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
            byte[] imageData = bas.toByteArray();
            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

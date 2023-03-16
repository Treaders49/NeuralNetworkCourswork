

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class LineGraph extends JPanel {
    
    private double[] trainData;
    private double[] validationData;
    private String xAxisLabel;
    private String yAxisLabel;
    
    
    
    public LineGraph(double[] trainErrorData, double[] validationErrorData, String xAxisLabel, String yAxisLabel) {
        this.trainData = trainErrorData;
        this.validationData = validationErrorData;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int padding = 70;
        double maxValue = 0;
        double minValue = 0;
        for (int i = 0; i < validationData.length; i++) {
            if (trainData[i] > maxValue) {
                maxValue = validationData[i];
            }
            if (trainData[i] < minValue) {
                minValue = validationData[i];
            }
        }
        double scale = (double) (height - 2 * padding) / (maxValue - minValue);
        
        // draw x and y axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, height - padding, padding, padding);
        g2d.drawLine(padding, height - padding, width - padding, height - padding);
        
        // draw x and y axis labels
        FontMetrics fm = g2d.getFontMetrics();
        int xAxisLabelWidth = fm.stringWidth(xAxisLabel);
        g2d.drawString(xAxisLabel, width/2 - xAxisLabelWidth/2, height - padding/2);
        int yAxisLabelWidth = fm.stringWidth(yAxisLabel);
        g2d.rotate(-Math.PI / 2);
        g2d.drawString(yAxisLabel, -height/2 - yAxisLabelWidth/2, padding/2);
        g2d.rotate(Math.PI / 2);
        
        // draw x and y axis tick marks and labels
        int xTickSpacing = (width - 2 * padding) / (trainData.length);
        int yTickSpacing = (height - 2 * padding) / 10;
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < trainData.length; i++) {
            int x = padding + i * xTickSpacing;
            g2d.drawLine(x, height - padding, x, padding);
            if (((i+1) % 20 == 0) || (i == 0)) {
            	g2d.drawString(Integer.toString(i+1), x - fm.stringWidth(Integer.toString(i+1))/2, height - padding/2);
            }
        }
        for (int i = 0; i <= 10; i++) {
            int y = height - padding - i * yTickSpacing;
            g2d.drawLine(padding, y, width - padding, y);
            if (BigDecimal.valueOf(i * (maxValue - minValue) / 10).toPlainString().length() < 7) {
            	continue;
            }
            g2d.drawString((BigDecimal.valueOf(i * (maxValue - minValue) / 10).toPlainString()).substring(0,6), padding/2 - 30, y + fm.getHeight()/2);
        }
        g2d.setColor(Color.BLUE);
        int x0 = padding;
        int y0 = height - padding - (int) ((trainData[0] - minValue) * scale);
        for (int i = 1; i < trainData.length; i++) {
            int x1 = padding + i * xTickSpacing;
            int y1 = height - padding - (int) ((trainData[i] - minValue) * scale);
            g2d.drawLine(x0, y0, x1, y1);
            g2d.fillOval(x1 - 2, y1 - 2, 4, 4);
            x0 = x1;
            y0 = y1;
        }
        
       g2d.setColor(Color.GREEN);
        x0 = padding;
        y0 = height - padding - (int) ((validationData[0] - minValue) * scale);
        for (int i = 1; i < validationData.length; i++) {
            int x1 = padding + i * xTickSpacing;
            int y1 = height - padding - (int) ((validationData[i] - minValue) * scale);
            g2d.drawLine(x0, y0, x1, y1);
            g2d.fillOval(x1 - 2, y1 - 2, 4, 4);
            x0 = x1;
            y0 = y1; 
        }
    }
}
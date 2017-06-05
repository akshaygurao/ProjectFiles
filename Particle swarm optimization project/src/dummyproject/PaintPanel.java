/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummyproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author aksha
 */
public class PaintPanel extends JPanel {

    private Process process;
    private double minX, maxX, minY, maxY;
    

    public PaintPanel() {
        super();
        setBackground(Color.LIGHT_GRAY);
        process = new Process();
        process.initializeSwarm();
        process.initializeVictims();
        process.execute();
        minX = Process.LOC_X_LOW;
        maxX = Process.LOC_X_HIGH;
        minY = Process.LOC_Y_LOW;
        maxY = Process.LOC_Y_HIGH;
    }

    
    private Point formattedPoint(double x, double y) {

        double normX = (x - (minX + maxX) / 2) / ((maxX - minX) / 2);
        double normY = (y - (minY + maxY) / 2) / ((maxY - minY) / 2);
        return new Point(getWidth() / 2 + (int) (getWidth() / 2 * normX), getHeight() / 2 - ((int) (getHeight() / 2 * normY)));
    }

    
    private void drawGrid(Graphics g) {
        Point point1, point2;

        for (int i = (int) minX; i <= maxX; i++) {
            point1 = formattedPoint(i, minY);
            point2 = formattedPoint(i, maxY);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
            g.drawString(String.valueOf(i), point1.x, (point1.y + point2.y) / 2);
        }

        for (int i = (int) minY; i <= maxY; i++) {
            point1 = formattedPoint(minX, i);
            point2 = formattedPoint(maxX, i);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
            g.drawString(String.valueOf(i), (point1.x + point2.x) / 2, point1.y);
        }
    }

    public void drawCircle(Graphics g, int x, int y, int size) {
        g.drawOval(x - size / 2, y - size / 2, size, size);
    }

    public void fillCircle(Graphics g, int x, int y, int size) {
        g.fillOval(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point point;

        g.setColor(Color.GRAY);

        drawGrid(g);

        g.setColor(Color.BLUE);

        for (Bots b : process.getSwarm()) {
            point = formattedPoint(b.getLocation().getCurrentLocation()[0], b.getLocation().getCurrentLocation()[1]);
            fillCircle(g, point.x, point.y, 4);
        }
        g.setColor(Color.RED);
        
        for(Victims v : process.getVictimSwarm()) {
          point = formattedPoint(v.getLocation().getCurrentLocation()[0], v.getLocation().getCurrentLocation()[1]);
            fillCircle(g, point.x, point.y, 4);  
        }
    }
}

package Demo;
import Demo.Tree.Node;
import Demo.Tree.TreeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.InterruptedException;
import java.util.ArrayList;

public class AlgoVisHelper {

    private AlgoVisHelper(){}

    public static final Color Red = new Color(0xF44336);
    public static final Color Indigo = new Color(0x3F51B5);
    public static final Color Blue = new Color(0x2196F3);
    public static final Color LightBlue = new Color(0x03A9F4);
    public static final Color Green = new Color(0x4CAF50);
    public static final Color Yellow = new Color(0xFFEB3B);
    public static final Color Grey = new Color(0x9E9E9E);
    public static final Color Black = new Color(0x000000);
    public static final Color White = new Color(0xFFFFFF);

    public static void strokeCircle(Graphics2D g, int x, int y, int r){

        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g.draw(circle);
    }

    public static void fillCircle(Graphics2D g, int x, int y, int r){

        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g.fill(circle);
    }

    public static void strokeRectangle(Graphics2D g, int x, int y, int w, int h){

        Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
        g.draw(rectangle);
    }

    public static void fillRectangle(Graphics2D g, int x, int y, int w, int h){

        Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
        g.fill(rectangle);
    }

    public static void setColor(Graphics2D g, Color color){
        g.setColor(color);
    }

    public static void setStrokeWidth(Graphics2D g, int w){
        int strokeWidth = w;
        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    public static void putImage(Graphics2D g, int x, int y, String imageURL){

        ImageIcon icon = new ImageIcon(imageURL);
        Image image = icon.getImage();

        g.drawImage(image, x, y, null);
    }

    public static void drawText(Graphics2D g, String text, int centerx, int centery){

        if(text == null)
            throw new IllegalArgumentException("Text is null in drawText function!");

        FontMetrics metrics = g.getFontMetrics();
        int w = metrics.stringWidth(text);
        int h = metrics.getDescent();
        g.drawString(text, centerx - w/2, centery + 2*h);
    }

    public static void drawTextFromFile(Graphics2D g, String fileName, Data data, int x, int y, int w) {
        setColor(g, Black);
        FontMetrics metrics = g.getFontMetrics();
        int h =  metrics.getHeight();
        BufferedReader reader;
        try {

            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            int i = 1;
            while(line != null) {
                if(i == data.curLine){
                    setColor(g, Yellow);
                    fillRectangle(g, x,y + (h+5) * (i-1) - h, w, h + metrics.getDescent() );
                }
                setColor(g, Black);
                g.drawString(line, x, y + (h+5) * (i++ - 1));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawLine(Graphics2D g, double sx, double sy, double fx, double fy) {
        Line2D l = new Line2D.Double(sx, sy, fx, fy);
        g.draw(l);
    }

    public static void drawTree(Graphics2D g, TreeFrame frame, ArrayList<Node> set, Node node, int x, int y, int r, double degree) {

        setColor(g, AlgoVisHelper.Black);
        if(node.cur) {
            setColor(g, AlgoVisHelper.Green);
            setStrokeWidth(g, 3);
        }
        if(node.searched) {
            setColor(g, AlgoVisHelper.Red);
            setStrokeWidth(g, 3);
        }
        if(!node.isBalance) {
            setColor(g, AlgoVisHelper.Blue);
            setStrokeWidth(g, 3);
        }
        strokeCircle(g, x, y, r);
        setStrokeWidth(g, 1);
        drawText(g, String.valueOf(node.value), x, y);
        if(frame.type.equals("avl"))
            drawText(g, String.valueOf(node.height()), x, y-2*r);
        int ny = (int) (y + 6 * r * Math.cos(degree));
        if (node.left != null) {
            int lx = (int) (x - 6 * r * Math.sin(degree));
            if (lx >= r && ny <= frame.getCanvasHeight() - r) {
                setColor(g, AlgoVisHelper.Black); //line is always black
                for(Node other : set) {
                    while ((other.x - lx) * (other.x - lx) + (other.y - ny) * (other.y - ny) <= 4 * r * r) {
                        if(other.value > node.value)
                            lx -= 5;
                        else
                            lx += 5;
                    }
                }
                set.add(node);
                node.x = lx;
                node.y = ny;
                drawLine(g, x, y + r, lx, ny - r);
                drawTree(g, frame, set, node.left, lx, ny, r, degree / 2);
            } else {
                frame.getMsg().setText("Node out of canvas");
            }
        }
        if (node.right != null) {
            int rx = (int) (x + 6 * r * Math.sin(degree));
            if (rx + r <= 2 * frame.getCanvasWidth() / 3 && ny + r <= frame.getCanvasHeight()) {
                setColor(g, AlgoVisHelper.Black);
                for(Node other : set) {
                    while ((other.x - rx) * (other.x - rx) + (other.y - ny) * (other.y - ny) <= 4 * r * r) {
                        if(other.value > node.value)
                            rx -= 5;
                        else
                            rx += 5;
                    }
                }
                set.add(node);
                node.x = rx;
                node.y = ny;
                drawLine(g, x, y + r, rx, ny - r);
                drawTree(g, frame, set, node.right, rx, ny, r, degree / 2);
                // System.out.println(node.right.value);
            } else {
                frame.getMsg().setText("Node out of canvas");
            }
        }
    }

    public static int validQuantity(SortFrame frame, String s) {
        try {
            int quantity = Integer.valueOf(s);
            if(quantity > 0 && quantity <= (frame.getCanvasWidth() * 2/3) / 21)
                return quantity;
            else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}

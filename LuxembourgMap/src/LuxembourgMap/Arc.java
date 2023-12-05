package Tema7;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Arc {
    private int startNodId;
    private int finishNodeId;
    private int length;


    private boolean isInSolution;

    Arc(int startNode , int finishNode , int length){
        this.startNodId=startNode;
        this.finishNodeId=finishNode;
        this.length=length;
        this.isInSolution=false;
    }

    public int getStartNode() {
        return startNodId;
    }

    public void setStartNodId(int startNode) {
        this.startNodId = startNode;
    }

    public int getFinishNode() {
        return finishNodeId;
    }

    public void setFinishNodeId(int finishNode) {
        this.finishNodeId = finishNode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void drawArc(Graphics g , Nod startNode , Nod finishNode) {
        if(!isInSolution)
        {
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(0));
        }
        else
        {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.draw(new Line2D.Double(startNode.getX(), startNode.getY(),finishNode.getX() ,finishNode.getY()));
    }

    public boolean isInSolution() {
        return isInSolution;
    }

    public void setInSolution(boolean isInSolution) {
        this.isInSolution = isInSolution;
    }
}

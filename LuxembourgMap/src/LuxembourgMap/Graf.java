package Tema7;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Graf extends JPanel {
    private Vector<Nod> vectorNoduri;
    private Vector<Arc>[] vectorArce;

    private Map<Integer, List<Arc>> mapArce = new HashMap<>();
    private Map<Integer, Nod> mapNoduri = new HashMap<>();

    private Nod nodStart;
    private Nod nodFinal;

    private static int numberOfClicks = 0;

    private static final double latime = 760;
    private static final double inaltime = 760;

    private static boolean citesteDinNou = false;

    public Graf() {
        readXMLFile();
        setXYForNodes();
        repaint();

        addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(citesteDinNou)
                {
                    readXMLFile();
                    setXYForNodes();
                    citesteDinNou = false;
                }

                if (numberOfClicks % 2 == 0)
                {
                    nodStart = getNodeInAreaClicked(e.getX() , e.getY());
                    ++numberOfClicks;

                }
                else
                {
                    nodFinal = getNodeInAreaClicked(e.getX() , e.getY());
                    ++numberOfClicks;

                    long startTime = System.currentTimeMillis();
                    dijkstraAlgorithm(nodStart.getIdNode());

                    List<Integer> nodeIdList = getShortestPathTo(nodFinal.getIdNode());

                    nodeIdList.add(nodFinal.getIdNode());

                    Integer firstNodeId = nodStart.getIdNode();
                    Integer secondNodeId;
                    while (!nodeIdList.isEmpty()) {
                        secondNodeId = nodeIdList.get(0);
                        nodeIdList.remove(0);

                        List<Arc> curentList = mapArce.get(firstNodeId);

                        for (Arc currentArc : curentList) {
                            if (currentArc.getFinishNode() == secondNodeId)
                                currentArc.setInSolution(true);
                        }

                        firstNodeId = secondNodeId;
                    }
                    citesteDinNou = true;
                    repaint();
                }repaint();
            }
        });

    }

    protected Nod getNodeInAreaClicked(int x, int y) {
        double minimDistance = Double.MAX_VALUE;
        int minimNodeIndex = -1;
        for(int index = 0; index < mapNoduri.size(); ++index) {
            double distanceBetweenTwoPoints = Math.sqrt(Math.pow(mapNoduri.get(index).getX() - x ,2) + Math.pow(mapNoduri.get(index).getY() - y ,2));
            if(distanceBetweenTwoPoints < minimDistance) {
                minimNodeIndex = index;
                minimDistance = distanceBetweenTwoPoints;
            }
        }
        return mapNoduri.get(minimNodeIndex);
    }

    private void readXMLFile() {
        try {
            File fXmlFile = new File(
                    "D:\\IAUnitBv\\ANUL 2\\SEMESTRUL 1\\Algoritmica grafurilor\\Teme\\TemeLaborator\\Tema7-AnghelusCrina-Cosmina10LF301\\Harta_Luxemburg.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("node");

            vectorNoduri = new Vector<>(nodeList.getLength());

            int dimension = nodeList.getLength();
            for (int index = 0; index < dimension; index++) {

                Node nNode = nodeList.item(index);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    vectorNoduri.add(new Nod(Integer.parseInt(eElement.getAttribute("id")),
                            Integer.parseInt(eElement.getAttribute("latitude")),
                            Integer.parseInt(eElement.getAttribute("longitude"))));

                    mapNoduri.put(index,new Nod(Integer.parseInt(eElement.getAttribute("id")),
                            Integer.parseInt(eElement.getAttribute("latitude")),
                            Integer.parseInt(eElement.getAttribute("longitude"))));

                }
            }

            vectorArce = new Vector[nodeList.getLength()];

            NodeList arcList = doc.getElementsByTagName("arc");

            Node nNode;
            Element eElement;
            Arc newArc;
            int arcDimension = arcList.getLength();

            for (int index = 0; index < arcDimension; index++) {
                nNode = arcList.item(index);

                if (index < dimension)
                    vectorArce[index] = new Vector<>();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    newArc = new Arc(Integer.parseInt(eElement.getAttribute("from")),
                            Integer.parseInt(eElement.getAttribute("to")),
                            Integer.parseInt(eElement.getAttribute("length")));

                    vectorArce[Integer.parseInt(eElement.getAttribute("from"))].addElement(newArc);

                }
            }

            for (int index = 0; index < dimension; index++) {
                List<Arc> currentList = new ArrayList<>();
                currentList = vectorArce[index];

                vectorNoduri.elementAt(index).adjacencies = currentList;
                mapArce.put(index, currentList);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dijkstraAlgorithm(Integer startNodeId) {
        Nod sourceNode = vectorNoduri.get(startNodeId);
        sourceNode.minDistance =0.;

        PriorityQueue<Nod> nodeQueue = new PriorityQueue<Nod>();
        nodeQueue.add(sourceNode);

        while (!nodeQueue.isEmpty()) {
            Nod minimNode = nodeQueue.poll();


            for (Arc arc : minimNode.adjacencies)
            {
                Nod targetNode = vectorNoduri.get(arc.getFinishNode());
                double weight = arc.getLength();
                double distanceThrough = minimNode.minDistance + weight;
                if (distanceThrough < targetNode.minDistance) {

                    targetNode.minDistance = distanceThrough ;
                    targetNode.previousNodeId = minimNode.getIdNode();

                    nodeQueue.remove(targetNode);
                    nodeQueue.add(targetNode);

                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int index = 0; index < mapArce.size(); ++index) {

            List<Arc> currentArcList = mapArce.get(index);

            for (Arc arc : currentArcList) {
                int startNodeId = arc.getStartNode();
                Nod startNode = mapNoduri.get(startNodeId);

                int finishNodeId = arc.getFinishNode();
                Nod finishNode = mapNoduri.get(finishNodeId);

                arc.drawArc(g, startNode, finishNode);
            }

        }
    }

    private void setXYForNodes() {

        for (Nod currentNode : vectorNoduri) {
        	currentNode.setX((latime*(currentNode.getLongitude()-573929))/78756);
        	currentNode.setY((inaltime*(5018275-currentNode.getLatitude()))/73246);
        }

        for (int index = 0; index < mapNoduri.size(); ++index) {
            Nod currentNode = mapNoduri.get(index);
            currentNode.setX((latime*(currentNode.getLongitude()-573929))/78756);
        	currentNode.setY((inaltime*(5018275-currentNode.getLatitude()))/73246);
        }
    }


    public  List<Integer> getShortestPathTo(int target)
    {
        List<Integer> path = new ArrayList<Integer>();
        for (Nod vertex = vectorNoduri.get(target); vertex.previousNodeId != -1; vertex = vectorNoduri.get( vertex.previousNodeId)) {
            path.add(vertex.getIdNode());
        }

        Collections.reverse(path);
        return path;
    }
}

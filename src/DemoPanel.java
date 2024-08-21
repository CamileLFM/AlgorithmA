

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class DemoPanel extends JPanel {
    
    
    final int maxCol = 15;
    final int maxRow = 10;
    final int screenWidth = 80 * maxCol;
    final int screenHeight = 50 * maxRow;

    Node[][] node = new Node[maxCol] [maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;

    int step = 0;
    
    public DemoPanel(){
        this.setPreferredSize(new DimensionUIResource(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        setStartNode(2, 2);
        setGoalNode(12, 8);

        setSolidNode(11, 2);
        setSolidNode(11, 3);
        setSolidNode(11, 4);
        setSolidNode(11, 5);
        setSolidNode(10, 5);
        setSolidNode(10, 6);
        setSolidNode(9, 6);
        setSolidNode(9, 7);
        setSolidNode(9, 8);

        setCostOnNodes();
    }

    private void  setStartNode (int col, int row){
        node[col][row].setAsStart();
        startNode = node [col][row];
        currentNode = startNode;
    }

    private void  setGoalNode (int col, int row){
        node [col][row].setGoal();
        goalNode = node[col][row];
    }

    private void  setSolidNode (int col, int row){
        node [col][row].setAsSolid();
    }

    private void setCostOnNodes(){
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {

            getCost(node [col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node){
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.costStart = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.costGoal = xDistance + yDistance;

        node.costTotal = node.costStart + node.costGoal;

        if (!node.equals(startNode) && !node.equals(goalNode)) {
            node.setText(String.valueOf(node.costStart)+ " + " + String.valueOf(node.costStart));
        }

    }

    public void search () {
        if (!goalReached && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if(row - 1 >= 0) openNode(node[col][row - 1]);
            if(row + 1 < maxRow) openNode(node[col][row + 1]);
            if(col + 1 < maxCol) openNode(node[col + 1][row]);
            if(col - 1 >= 0) openNode(node[col - 1][row]);

            int bestNodeIndex = 0;
            int bestNodeCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {

                if (openList.get(i).costTotal < bestNodeCost) {
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).costTotal;
                } else if (openList.get(i).costTotal == bestNodeCost) {
                    if (openList.get(i).costStart < openList.get(bestNodeIndex).costStart) bestNodeIndex = i;
                } 

            }
            
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

            step++;
        }
    }

    private void openNode (Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void autoSearch () {
        while (!goalReached && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if(row - 1 >= 0) openNode(node[col][row - 1]);
            if(row + 1 < maxRow) openNode(node[col][row + 1]);
            if(col + 1 < maxCol) openNode(node[col + 1][row]);
            if(col - 1 >= 0) openNode(node[col - 1][row]);

            int bestNodeIndex = 0;
            int bestNodeCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {

                if (openList.get(i).costTotal < bestNodeCost) {
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).costTotal;
                } else if (openList.get(i).costTotal == bestNodeCost) {
                    if (openList.get(i).costStart < openList.get(bestNodeIndex).costStart) bestNodeIndex = i;
                } 

            }
            
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

        }
    }

    private void trackThePath () {
        Node current = goalNode;

        while (!startNode.equals(current)) {
            current = current.parent;

            if (!startNode.equals(current)) {
                current.setAsPath();
            }
        }
    }
}

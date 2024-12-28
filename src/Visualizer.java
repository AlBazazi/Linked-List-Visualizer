import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class Visualizer extends JFrame implements ActionListener {
    LinkedList<Node> list;
    static JPanel panel;
    private JButton addButton, removeButton, searchButton, sortButton, traverseButton;
    public static HashSet<Integer> addresses;
    static Random rand;
    static int startLink;

    // CONSTRUCTOR
    public Visualizer() {
        // Initialization and Frame Setting
        list = new LinkedList<>();
        addresses = new HashSet<>();
        rand = new Random();
        setTitle("Linked List Visualizer");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Make START Node Address
        startLink = generateAddress();
        addresses.add(startLink);

        // Make Buttons
        addButton = new JButton("Add Node");
        removeButton = new JButton("Remove Node");
        searchButton = new JButton("Search Element");
        sortButton = new JButton("Sort List");
        traverseButton=new JButton("Traverse List");

        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setSize(new Dimension(125, 40));

        removeButton.addActionListener(this);
        removeButton.setFocusable(false);
        removeButton.setSize(new Dimension(125, 40));
        
        searchButton.addActionListener(this);
        searchButton.setFocusable(false);
        searchButton.setSize(new Dimension(125, 40));
        
        sortButton.addActionListener(this);
        sortButton.setFocusable(false);
        sortButton.setSize(new Dimension(125, 40));
                
        traverseButton.addActionListener(this);
        traverseButton.setFocusable(false);
        traverseButton.setSize(new Dimension(125, 40));
        
        // Drawing Panel
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawList(g);
            }
        };
        
        // Button Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setPreferredSize(new Dimension(150, 125));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        
        inputPanel.add(traverseButton);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(searchButton);
        inputPanel.add(sortButton);
       
        // Add Panels
        add(inputPanel, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }

    // DRAW LIST ON SCREEN
    private void drawList(Graphics g) {
        int x = 50;
        int y = 100;
        int nodeWidth = 80;
        int nodeHeight = 60;

        // Draw start node
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, nodeWidth, nodeHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, nodeWidth, nodeHeight);
        g.drawString("START", x + 10, y + 20);
        g.drawString("LINK: " + (list.isEmpty() ? "NULL" : startLink), x + 10, y + 40);
        x += nodeWidth + 40;
        
        // Draw linked list nodes
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            g.setColor(node.highlighted ? Color.YELLOW : Color.LIGHT_GRAY); // Highlight if flagged
            g.fillRect(x, y, nodeWidth, nodeHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, nodeWidth, nodeHeight);
            g.drawString("INFO: " + node.info, x + 10, y + 20);
            g.drawString("LINK: " + (i < list.size() - 1 ? node.nextLink : "NULL"), x + 10, y + 40);

            // Draw arrow to next node
            if (i < list.size() - 1) {
                g.drawLine(x + nodeWidth, y + nodeHeight / 2, x + nodeWidth + 15, y + nodeHeight / 2);
                g.drawLine(x + nodeWidth + 15, y + nodeHeight / 2, x + nodeWidth + 15, y + nodeHeight / 2 - 5);
                g.drawLine(x + nodeWidth + 15, y + nodeHeight / 2, x + nodeWidth + 15, y + nodeHeight / 2 + 5);
            }
            x += nodeWidth + 40;
        }

        // Draw arrow from START to first node if list has nodes
        if (!list.isEmpty()) {
            g.drawLine(50 + nodeWidth, 130, 50 + nodeWidth + 20, 130);
            g.drawLine(50 + nodeWidth + 20, 130, 50 + nodeWidth + 20, 125);
            g.drawLine(50 + nodeWidth + 20, 130, 50 + nodeWidth + 20, 135);
        }
    }
    // ACTIONS PERFORMED BY BUTTONS
    @Override
    public void actionPerformed(ActionEvent e) {
    	// TRAVERSE
    	if(e.getSource()==traverseButton) {
    		Operations.traverseList(list);
    	}    	
    	// ADD
        if (e.getSource()==addButton) {
            list=Operations.addElement(list);
            panel.repaint();
        }        
        // REMOVE
        if(e.getSource()==removeButton) {
        	panel.repaint();
        }        
        // SEARCH
        if(e.getSource()==searchButton) {
        	String strKey = JOptionPane.showInputDialog("Enter INFO to Search");
            int key = Integer.parseInt(strKey);
            int result=-1; // DEFAULT "NOT FOUND"
        	for(int i=0;i<list.size();i++) {
        		if(list.get(i).info==key) result=i;
        	}
        	if(result==-1) {
        		JOptionPane.showMessageDialog(this, "Element NOT FOUND in List");
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Element FOUND at Index "+result);
        	}
        }        
        // SORT
        if(e.getSource()==sortButton) {
        	list=Operations.sortList(list);
        	panel.repaint();        	
        }
    }
    public static int generateAddress() {
        int address;
        do {
            address = rand.nextInt(100) + 1;
        } while (addresses.contains(address));
        addresses.add(address);
        return address;
    }
}
class Node {
    boolean highlighted;
	int info;
    int link;
    int nextLink;
    Node(int value, int address) {
        info = value;
        link = address;
        nextLink = -1;
        highlighted=false;
    }
    void setNext(int a) {
        nextLink = a;
    }
}

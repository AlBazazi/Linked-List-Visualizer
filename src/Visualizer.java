import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
@SuppressWarnings("serial")
public class Visualizer extends JFrame implements ActionListener {
    LinkedList<Node> linkedList;
    JPanel panel;
    private JButton addButton, removeButton, searchButton;
    HashSet<Integer> addresses;
    Random random;
    int startAddress;

    public Visualizer() {
    	// Initialization and Frame Setting
        linkedList = new LinkedList<>();
        addresses = new HashSet<>();
        random = new Random();
        setTitle("Linked List Visualizer");
        setSize(800,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Make START Node Address
        startAddress=generateAddress();
        addresses.add(startAddress);

        // Make Buttons
        addButton=new JButton("Insert Node");
        removeButton=new JButton("Remove Node");
        searchButton=new JButton("Search Element");

        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setSize(new Dimension(125, 40));

        removeButton.addActionListener(this);
        removeButton.setFocusable(false);
        removeButton.setSize(new Dimension(125, 40));
        
        searchButton.addActionListener(this);
        searchButton.setFocusable(false);
        searchButton.setSize(new Dimension(125,40));
        
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
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        inputPanel.setPreferredSize(new Dimension(150,125));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(searchButton);
       
        // Add Panels
        add(inputPanel, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }
    void drawList(Graphics g) {
        int x=50;
        int y=100;
        int nodeWidth=80;
        int nodeHeight=60;
        // Draw start node
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, nodeWidth, nodeHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, nodeWidth, nodeHeight);
        g.drawString("START",x+10,y+20);
        if (!linkedList.isEmpty()) {
        	g.drawString("LINK: " + startAddress,x+10,y+40);
        } else {
        	g.drawString("LINK: NULL",x+10,y+40);
        }
        x+=nodeWidth+40;
        
        // Draw linked list nodes
        for (int i = 0; i < linkedList.size(); i++) {
            Node node = linkedList.get(i);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, nodeWidth, nodeHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, nodeWidth, nodeHeight);
            // Display info and link
            g.drawString("INFO: " + String.valueOf(node.value),x+10,y+20);
            // Change last node's link display to NULL
            g.drawString("LINK: " + (i<linkedList.size()-1 ? node.address:"NULL"),x+10,y+40);

            // Draw arrow to next node
            if (i<linkedList.size()-1) {
                g.drawLine(x+nodeWidth,y+nodeHeight/2,x+nodeWidth+15,y+nodeHeight/2);
                g.drawLine(x+nodeWidth+15,y+nodeHeight/2,x+nodeWidth+15,y+nodeHeight/2-5);
                g.drawLine(x+nodeWidth+15,y+nodeHeight/2,x+nodeWidth+15,y+nodeHeight/2+5);
            }
            x += nodeWidth+40;
        }
        // Draw arrow from START to first node if list has nodes
        if (!linkedList.isEmpty()) {
            g.drawLine(50+nodeWidth,130,50+nodeWidth+20,130);
            g.drawLine(50+nodeWidth+20,130,50+nodeWidth+20,125);
            g.drawLine(50+nodeWidth+20,130,50+nodeWidth+20,135);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==addButton) {
        	// Function for adding an element in either first index, last, or any other.
        }
        if (e.getSource()==removeButton) {
        	// Function for removing any element in either first index, last, or any other.
        }
        if (e.getSource()==searchButton) {
        	// Function which searches for an element, then returns true or false (boolean return).
        }
    }
    private int generateAddress() {
        int address;
        do {
            address = random.nextInt(100) + 1;
        } while (addresses.contains(address));
        addresses.add(address);
        return address;
    }
    private static class Node {
        int value;
        int address;
        Node(int value, int address) {
            this.value = value;
            this.address = address;
        }
    }
}
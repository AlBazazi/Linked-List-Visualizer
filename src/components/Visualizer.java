package components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.*;
import models.Node;
import utils.Operations;
public class Visualizer extends JFrame implements ActionListener {

    public static LinkedList<Node> list;
    static LinkedList<Integer> availList;
    public static JPanel drawingPanel;
    private JButton[] buttons;
    public static HashSet<Integer> addresses;
    public static int startLink;
    private static JTextArea availTextArea;

    public Visualizer() {
        // Initialization and Frame Setting
        list = new LinkedList<>();
        availList=new LinkedList<>();
        availList=getAvailList();
        addresses = new HashSet<>();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Linked List Visualizer");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Buttons
        String[] labels = {"Traverse", "Insert", "Remove", "Search", "Sort","Reset","Close"};
        buttons = new JButton[labels.length];
        Color blueColor = new Color(10, 102, 204); // blue accent
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(labels[i]);
            JButton btn = buttons[i];
            btn.addActionListener(this);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(140, 42));
            btn.setBackground(blueColor);
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 1),
                    BorderFactory.createLineBorder(blueColor.darker(), 1, true)));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btn.setBackground(blueColor.darker());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btn.setBackground(blueColor);
                }
            });
        }

        // Drawing Panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawList(g);
            }
        };
        drawingPanel.setOpaque(true);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setPreferredSize(new Dimension(180, 0));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(20, 16, 20, 16)));
        inputPanel.setBackground(new Color(220, 235, 255));
        inputPanel.setOpaque(true);

        inputPanel.add(Box.createVerticalGlue());
        for (JButton button : buttons) {
            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            wrap.setOpaque(false);
            wrap.add(button);
            inputPanel.add(wrap);
            inputPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }
        inputPanel.add(Box.createVerticalGlue());

        // JTextArea for Avail
        availTextArea = new JTextArea(4, 40);
        availTextArea.setEditable(false);
        availTextArea.setLineWrap(true);
        availTextArea.setWrapStyleWord(true);
        availTextArea.setBackground(new Color(235, 245, 255));
        availTextArea.setForeground(new Color(6, 45, 91));
        availTextArea.setFont(new Font("Segoe UI", Font.BOLD, 18));
        availTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateAvailList();

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(220, 235, 255));
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        JLabel titleLabel = new JLabel("Linked List Visualizer");
        titleLabel.setForeground(new Color(6, 45, 91));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        topBar.add(titleLabel, BorderLayout.WEST);
        
        add(topBar, BorderLayout.NORTH);

        add(inputPanel, BorderLayout.WEST);

        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(drawingPanel, BorderLayout.CENTER);

        JScrollPane availScroll = new JScrollPane(availTextArea);
        availScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        availScroll.getViewport().setBackground(new Color(235, 245, 255));
        add(availScroll, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private static void updateAvailList() {   	
        StringBuilder sb = new StringBuilder("Avail List: [ ");
        for (int i = 0; i < availList.size(); i++) {
            sb.append(availList.get(i));
            if (i < availList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" ]");
        availTextArea.setText(sb.toString());
    }

    private static LinkedList<Integer> getAvailList() {
        LinkedList<Integer> avail = new LinkedList<>();
        for (int i = 1; i <= 100; i++) {
            avail.add(i);
        }
        try {
        	avail.removeIf(addresses::contains);
        } catch (Exception e) {}
        return avail;
    }

    private void drawList(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int padding = 40;
        int x = padding;
        int y = 80;
        int nodeWidth = 120;
        int nodeHeight = 70;

        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, new Color(225, 240, 255), 0, h, new Color(245, 250, 255));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(210, 225, 245));
        g2.fillRoundRect(x, y, nodeWidth, nodeHeight, 14, 14);
        g2.setColor(new Color(150, 180, 220));
        g2.drawRoundRect(x, y, nodeWidth, nodeHeight, 14, 14);
        g2.setColor(new Color(6, 45, 91));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.drawString("START", x + 12, y + 26);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2.drawString("LINK: " + (list.isEmpty() ? "NULL" : startLink), x + 12, y + 46);
        x += nodeWidth + 50;

        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            Color nodeBg = node.highlighted ? new Color(255, 235, 140) : new Color(200, 220, 245);
            Color nodeBorder = node.highlighted ? new Color(210, 160, 20) : new Color(120, 150, 190);

            g2.setColor(nodeBg);
            g2.fill(new RoundRectangle2D.Double(x, y, nodeWidth, nodeHeight, 12, 12));
            g2.setColor(nodeBorder);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Double(x, y, nodeWidth, nodeHeight, 12, 12));

            g2.setColor(node.highlighted ? Color.BLACK : new Color(6, 45, 91));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString("INFO: " + node.info, x + 12, y + 28);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.drawString("LINK: " + (i < list.size() - 1 ? node.nextLink : "NULL"), x + 12, y + 48);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.drawString("ADDR: " + node.link, x + 6, y - 6);

            if (i < list.size() - 1) {
                int x1 = x + nodeWidth;
                int yMid = y + nodeHeight / 2;
                int x2 = x + nodeWidth + 25;
                g2.setColor(new Color(100, 125, 160));
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(x1, yMid, x2, yMid);
                Polygon arrow = new Polygon();
                arrow.addPoint(x2, yMid);
                arrow.addPoint(x2 - 6, yMid - 6);
                arrow.addPoint(x2 - 6, yMid + 6);
                g2.fill(arrow);
            }
            x += nodeWidth + 50;
        }

        if (!list.isEmpty()) {
            int sx1 = padding + nodeWidth;
            int sy = y + nodeHeight / 2;
            int sx2 = padding + nodeWidth + 25;
            g2.setColor(new Color(100, 125, 160));
            g2.setStroke(new BasicStroke(2f));
            g2.drawLine(sx1, sy, sx2, sy);
            Polygon arrow = new Polygon();
            arrow.addPoint(sx2, sy);
            arrow.addPoint(sx2 - 6, sy - 6);
            arrow.addPoint(sx2 - 6, sy + 6);
            g2.fill(arrow);
        }
    }
    
    // ACTIONS PERFORMED BY BUTTONS
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            Operations.traverseList(list);
        }
        if (e.getSource() == buttons[1]) {
            list = Operations.addElement(list);
            actionsAfterButtonEvent();
        }
        if (e.getSource() == buttons[2]) {
            list = Operations.removeElement(list);
            actionsAfterButtonEvent();
        }
        if (e.getSource() == buttons[3]) {
            Operations.searchElement(list);
        }
        if (e.getSource() == buttons[4]) {
            list = Operations.sortList(list);
            actionsAfterButtonEvent();
        }
        if (e.getSource() == buttons[5]) {
            list.clear();
            addresses.clear();
            actionsAfterButtonEvent();
        }
        if (e.getSource() == buttons[6]) {
        	System.exit(0);
        }
    }
    private static void actionsAfterButtonEvent() {
        drawingPanel.repaint();
        availList=getAvailList();
        updateAvailList();
    }
}
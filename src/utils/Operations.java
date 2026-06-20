package utils;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import components.Visualizer;
import models.Node;
public class Operations {

    public static void searchElement(LinkedList<Node> list) {
    	if(list.isEmpty()) {
            listIsEmptyErrorPane();
            return;
        }
        String strKey = JOptionPane.showInputDialog("Enter INFO to Search");
        int key = Integer.parseInt(strKey);
        new Thread(() -> {
            boolean flag = false;
            for (int i = 0; i < list.size(); i++) {                
                Node currentNode = list.get(i);
                currentNode.highlighted = true;
                Visualizer.drawingPanel.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (currentNode.info == key) {
                    JOptionPane.showMessageDialog(null, "Element found at Index " + i, "Success", JOptionPane.INFORMATION_MESSAGE);
                    flag = true;
                }
                currentNode.highlighted = false;
                Visualizer.drawingPanel.repaint();
                if (flag) break;
            }
            if (!flag) JOptionPane.showMessageDialog(null, "Element not Found", "Failure", JOptionPane.INFORMATION_MESSAGE);
        }).start();
    }

    
    public static LinkedList<Node> removeElement(LinkedList<Node> list) {
        if(list.isEmpty()) {
            listIsEmptyErrorPane();
            return list;
        }
        String[] options = {"Remove from Beginning", "Remove from End", "Remove by Index"};
        int choice = JOptionPane.showOptionDialog(null, "Choose Removal method:",
                "Remove Node", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "List is EMPTY");
        } 
        else {
            if (choice == 0) {
                Visualizer.startLink = list.getFirst().nextLink;
                Visualizer.addresses.remove(list.getFirst().link);
                list.removeFirst();
            } 
            else if (choice == 1) {
                if (list.size() == 1) {
                    Visualizer.addresses.remove(list.getLast().link);
                    list.removeLast();
                }
                else {
                    list.get(list.size() - 2).setNext(-1);
                    Visualizer.addresses.remove(list.getLast().link);
                    list.removeLast();
                }
            }
            else if (choice == 2) {
                String i = JOptionPane.showInputDialog(null, "Enter INDEX of Node");
                int index = Integer.parseInt(i);
                if (index < 0 || index >= list.size()) {
                    JOptionPane.showMessageDialog(null, "Index out of bounds");
                }
                else {
                    if (index == 0) {
                        Visualizer.startLink = list.getFirst().nextLink;
                        Visualizer.addresses.remove(list.getFirst().link);
                        list.removeFirst();
                    }
                    else if (index == list.size() - 1) {
                        Visualizer.addresses.remove(list.getLast().link);
                        list.removeLast();                    
                    }
                    else {
                        list.get(index - 1).setNext(list.get(index).nextLink);
                        Visualizer.addresses.remove(list.get(index).link);
                        list.remove(list.get(index));
                    }   
                }
            }
        }        
        return list;
    }

    
    public static LinkedList<Node> addElement(LinkedList<Node> list) {
        String[] options = {"Insert at Beginning", "Insert at End", "Insert by Index"};
        int choice = JOptionPane.showOptionDialog(null, "Choose Insertion method:",
                "Insert Node", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);    
        if (choice == 0) {
            Node newFirstNode = createNode();
            if (newFirstNode==null) return list;
            newFirstNode.setNext(Visualizer.startLink);
            Visualizer.startLink = newFirstNode.link;
            list.addFirst(newFirstNode);
        }
        else if (choice == 1) {
            Node newLastNode = createNode();
            if(newLastNode==null) return list;
            if (!list.isEmpty()) {
                list.getLast().setNext(newLastNode.link);
            }
            else {
            	Visualizer.startLink=newLastNode.link;
            }
            list.addLast(newLastNode);
        }
        else if (choice == 2) {
            String j = JOptionPane.showInputDialog(null, "Enter Index of new Node");
            int index = Integer.parseInt(j);
            if (index >= 0 && index <= list.size()) {
                Node newNode = createNode();
                if(newNode==null) return list;
                if (index == 0) {
                    newNode.nextLink = Visualizer.startLink;
                    Visualizer.startLink = newNode.link;
                    list.addFirst(newNode);
                }
                else if (index == list.size() - 1) {
                	if(list.isEmpty()) {
                		Visualizer.startLink=newNode.link;
                	}
                	newNode.setNext(list.getLast().link);
                	list.get(index - 1).setNext(newNode.link);
                    list.add(index, newNode);
                }
                else {
                    newNode.setNext(list.get(index - 1).nextLink);
                    list.get(index - 1).setNext(newNode.link);
                    list.add(index, newNode);
                }
            } 
            else {
                JOptionPane.showMessageDialog(null, "Index out of bounds.");
            }
        }
        return list;
    }
    
    public static LinkedList<Node> sortList(LinkedList<Node> list) {
        if(list.isEmpty()) {
            listIsEmptyErrorPane();
            return list;
        }
    	String[] options= {"Ascending","Descending"};
    	int choice = JOptionPane.showOptionDialog(null, "Choose Sorting method:",
                "Sort List", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    	if(choice==0) {
    		return ascending(list);
    	}
    	else if(choice==1) {
    		return descending(list);
    	}
    	else {
    		return list;
    	}
        
    }

    public static LinkedList<Node> ascending(LinkedList<Node> list) {
        LinkedList<Node> sortedList = new LinkedList<>();
        while (!list.isEmpty()) {
            Node minNode = list.getFirst();
            int index = 0;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).info < minNode.info) {
                    minNode = list.get(i);
                    index = i;
                }
            }
            list.remove(index);
            sortedList.add(minNode);
        }

        if (sortedList.equals(list)) return list;

        for (int i = 0; i < sortedList.size() - 1; i++) {
            Node current = sortedList.get(i);
            Node nextNode = sortedList.get(i + 1);
            current.setNext(nextNode.link);
        }

        Node firstNode = sortedList.getFirst();
        Visualizer.startLink = firstNode.link;
        return sortedList;
    }

    public static LinkedList<Node> descending(LinkedList<Node> list) {
    	LinkedList<Node> sortedList = new LinkedList<>();
        while (!list.isEmpty()) {
            Node maxNode = list.getFirst();
            int index = 0;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).info > maxNode.info) {
                    maxNode = list.get(i);
                    index = i;
                }
            }
            list.remove(index);
            sortedList.add(maxNode);
        }
        if (sortedList.equals(list)) return list;

        for (int i = 0; i < sortedList.size() - 1; i++) {
            Node current = sortedList.get(i);
            Node nextNode = sortedList.get(i + 1);
            current.setNext(nextNode.link);
        }

        Node firstNode = sortedList.getFirst();
        Visualizer.startLink = firstNode.link;
        return sortedList;
    }

    public static void traverseList(LinkedList<Node> list) {
    	if(list.isEmpty()) {
            listIsEmptyErrorPane();
            return;
        }
        new Thread(() -> {
            for (Node currentNode : list) {
                currentNode.highlighted = true;
                Visualizer.drawingPanel.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Visualizer.drawingPanel.repaint();
            }
            for(Node n: list) {
            	n.highlighted=false;
            }
            JOptionPane.showMessageDialog(null, "Traversal Complete", "Operation Completed",
            		JOptionPane.INFORMATION_MESSAGE);
        }).start();
    }

   private static int generateAddress() {
        int address;
        java.util.Random rand = new java.util.Random();
        do {
            address = rand.nextInt(100) + 1;
        } while (Visualizer.addresses.contains(address));
        Visualizer.addresses.add(address);
        return address;
    }
    private static Node createNode() {
        String i = JOptionPane.showInputDialog(null, "Enter INFO of new Node");
        try {
        	int info = Integer.parseInt(i);
        	int address = generateAddress();
    		return new Node(info, address);
        } catch (NumberFormatException fme) {
        	JOptionPane.showMessageDialog(null, "Only Integers are allowed!","Error",
        			JOptionPane.ERROR_MESSAGE);
        	return null;
        } catch (Exception e) {return null;}       
    }
    private static void listIsEmptyErrorPane() {
        JOptionPane.showMessageDialog(null,"List is Empty!","Error",JOptionPane.ERROR_MESSAGE);
    	return;
    }
}
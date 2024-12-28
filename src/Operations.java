import java.util.LinkedList;
import javax.swing.JOptionPane;
public class Operations {
	// AZAN, SEARCH
	public static int searchElement(LinkedList<Node> list) {
		String strKey = JOptionPane.showInputDialog("Enter INFO to Search");
        int key = Integer.parseInt(strKey);
    	for(int i=0;i<list.size();i++) {
    		if(list.get(i).info==key) return i;
    	}
    	return -1;
	}
	
	// TARIQ (SHAHEER), REMOVE
	
	// SAFWAN, ADD
	public static LinkedList<Node> addElement(LinkedList<Node> list) {
	    String[] options = {"Insert at Beginning", "Insert at End", "Insert by Index"};
	    int choice = JOptionPane.showOptionDialog(null, "Choose Insertion method:",
	            "Insert Node", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
	            null, options, options[0]);
	    if (choice==0) {
	        String i=JOptionPane.showInputDialog(null, "Enter INFO of new Node");
	        int info=Integer.parseInt(i);
	        int address=Screen.generateAddress();
	        Node newFirstNode=new Node(info, address);
	        newFirstNode.setNext(Screen.startLink);
	        Screen.startLink = address;
	        list.addFirst(newFirstNode);
	    } 
	    else if (choice==1) {
	        String i=JOptionPane.showInputDialog(null, "Enter INFO of new Node");
	        int info=Integer.parseInt(i);
	        int address=Screen.generateAddress();
	        Node newLastNode=new Node(info, address);
	        if (!list.isEmpty()) {
	            list.getLast().setNext(address); // Link the new node to the last node
	        }
	        list.addLast(newLastNode);
	    } 
	    else {
	        try {
	            String j=JOptionPane.showInputDialog(null, "Enter Index of new Node");
	            int index=Integer.parseInt(j);
	            if (index>=0 && index<=list.size()) {
	                String i=JOptionPane.showInputDialog(null, "Enter INFO of new Node");
	                int info=Integer.parseInt(i);
	                int address=Screen.generateAddress();
	                Node newNode=new Node(info, address);
	                if(index==0) {
	                	newNode.nextLink=Screen.startLink;
	                	Screen.startLink=address;
	                	list.addFirst(newNode);
	                }
	                else if(index==list.size()-1) {
	                	list.getLast().nextLink=address;
	                	list.addLast(newNode);
	                }
	                else {
	                	Node current=list.get(index);
	                	Node previous=list.get(index-1);
	                	newNode.setNext(current.link);
	                	previous.setNext(address);
	                	list.add(index, newNode);
	                }
	            } 
	            else {
	                JOptionPane.showMessageDialog(null, "Index out of bounds.");
	            }
	        } 
	        catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "Enter a valid integer.");
	        }
	    }
	    return list;
	}
	public static LinkedList<Node> sortList(LinkedList<Node> list) {
		LinkedList<Node> sortedList = new LinkedList<>();
        while (!list.isEmpty()) {
            Node minNode = list.get(0);
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
        // Update nextLink for sortedList
        for (int i = 0; i < sortedList.size() - 1; i++) {
            sortedList.get(i).setNext(sortedList.get(i + 1).link);
        }
        // Set the last node's nextLink to -1 (NULL)
        if (!sortedList.isEmpty()) {
            sortedList.getLast().setNext(-1);
        }
        Screen.startLink=sortedList.getFirst().link;       
		return sortedList;
	}

	public static void traverseList(LinkedList<Node> list) {
		new Thread(() -> {
			for (int i = 0; i < list.size(); i++) {
				Node currentNode = list.get(i);
				currentNode.highlighted = true;
				Screen.panel.repaint();
				try {
					Thread.sleep(1000); // 2000 milliseconds = 2 seconds
				}
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}
            currentNode.highlighted = false;
            Screen.panel.repaint();
			}
		}).start();
	}
}
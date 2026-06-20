package models;
public class Node {
    public boolean highlighted;
    public int info;
    public int link;
    public int nextLink;

    public Node(int value, int address) {
        info = value;
        link = address;
        nextLink = -1;
        highlighted = false;
    }
    public void setNext(int link) {
        nextLink = link;
    }
}
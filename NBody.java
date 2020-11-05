import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;

class ArrayList<E> {
    Object arr[];
    int size;
    int capa;

    ArrayList() {
        capa = 10;
        size = 0;
        arr = new Object[capa];
    }

    ArrayList(int cap) {
        capa = cap;
        size = 0;
        arr = new Object[capa];
    }

    void add(E item) {
        // grow list //
        if (size == currentLen()) {
            Object arrTmp[] = new Object[size];
            for (int i = 0; i < size; i++)
                arrTmp[i] = arr[i];
            capa = capa + 10;
            arr = new Object[capa];
            for (int i = 0; i < size; i++)
                arr[i] = arrTmp[i];
        }
        arr[size++] = item;
    }

    void replace(int pos, E item) {
        for (int i = size; i > pos; i--) {
            arr[pos] = arr[i - 1];
            arr[pos] = item;
        }
    }

    @SuppressWarnings("unchecked") // “uses unchecked or unsafe operations” error override
    E get(int i) {
        return (E) arr[i];
    }

    boolean checkEmpty() {
        return (size == 0);
    }

    int currentLen() {
        return capa;
    }

    int size() {
        return size;
    }
}

class LinkedList<E> {
    // setup //
    private static int size;
    private Node head;

    private class Node {
        Node next;
        Object data;

        public Node(Object d) {
            next = null;
            data = d;
        }

        @SuppressWarnings("unused") // Visual Code Error
        public Node(Object dataValue, Node nextValue) {
            next = nextValue;
            data = dataValue;
        }

        public Object getData() {
            return data;
        }

        @SuppressWarnings("unused") // Visual Code Error
        public void setData(Object d) {
            data = d;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node n) {
            next = n;
        }
    }

    public int size() {
        return size;
    }

    // add to end of list //
    public void add(Object data) {
        // if infront //
        if (head == null)
            head = new Node(data);

        // else //
        Node temp = new Node(data);
        Node curr = head;

        // cycle until null is found
        if (curr != null) {
            while (curr.getNext() != null)
                curr = curr.getNext();
            curr.setNext(temp);
        }
        size++;
    }

    // add to specific pos //
    public void add(Object data, int pos) {
        Node temp = new Node(data);
        Node curr = head;

        // find position //
        if (curr != null)
            for (int i = 0; i < pos && curr.getNext() != null; i++)
                curr = curr.getNext();

        // store at position //
        temp.setNext(curr.getNext());
        curr.setNext(temp);
        size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int pos) {
        if (pos < 0) // error checker //
            return null;
        // returns value at pos//
        Node curr = null;
        if (head != null) {
            curr = head.getNext();
            for (int i = 0; i < pos; i++) {
                if (curr.getNext() == null)
                    return null;
                curr = curr.getNext();
            }
            return (E) curr.getData();
        }
        return (E) curr;
    }

    // remove at position //
    public boolean remove(int pos) {
        // safety net //
        if (pos < 1 || pos > size())
            return false;
        Node curr = head;
        // remove at pos //
        if (head != null) {
            for (int i = 0; i < pos; i++) {
                if (curr.getNext() == null)
                    return false;
                curr = curr.getNext();
            }
            curr.setNext(curr.getNext().getNext());
            size--;
            return true;
        }
        return false;
    }

    // remove and add at position //
    public void replace(int pos, Object data) {
        remove(pos);
        add(data, pos);
    }
}

public class NBody extends JPanel implements ActionListener {
    // Visual Code Error override //
    private static final long serialVersionUID = -9015211243192660708L;

    // global set up //
    Timer tm = new Timer(5, this);
    static int NUM_OF_BODIES = 0;
    static int FRAME_WIDTH = 768; // JFrame //
    static int FRAME_HEIGHT = 768; // JFrame //
    static Boolean ArrayL = false;
    static Boolean linkL = false;
    static ArrayList<String> AL = new ArrayList<String>();
    static LinkedList<String> LL = new LinkedList<String>();

    // circle painter calculator //
    @Override // Signature Visual Code Error //
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        if (ArrayL)
            // sets up every body position and size //
            for (int i = 0; i < NUM_OF_BODIES; i++) {
                int bodySize = (int) Float.parseFloat(AL.get(6 + (i * 7)));
                int tmpx = (int) Float.parseFloat(AL.get(2 + (i * 7))) - (bodySize / 2);
                int tmpy = (int) Float.parseFloat(AL.get(3 + (i * 7))) - (bodySize / 2);
                g.fillOval(tmpx, tmpy, bodySize, bodySize);
            }
        if (linkL)
            // sets up every body position and size //
            for (int i = 0; i < NUM_OF_BODIES; i++) {
                int bodySize = (int) Float.parseFloat(LL.get(6 + (i * 7)));
                int tmpx = (int) Float.parseFloat(LL.get(2 + (i * 7))) - (bodySize / 2);
                int tmpy = (int) Float.parseFloat(LL.get(3 + (i * 7))) - (bodySize / 2);
                g.fillOval(tmpx, tmpy, bodySize, bodySize);
            }
        tm.start();
    }

    // does physics math for Array Lists //
    public static void runPhyicsAL() {
        // creates temp values to do math with forces //
        for (int i = 0; i < NUM_OF_BODIES; i++) {
            Float tmpMass = Float.parseFloat(AL.get(1 + (i * 7)));
            Float tmpx = Float.parseFloat(AL.get(2 + (i * 7)));
            Float tmpy = Float.parseFloat(AL.get(3 + (i * 7)));
            Float tmpVx = Float.parseFloat(AL.get(4 + (i * 7)));
            Float tmpVy = Float.parseFloat(AL.get(5 + (i * 7)));
            int bodySize = (int) Float.parseFloat(AL.get(6 + (i * 7)));

            // freezes if out of bounds //
            if ((tmpx + bodySize > 0) && (tmpx - bodySize < FRAME_WIDTH) && (tmpy + bodySize > 0)
                    && (tmpy - bodySize < FRAME_HEIGHT)) {

                // F = G (m*m)/r^2
                Float f = 0.0f; // force x
                Float r = 0.0f; // distance

                // does math for each planet except for current //
                for (int j = 0; j < NUM_OF_BODIES; j++) {
                    r = 0.0f; // distance
                    if (j != i) {
                        // calculates distance //
                        Float otherx = Float.parseFloat(AL.get(2 + (j * 7)));
                        Float othery = Float.parseFloat(AL.get(3 + (j * 7)));

                        // A^2 + B^2 = C^2
                        r += (float) Math.pow(tmpx - otherx, 2);
                        r += (float) Math.pow(tmpy - othery, 2);
                        r = (float) Math.sqrt(r); // == C

                        // calculates forces from all bodies
                        Float otherMass = Float.parseFloat(AL.get(1 + (j * 7)));
                        f += ((0.0000000000667f * tmpMass * otherMass) / (float) Math.pow(r, 2));
                    }
                }

                // push updated values to list //
                AL.replace(2 + (i * 7), Float.toString(tmpx + tmpVx + f));
                AL.replace(3 + (i * 7), Float.toString(tmpy + tmpVy + f));
                AL.replace(4 + (i * 7), Float.toString(tmpVx));
                AL.replace(5 + (i * 7), Float.toString(tmpVy));
            }
        }
    }

    // does physics math for Linked Lists //
    public static void runPhyicsLL() {
        // creates temp values to do math with forces //
        for (int i = 0; i < NUM_OF_BODIES; i++) {
            Float tmpMass = Float.parseFloat(LL.get(1 + (i * 7)));
            Float tmpx = Float.parseFloat(LL.get(2 + (i * 7)));
            Float tmpy = Float.parseFloat(LL.get(3 + (i * 7)));
            Float tmpVx = Float.parseFloat(LL.get(4 + (i * 7)));
            Float tmpVy = Float.parseFloat(LL.get(5 + (i * 7)));
            int bodySize = (int) Float.parseFloat(LL.get(6 + (i * 7)));

            // freezes if out of bounds //
            if ((tmpx + bodySize > 0) && (tmpx - bodySize < FRAME_WIDTH) && (tmpy + bodySize > 0)
                    && (tmpy - bodySize < FRAME_HEIGHT)) {

                // F = G (m*m)/r^2
                Float f = 0.0f; // force x
                Float r = 0.0f; // distance

                // does math for each planet except for current //
                for (int j = 0; j < NUM_OF_BODIES; j++) {
                    r = 0.0f; // distance
                    if (j != i) {
                        // calculates distance //
                        Float otherx = Float.parseFloat(LL.get(2 + (j * 7)));
                        Float othery = Float.parseFloat(LL.get(3 + (j * 7)));

                        // A^2 + B^2 = C^2
                        r += (float) Math.pow(tmpx - otherx, 2);
                        r += (float) Math.pow(tmpy - othery, 2);
                        r = (float) Math.sqrt(r); // == C

                        // calculates forces from all bodies
                        Float otherMass = Float.parseFloat(LL.get(1 + (j * 7)));
                        f += ((0.0000000000667f * tmpMass * otherMass) / (float) Math.pow(r, 2));
                    }
                }

                // push updated values to list //
                LL.replace(2 + (i * 7), Float.toString(tmpx + tmpVx + f));
                LL.replace(3 + (i * 7), Float.toString(tmpy + tmpVy + f));
                LL.replace(4 + (i * 7), Float.toString(tmpVx));
                LL.replace(5 + (i * 7), Float.toString(tmpVy));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        // does physic math for which ever list type choosen //
        if (ArrayL)
            runPhyicsAL();
        if (linkL)
            runPhyicsLL();
        repaint(); // updates frames //
    }

    public static void runFrame() {
        // Graphics Set up //
        NBody n = new NBody();
        JFrame jf = new JFrame();
        jf.setTitle("NBody");
        jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(n);
    }

    // if array list choosen - then store in array list //
    public static void runArrayList(float scale, Scanner fileIn) {
        runFrame(); // starts j frame set up //
        // takes file and add it to list //
        while (fileIn.hasNext()) {
            String tmp = fileIn.next();
            String[] commaRid = tmp.split(","); // removes commas before adding //
            for (int i = 0; i < commaRid.length; i++)
                AL.add(commaRid[i]);
        }
        NUM_OF_BODIES = (AL.size / 7);
    }

    // if linked list choosen - then store in linked list //
    public static void runLinkedList(float scale, Scanner fileIn) {
        runFrame();// starts j frame set up //
        // takes file and add it to list //
        while (fileIn.hasNext()) {
            String tmp = fileIn.next();
            String[] commaRid = tmp.split(",");
            for (int i = 0; i < commaRid.length; i++)
                LL.add(commaRid[i]);
        }
        NUM_OF_BODIES = (LL.size() / 7);
    }

    public static void main(String[] argv) throws FileNotFoundException {
        // file reader start//

        // loads file //
        Scanner fileIn = new Scanner(new File(argv[0]));

        // reads data structure type //
        String dataStruct = fileIn.nextLine();

        // sets scale //
        float scale = Float.parseFloat(fileIn.nextLine());

        // sets data structure type //
        if (dataStruct.equals("ArrayList")) {
            ArrayL = true;
            runArrayList(scale, fileIn);
        } else if (dataStruct.equals("LinkedList")) {
            linkL = true;
            runLinkedList(scale, fileIn);
        } else {
            System.out.println("Error in txt file given");
        }
        fileIn.close();
        // file reader end //
    }
}

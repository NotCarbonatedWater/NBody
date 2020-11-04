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

public class NBody extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -9015211243192660708L;

    Timer tm = new Timer(5, this);
    int x = 0, velX = 2;
    static int NUM_OF_BODIES = 0;
    static int FRAME_WIDTH = 768;
    static int FRAME_HEIGHT = 768;
    static ArrayList<String> A1 = new ArrayList<String>();

    @Override // Signature Visual Code Error //
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);

        for (int i = 0; i < NUM_OF_BODIES; i++) {
            int tmpx = (int) Float.parseFloat(A1.get(2 + (i * 7)));
            int tmpy = (int) Float.parseFloat(A1.get(3 + (i * 7)));
            int bodySize = (int) Float.parseFloat(A1.get(6 + (i * 7)));
            g.fillOval(tmpx, tmpy, bodySize, bodySize);
        }

        tm.start();
    }

    public static void runPhyics() {
        for (int i = 0; i < NUM_OF_BODIES; i++) {
            Float tmpx = Float.parseFloat(A1.get(2 + (i * 7)));
            Float tmpy = Float.parseFloat(A1.get(3 + (i * 7)));
            Float tmpVx = Float.parseFloat(A1.get(4 + (i * 7)));
            Float tmpVy = Float.parseFloat(A1.get(5 + (i * 7)));

            A1.replace(2 + (i * 7), Float.toString(tmpx + tmpVx));
            A1.replace(3 + (i * 7), Float.toString(tmpy + tmpVy));
            A1.replace(4 + (i * 7), Float.toString(tmpVx));
            A1.replace(5 + (i * 7), Float.toString(tmpVy));

        }
        System.out.println("ARRAY LIST IN PHYSICS");
        for (int i = 0; i < A1.size(); i++)
            System.out.print(A1.get(i) + "\n");
    }

    public void actionPerformed(ActionEvent e) {
        x = x + velX;
        runPhyics();
        repaint();
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

    public static void runWithArrayList(float scale, Scanner fileIn) {
        runFrame();

        while (fileIn.hasNext()) {
            String tmp = fileIn.next();
            String[] commaRid = tmp.split(",");
            for (int i = 0; i < commaRid.length; i++)
                A1.add(commaRid[i]);
        }
        System.out.println("ARRAY LIST");
        for (int i = 0; i < A1.size(); i++)
            System.out.print(A1.get(i) + "\n");

        System.out.println("ARRAY LIST");

        for (int i = 0; i < A1.size(); i++) {
            System.out.print(A1.get(i) + "\n");
        }
        System.out.println("\n\n" + (A1.size / 7));
        NUM_OF_BODIES = (A1.size / 7);

    }

    public static void main(String[] argv) throws FileNotFoundException {
        // file reader start//
        // loads file //

        // terminal switch: //
        // Scanner fileIn = new Scanner(new File(argv[0]));
        // VS CODE Switch: // nbody_input.txt
        File file = new File("nbody_input.txt");
        Scanner fileIn = new Scanner(file);

        // reads data structure type //
        String dataStruct = fileIn.nextLine();
        System.out.println(dataStruct);

        // sets scale //
        float scale = Float.parseFloat(fileIn.nextLine());
        System.out.println("Does it work: " + scale);

        // sets data structure type //
        if (dataStruct.equals("ArrayList")) {
            System.out.println("ArrayList - found");
            runWithArrayList(scale, fileIn);
        } else if (dataStruct.equals("LinkedList")) {
            System.out.println("LinkedList - found");
        } else {
            System.out.println("Error in txt file - found");
        }
        fileIn.close();
        // file reader end //
    }
}


// import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
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
        if (size == 0)
            return true;
        else
            return false;
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawRect(100, 10, 30, 40);
        g.fillRect(10, 10, 20, 10);

        g.setColor(Color.BLUE);
        g.fillOval(x, 100, 50, 50);

        tm.start();
    }

    public void actionPerformed(ActionEvent e) {
        x = x + velX;
        repaint();
    }

    public static void runWithArrayList(float scale, Scanner fileIn) {

        ArrayList<String> A1 = new ArrayList<String>();

        while (fileIn.hasNext()) {
            String tmp = fileIn.next();
            String[] commaRid = tmp.split(",");
            for (int i = 0; i < commaRid.length; i++) {
                A1.add(commaRid[i]);
            }
        }
        System.out.println("ARRAY LIST");
        for (int i = 0; i < A1.size(); i++) {
            System.out.print(A1.get(i) + "\n");
        }

        A1.replace(0, "696969696969696969696969");

        System.out.println("ARRAY LIST");
        for (int i = 0; i < A1.size(); i++) {
            System.out.print(A1.get(i) + "\n");
        }
    }

    public static void main(String[] argv) throws FileNotFoundException {

        // file reader start//
        // loads file //

        // terminal switch: //
        // Scanner fileIn = new Scanner(new File(argv[0]));

        // VS CODE Switch: // nbody_input.txt
        File file = new File("nbody_input.txt");
        Scanner fileIn = new Scanner(file);

        // fileIn.nextLine(); // Reads one line from the file
        // fileIn.next(); // Reads one word from the file

        // fileIn.hasNext(); // Returns true if there is another word in the file
        // fileIn.hasNextLine(); // Returns true if there is another line to read from
        // the file

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

        // Graphics Set up //
        NBody n = new NBody();
        JFrame jf = new JFrame();
        jf.setTitle("NBody");
        jf.setSize(768, 768);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(n);
    }
}

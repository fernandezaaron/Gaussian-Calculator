import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Gauss extends JFrame {
    JScrollPane CodesimScroll, ComputeScroll;
    JTable InputTable;
    JTable[] OperationTable = new JTable[100];
    JTable[] ZeroTable = new JTable[100];
    JPanel GaussTablePanel, GaussPanel, UI, ButtonsPanel, ButtonsPanel2, codesimulation1, codesimulation, computation1, computation, EastPanel;
    JScrollPane InputScroll, GaussScroll;
    JScrollPane[] OperationScroll = new JScrollPane[100];
    JScrollPane[] ZeroScroll = new JScrollPane[100];
    JButton Generate, Calculate, Randomizer, Clear;
    JButton next, prev;
    JTextField SizeInput;
    JLabel InputLabel;
    DefaultTableModel InputDTM;
    DefaultTableModel[] OperationDTM = new DefaultTableModel[100];
    DefaultTableModel[] ZeroDTM = new DefaultTableModel[100];
    JTextArea codesim = new JTextArea();
    JTextArea compu = new JTextArea();
    Resultspage res;

    int Counter = 0;
    int click = 1;

    public Gauss() {
        super("Gaussian Multiplication Matrix");

        SizeInput = new JTextField();

        Generate = new JButton(new AbstractAction("Generate Table") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeint = Integer.parseInt(SizeInput.getText());
                if(Integer.parseInt(SizeInput.getText()) < 2) {
                    SizeInput.setText("2");
                    sizeint = 2;
                }
                else if(Integer.parseInt(SizeInput.getText()) > 6) {
                    SizeInput.setText("6");
                    sizeint = 6;
                }

                InputDTM = new DefaultTableModel(sizeint, sizeint+1);
                InputTable = new JTable(InputDTM);
                InputScroll =  new JScrollPane(InputTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                InputScroll.getViewport().setPreferredSize(new Dimension(400, 300));

                EastPanel.add(prev);
                EastPanel.add(next);
                CodesimScroll = new JScrollPane(codesimulation1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                CodesimScroll.setPreferredSize(new Dimension(300, 300));
                codesimulation1.add(codesim);
                codesimulation.add(CodesimScroll, BorderLayout.EAST);
                codesimulation.add(EastPanel, BorderLayout.EAST);
                ComputeScroll = new JScrollPane(computation1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                ComputeScroll.setPreferredSize(new Dimension(300, 300));
                computation1.add(compu);
                computation.add(ComputeScroll, BorderLayout.EAST);
                GaussTablePanel.add(InputScroll);
                GaussPanel.add(GaussTablePanel, BorderLayout.WEST);
                add(GaussPanel, BorderLayout.WEST);
                add(codesimulation, BorderLayout.EAST);
                add(computation, BorderLayout.CENTER);
                GaussPanel.updateUI();
                codesim.setEditable(false);
            }
        });

        Randomizer = new JButton(new AbstractAction("Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeint = Integer.parseInt(SizeInput.getText());
                int min = 1;
                int max = 50;
                for(int i=0; i<sizeint; i++) {
                    for (int j = 0; j <= sizeint; j++) {
                        double rand = Math.random() * (max - min + 1) + min;
                        InputDTM.setValueAt((int)rand,i,j);
                    }
                }
            }
        });

        Clear = new JButton(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                SizeInput.setText("");
                codesimulation.removeAll();
                codesimulation.revalidate();
                codesimulation.repaint();
                computation.removeAll();
                computation.revalidate();
                computation.repaint();
                GaussPanel.removeAll();
                GaussPanel.revalidate();
                GaussPanel.repaint();
                GaussScroll.removeAll();
                GaussScroll.revalidate();
                GaussScroll.repaint();
                GaussTablePanel.removeAll();
                GaussTablePanel.revalidate();
                GaussTablePanel.repaint();
                EastPanel.removeAll();
                EastPanel.revalidate();
                EastPanel.repaint();
                res.dispose();
                Counter = 0;
            }
        });

        next = new JButton(new AbstractAction("Next") {
            @Override
            public void actionPerformed(ActionEvent e) {
                click++;
                Codes(click);
                System.out.println("Next: " + click);

            }
        });

        prev = new JButton(new AbstractAction("Prev") {
            @Override
            public void actionPerformed(ActionEvent e) {
                click--;
                Codes(click);
                System.out.println("Prev: " + click);
            }
        });

        Calculate = new JButton(new AbstractAction("Calculate") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeint = Integer.parseInt(SizeInput.getText());
                int i, j;
                float[][] gauss = new float[sizeint][sizeint+1];
                float[][] holdgauss = new float[sizeint][sizeint+1];
                String[][] ph = new String[sizeint][sizeint+1];
                Codes(click);

                for(i = 0; i < sizeint; i++) {
                    for(j = 0; j <= sizeint; j++) {
                        ph[i][j] = InputDTM.getValueAt(i, j).toString();
                        gauss[i][j] = Float.parseFloat(ph[i][j]);
                        holdgauss[i][j] = Float.parseFloat(ph[i][j]);
                    }
                }

                while(Counter < sizeint) {
                    Operate(gauss, holdgauss, sizeint, Counter);

                    OperationDTM[Counter] = new DefaultTableModel(sizeint,sizeint+1);
                    OperationTable[Counter] = new JTable(OperationDTM[Counter]);
                    OperationScroll[Counter] = new JScrollPane(OperationTable[Counter], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    for(i = 0; i < sizeint; i++) {
                        for(j = 0; j <= sizeint; j++) {
                            OperationDTM[Counter].setValueAt(holdgauss[i][j], i, j);
                        }
                    }

                    Zeroer(gauss, holdgauss, sizeint, Counter);

                    ZeroDTM[Counter] = new DefaultTableModel(sizeint,sizeint+1);
                    ZeroTable[Counter] = new JTable(ZeroDTM[Counter]);
                    ZeroScroll[Counter] = new JScrollPane(ZeroTable[Counter], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    for(i = 0; i < sizeint; i++) {
                        for(j = 0; j <= sizeint; j++) {
                            ZeroDTM[Counter].setValueAt(holdgauss[i][j], i, j);
                        }
                    }

                    GaussTablePanel.add(OperationScroll[Counter]);
                    GaussTablePanel.add(ZeroScroll[Counter]);
                    GaussScroll = new JScrollPane(GaussTablePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    GaussPanel.add(GaussScroll, BorderLayout.WEST);
                    add(GaussPanel, BorderLayout.WEST);
                    GaussPanel.updateUI();
                    Counter++;
                }

                res = new Resultspage(gauss, sizeint);
                res.setVisible(true);

            }
        });

        UI = new JPanel(new GridLayout(3,2));

        InputLabel = new JLabel("Enter size");
        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ButtonsPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        codesimulation = new JPanel();
        codesimulation.setLayout(new BoxLayout(codesimulation, BoxLayout.Y_AXIS));
        codesimulation.setPreferredSize(new Dimension(300,250));
        codesimulation.setBackground(Color.lightGray);

        codesimulation1 = new JPanel(new GridLayout(1,1));

        computation = new JPanel();
        computation.setLayout(new BoxLayout(computation, BoxLayout.Y_AXIS));
        computation.setPreferredSize(new Dimension(300,250));
        computation.setBackground(Color.lightGray);

        computation1 = new JPanel(new GridLayout(1,1));

        EastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        EastPanel.setPreferredSize(new Dimension(100, 30));
        EastPanel.setBackground(Color.lightGray);

        ButtonsPanel.add(Generate);
        ButtonsPanel.add(Calculate);
        ButtonsPanel2.add(Randomizer);
        ButtonsPanel2.add(Clear);

        UI.add(InputLabel);
        UI.add(SizeInput);
        UI.add(ButtonsPanel);
        UI.add(ButtonsPanel2);

        GaussPanel = new JPanel(new BorderLayout());
        GaussPanel.setPreferredSize(new Dimension(470,100));
        GaussPanel.setBackground(Color.lightGray);

        GaussTablePanel = new JPanel();
        GaussTablePanel.setLayout(new BoxLayout(GaussTablePanel, BoxLayout.Y_AXIS));
        GaussTablePanel.setBackground(Color.orange);

        add(UI, BorderLayout.NORTH);
        setSize(1280,750);
        setVisible(true);
    }

    // ETO YUNG FUNCTION NA GAGAWING 1 YUNG VALUE KAPAG SAME INDEX. HALIMBAWA i = 0 , j = 0
    float[][] Operate(float[][] gauss, float[][] holdgauss, int sizeint, int count) {
        for(int i = 0; i < sizeint; i++) {
            for (int j = 0; j <= sizeint; j++) {
                if (holdgauss[count][count] != 1) { // KAPAG HINDI 1 YUNG VALUE SA PAREHONG INDEX, IDIDIVIDE ANG LAHAT NG VALUE SA ROW GAMIT YUNG UNANG VALUE
                    compu.append(gauss[count][j] + " / " + holdgauss[count][count]);
                    gauss[count][j] /= holdgauss[count][count];
                    compu.append(" = " + gauss[count][j] + "\n");
                }
                if(gauss[i][j] == -0) { // MAY TENDENCY NA MAGNEGATIVE YUNG VALUE NA TO KAPAG NA MINUS, KAYA TATANGGALIN NITO YUNG NEGATIVE
                    gauss[i][j] = 0;
                }
                System.out.print(gauss[i][j] + " ");
            }
            saver(gauss, holdgauss, sizeint);
            System.out.println();
        }
        compu.append("\n");
        return holdgauss;
    }

    // ETO YUNG FUNCTION NA MINAMINUS YUNG MGA HINDI PA ZERO SA COLUMN
    float[][] Zeroer(float[][] gauss, float[][] holdgauss, int sizeint, int count) {
        float[][] Zeroing = new float[sizeint][sizeint+1];
        for(int i = 0; i < sizeint; i++) { // DITO SINESAVE YUNG VALUE NA IMAMINUS KADA COLUMN
            Zeroing[i][count] = gauss[i][count];
        }

        System.out.println();

        for(int i = 0; i < sizeint; i++) {
            for(int j = 0; j <= sizeint; j++) {
                if(i != count) {
                    if(Zeroing[i][count] < 0 ) { // I-AADD DITO YUNG VALUE KAPAG NEGATIVE YUNG VALUE PARA MAGING ZERO
                        compu.append(holdgauss[i][j] + " + (" + holdgauss[count][j] + " * " + " -(" + Zeroing[i][count] + ")");
                        gauss[i][j] = holdgauss[i][j] + (holdgauss[count][j] * -(Zeroing[i][count]));
                        compu.append(" = " + gauss[i][j] + "\n");
                    }
                    else { // I-MAMINUS DITO YUNG VALUE PARA MAGING ZERO
                        compu.append(holdgauss[i][j] + " - (" + holdgauss[count][j] + " * " + Zeroing[i][count] + ")");
                        gauss[i][j] = holdgauss[i][j] - (holdgauss[count][j] * Zeroing[i][count]);
                        compu.append(" = " + gauss[i][j] + "\n");
                    }
                }
                if(gauss[i][j] == -0) { // KAPAG NEGATIVE ZERO YUNG VALUE, TATANGGALIN ANG NEGATIVE DITO
                    gauss[i][j] = 0;
                }
                System.out.print(gauss[i][j] + " ");
            }
            System.out.println();
            compu.append("\n");
        }
        saver(gauss, holdgauss, sizeint);
        System.out.println();

        return holdgauss;
    }

    // DITO INUUPDATE YUNG MATRIX PAGTAPOS MAGDIVIDE AT MINUS
    static float[][] saver(float[][] gauss, float[][] holdgauss, int sizeint) {
        for(int i = 0; i < sizeint; i++) {
            for(int j = 0; j <= sizeint; j++) {
                holdgauss[i][j] = gauss[i][j];
            }
        }
        return holdgauss;
    }

    // ITO YUNG FUNCTION NA NAGPAPAKITA NG CODE
    public void Codes(int click){
        if(click % 2 != 0){
            codesim.setText("for(int i = 0; i < sizeint; i++) {\n" +
                    "            for (int j = 0; j <= sizeint; j++) {\n" +
                    "                if (holdgauss[count][count] != 1) {\n" +
                    "                    gauss[count][j] /= holdgauss[count][count];\n" +
                    "                }\n" +
                    "                if(gauss[i][j] == -0) {\n" +
                    "                    gauss[i][j] = 0;\n" +
                    "                }\n" +
                    "                System.out.print(gauss[i][j] + \" \");\n" +
                    "            }\n" +
                    "            saver(gauss, holdgauss, sizeint, count);\n" +
                    "            System.out.println();\n" +
                    "        }");
        }
        if(click % 2 == 0){
            codesim.setText("for(int i = 0; i < sizeint; i++) {\n" +
                    "            Zeroing[i][count] = gauss[i][count];\n" +
                    "        }\n" +
                    "\n" +
                    "        for(int i = 0; i < sizeint; i++) {\n" +
                    "            for(int j = 0; j <= sizeint; j++) {\n" +
                    "                if(i != count) {\n" +
                    "                    if(Zeroing[i][count] < 0 ) {\n" +
                    "                        gauss[i][j] = holdgauss[i][j] + (holdgauss[count][j] * -(Zeroing[i][count]));\n" +
                    "                    }\n" +
                    "                    else {\n" +
                    "                        gauss[i][j] = holdgauss[i][j] - (holdgauss[count][j] * Zeroing[i][count]);\n" +
                    "                    }\n" +
                    "                }\n" +
                    "                if(gauss[i][j] == -0) {\n" +
                    "                    gauss[i][j] = 0;\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "        saver(gauss, holdgauss, sizeint, count);\n");
        }
    }

    // DRIVER FUNCTION
    public static void main(String[] args) {
        new Gauss();
    }
}

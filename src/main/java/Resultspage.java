import javax.swing.*;

class Resultspage extends JFrame {
    Resultspage(float gauss[][], int sizeint) {
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Result");
        JLabel[] res = new JLabel[100];
        JLabel word = new JLabel();
        float[] resgauss = new float[sizeint];
        for(int i = 0; i < sizeint; i++) {
            for(int j = 0; j <= sizeint; j++) {
                resgauss[i] = gauss[i][sizeint];
            }
        }

        word.setText("Results");
        word.setBounds(10,10, 50,20);

        int yaxis = 10;
        for(int i = 0; i < sizeint; i++) {
            res[i] = new JLabel();
            res[i].setText(String.valueOf((char)(i+65)) + " = " + String.valueOf(resgauss[i]));
            System.out.println("x" + (i+1) + " = " + resgauss[i]);
            res[i].setBounds(10, yaxis, 100,100);
            add(res[i]);
            yaxis += 20;
        }
        add(word);
        setSize(200,250);
    }

}

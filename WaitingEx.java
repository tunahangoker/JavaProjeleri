import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surfacet extends JPanel
        implements ActionListener {

    private Timer timer;
    private int count;
    private final int INITIAL_DELAY = 200; //animasyonu oynatma gecikmesi
    private final int DELAY = 80; //animasyonu oynatma hızı
    private final int NUMBER_OF_LINES = 8;
    private final int STROKE_WIDTH = 3;

    private final double[][] trs = {
            {0.0, 0.15, 0.30, 0.5, 0.65, 0.80, 0.9, 1.0},
            {1.0, 0.0, 0.15, 0.30, 0.5, 0.65, 0.8, 0.9},
            {0.9, 1.0, 0.0, 0.15, 0.3, 0.5, 0.65, 0.8},
            {0.8, 0.9, 1.0, 0.0, 0.15, 0.3, 0.5, 0.65},
            {0.65, 0.8, 0.9, 1.0, 0.0, 0.15, 0.3, 0.5},
            {0.5, 0.65, 0.8, 0.9, 1.0, 0.0, 0.15, 0.3},
            {0.3, 0.5, 0.65, 0.8, 0.9, 1.0, 0.0, 0.15},
            {0.15, 0.3, 0.5, 0.65, 0.8, 0.9, 1.0, 0.0}
    };

    public Surfacet() {

        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        Graphics2D g3d = (Graphics2D) g.create();


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
        int height = getHeight();

        g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2d.translate(width / 2, height / 2);






        for (int i = 0; i < 5; i++) {

            float alpha = (float) trs[count % NUMBER_OF_LINES][i];
            AlphaComposite acomp = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(acomp);


            g2d.rotate(72 * (Math.PI / 180));



            double red = Math.random();
            double green = Math.random();
            double blue = Math.random();

            // Renk bileşenlerini 0-255 arasına sınırlayın
            int redValue = (int) (red * 256);
            int greenValue = (int) (green * 256);
            int blueValue = (int) (blue * 256);

            // Color sınıfını kullanarak rastgele rengi oluşturun
            Color randomColor = new Color(redValue, greenValue, blueValue);

            setBackground(randomColor);



            g2d.setColor(randomColor);
            g2d.fillOval(0,-10,100,100);

            //g2d.drawLine(0, -10, 0, -40);
        }

        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
        count++;
    }
}

public class WaitingEx extends JFrame {

    public WaitingEx() {
        add(new Surfacet());
        setTitle("Waiting");
        setSize(1100, 1100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }



    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                WaitingEx ex = new WaitingEx();
                ex.setVisible(true);
            }
        });
    }
}
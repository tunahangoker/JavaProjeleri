import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class ss extends JPanel implements ActionListener {
    private final Color colors[] = {
            Color.blue, Color.cyan, Color.green,
            Color.magenta, Color.orange, Color.pink,
            Color.red, Color.yellow, Color.lightGray, Color.white
    };

    private float estroke[];
    private Path2D.Float[] stars;
    private double maxSize = 10;
    private final int NUMBER_OF_STARS = 35;
    private final int DELAY = 250;
    private final int INITIAL_DELAY = 0;
    private Timer timer;
    private int rightCount = 0;

    public ss() {
        initSurface();
        initStars();
        initTimer();
    }

    private void initSurface() {
        setBackground(Color.black);
        stars = new Path2D.Float[NUMBER_OF_STARS];
        estroke = new float[stars.length];
    }

    private void initStars() {
        int w = 1100; // Genişlik
        int h = 1100; // Yükseklik
        maxSize = 10; // Başlangıçta küçük boyutta başlasın

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Path2D.Float();
            posRandStars(i, maxSize * Math.random(), w, h);
        }
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void posRandStars(int i, double size, int w, int h) {
        double x = Math.random() * (w - (10*maxSize / 2));
        double y = Math.random() * (h - (maxSize / 2));
        stars[i] = createStar(x, y, size);
    }

    private Path2D.Float createStar(double x, double y, double size) {
        Path2D.Float star = new Path2D.Float();
        double angle = Math.toRadians(36);
        double xCenter = x + size / 2;
        double yCenter = y + size / 2;
        double outerRadius = size / 2;
        double innerRadius = outerRadius / 2;

        for (int i = 0; i < 10; i++) {
            double xOuter = xCenter + Math.cos(i * angle) * ((i % 2 == 0) ? outerRadius : innerRadius);
            double yOuter = yCenter + Math.sin(i * angle) * ((i % 2 == 0) ? outerRadius : innerRadius);

            if (i == 0) {
                star.moveTo(xOuter, yOuter);
            } else {
                star.lineTo(xOuter, yOuter);
            }
        }

        star.closePath();
        return star;
    }

    private void doStep(int w, int h) {
        for (int i = 0; i < stars.length; i++) {
            double size = stars[i].getBounds2D().getWidth() + 1;
            //estroke[i] += 0.25f;
            size += 30;

            if (size > 130) {
                size = 30;
                posRandStars(i, size, w, h);
                rightCount++;
            } else {
                double x = stars[i].getBounds2D().getX() + 1;
                double y = stars[i].getBounds2D().getY();
                stars[i] = createStar(x, y, size);
                rightCount++;
            }
        }




    }

    private void drawStars(Graphics2D g2d) {
        for (int i = 0; i < stars.length; i++) {
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHints(rh);
            g2d.setColor(colors[i % colors.length]);
            g2d.fill(stars[i]);
        }
        g2d.dispose();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        Dimension size = getSize();
        doStep(size.width, size.height);
        drawStars(g2d);
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
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Stars");
                frame.add(new ss());
                frame.setSize(1100, 1100);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FireworksDisplay extends JPanel implements ActionListener {
    private Timer timer;
    private ArrayList<Firework> fireworks;
    private Random random;

    public FireworksDisplay() {
        this.setBackground(Color.BLACK);
        this.timer = new Timer(50, this);
        this.fireworks = new ArrayList<>();
        this.random = new Random();
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Firework firework : fireworks) {
            firework.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (random.nextInt(10) > 7) {
            fireworks.add(new Firework(random.nextInt(getWidth()), getHeight(), random));
        }
        for (Firework firework : fireworks) {
            firework.update();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fireworks Display");
        FireworksDisplay fireworksDisplay = new FireworksDisplay();
        frame.add(fireworksDisplay);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Firework {
    private int x, y;
    private int[] dx, dy;
    private Color color;
    private Random random;
    private int age;
    private final int maxAge = 50;

    public Firework(int x, int y, Random random) {
        this.x = x;
        this.y = y;
        this.random = random;
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        int numParticles = 20;
        this.dx = new int[numParticles];
        this.dy = new int[numParticles];
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * i / numParticles;
            double speed = random.nextDouble() * 10;
            dx[i] = (int) (Math.cos(angle) * speed);
            dy[i] = (int) (Math.sin(angle) * speed);
        }
        this.age = 0;
    }

    public void update() {
        for (int i = 0; i < dx.length; i++) {
            dx[i] *= 0.95;
            dy[i] *= 0.95;
        }
        age++;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        if (age < maxAge) {
            for (int i = 0; i < dx.length; i++) {
                g.fillOval(x + dx[i], y + dy[i], 5, 5);
            }
        }
    }
}

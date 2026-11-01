package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel extends JPanel {

    private Moon moon;
    private Rocket rocket;
    private ArrayList<Star> stars = new ArrayList<>();
    private boolean starsGenerated = false;

    public DrawPanel(){
        moon = new Moon(600,100,100);
        rocket = new Rocket();

        Timer timer = new Timer(40, e -> {
            rocket.updateFlame();
            rocket.updatePosition();

            for (Star star : stars) {
                star.update();
            }

            repaint();
        });

        timer.start();

    }

    private void generateStars(int count) {
        Random random = new Random();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        for (int i = 0; i < count; i++) {
            double x = random.nextInt(Math.max(1, panelWidth));
            double y = random.nextInt(Math.max(1, panelHeight));

            stars.add(new Star(x, y, random));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

       

        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient =
                new GradientPaint(0, 0,
                        new Color(15,25,80), 0, height, new Color(50,30,90));

        g2.setPaint(gradient);

        g2.fillRect(0, 0, width, height);


        if (!starsGenerated && width > 0 && height > 0) {
            generateStars(150);
            starsGenerated = true;
        }

        // Рисуем звёзды поверх фона
        for (Star star : stars) {
            star.draw(g2);
        }

        Planet mars = new Planet(
                300, 200, 40,
                new Color(190, 80, 60)
        );

        moon.draw(g2);
        rocket.draw(g2);
        mars.draw(g2);




        g2.dispose();
    }
}

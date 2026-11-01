package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel extends JPanel {

    private Rocket rocket;
    private ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<Planet> planets = new ArrayList<>();

    private boolean starsGenerated = false;

    public DrawPanel() {
        rocket = new Rocket(400,400,250);
        createPlanets();

        Timer timer = new Timer(40, e -> {
            rocket.updateFlame();
            rocket.updatePosition();

            for (Star star : stars) {
                star.update();
                star.move(0.3, getWidth(),getHeight());
            }

            for (Planet planet : planets) {
                planet.updateGlow();
            }
            repaint();
        });
        timer.start();

    }

    private void createPlanets(){
        Planet mars = new Planet(
                300, 200, 40,
                new Color(190, 80, 60)
        );
        Planet saturn = new RingPlanet(
                150, 400, 50,
                new Color(220, 190, 130), // Цвет планеты
                new Color(120, 160, 190)  // Цвет кольца
        );

        Planet moon = new Moon(650, 100, 65);

        planets.add(mars);
        planets.add(saturn);
        planets.add(moon);
    }

    private void generateStars(int count) {
        Random random = new Random();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        for (int i = 0; i < count; i++) {
            double x = random.nextInt(Math.max(1, panelWidth));
            double y = random.nextInt(Math.max(1, panelHeight));

            stars.add(new Star(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();


        // background
        GradientPaint gradient =
                new GradientPaint(0, 0,
                        new Color(15,25,80), 0, height, new Color(50,30,90));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);


        if (!starsGenerated && width > 0 && height > 0) {
            generateStars(150);
            starsGenerated = true;
        }

        for (Star star : stars) {
            star.draw(g2);
        }

        for (Planet planet : planets) {
            planet.draw(g2);
        }

        rocket.draw(g2);


        g2.dispose();
    }
}

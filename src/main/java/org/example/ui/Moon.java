package org.example.ui;

import java.awt.*;

public class Moon implements Drawable {

    private int x;
    private int y;
    private int radius;

    public Moon(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g2) {

        float[] dist = {
                0.0f,
                0.45f,
                0.75f,
                1.0f
        };

        Color[] colors = {
                new Color(255, 255, 230, 120),
                new Color(255, 255, 230, 60),
                new Color(255, 255, 230, 25),
                new Color(255, 255, 230, 5)
        };

        RadialGradientPaint glow = new RadialGradientPaint(
                x + radius / 2f,
                y + radius / 2f,
                radius * 0.9f,
                dist,
                colors
        );

        g2.setPaint(glow);

        g2.fillOval(
                x - radius / 2,
                y - radius / 2,
                radius * 2,
                radius * 2
        );



        g2.setColor(new Color(220,220,210));
        g2.fillOval(x, y, radius, radius);

        g2.setColor(new Color(180,180,175));

        g2.fillOval(
                x + radius / 5,
                y + radius / 4,
                radius / 7,
                radius / 7
        );

        g2.fillOval(
                x + radius / 2,
                y + radius / 5,
                radius / 5,
                radius / 5
        );

        g2.fillOval(
                x + radius / 3,
                y + radius * 2 / 3,
                radius / 8,
                radius / 8
        );

        g2.fillOval(
                x + radius / 7,
                y + radius * 3 / 5,
                radius / 10,
                radius / 10
        );


        g2.fillOval(
                x + radius * 3 / 5,
                y + radius * 3 / 4,
                radius / 12,
                radius / 12
        );




    }
}

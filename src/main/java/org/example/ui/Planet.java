package org.example.ui;

import java.awt.*;

public class Planet implements Drawable{


    protected double x, y;
    protected int radius;
    protected Color color;

    public Planet(double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }


    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();

        float px = (float) (x - radius);
        float py = (float) (y - radius);
        float diameter = radius * 2;

        // Основной градиент: свет сверху слева, тень снизу справа
        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point((int)(x - radius * 0.35), (int)(y - radius * 0.4)),
                radius * 1.8f,
                new float[]{0.0f, 0.45f, 0.75f, 1.0f},
                new Color[]{
                        color.brighter().brighter(),
                        color.brighter(),
                        color,
                        color.darker().darker()
                }
        );

        g.setPaint(gradient);
        g.fillOval((int)px, (int)py, (int)diameter, (int)diameter);

        // Небольшой световой блик
        g.setColor(new Color(255, 255, 255, 35));
        g.fillOval(
                (int)(x - radius * 0.55),
                (int)(y - radius * 0.65),
                (int)(radius * 0.65),
                (int)(radius * 0.4)
        );

        g.dispose();
    }

}


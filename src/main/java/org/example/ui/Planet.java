package org.example.ui;

import java.awt.*;


import java.awt.*;

public class Planet implements Drawable {

    protected double x, y;
    protected int radius;
    protected Color color;

    protected float glowIntensity = 0.5f;
    protected float glowDirection = 1; // 1 or -1

    public Planet(double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void updateGlow() {
        glowIntensity += glowDirection * 0.025f;

        if (glowIntensity >= 1.0f) {
            glowIntensity = 1.0f;
            glowDirection = -1;
        } else if (glowIntensity <= 0.35f) {
            glowIntensity = 0.35f;
            glowDirection = 1;
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();

        // Свечение
        int alpha = (int)(glowIntensity * 150);

        RadialGradientPaint glow = new RadialGradientPaint(
                new Point((int)x, (int)y),
                radius * 2.8f,
                new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha),
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha / 2),
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)
                }
        );

        g.setPaint(glow);
        g.fillOval(
                (int)(x - radius * 2.8),
                (int)(y - radius * 2.8),
                (int)(radius * 5.6),
                (int)(radius * 5.6)
        );

        // Градиент самой планеты
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
        g.fillOval(
                (int)(x - radius),
                (int)(y - radius),
                radius * 2,
                radius * 2
        );

        g.dispose();
    }
}


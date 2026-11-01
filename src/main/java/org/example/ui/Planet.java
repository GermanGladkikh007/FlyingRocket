package org.example.ui;

import java.awt.*;

public class Planet implements Drawable{


    protected double x, y;
    protected int radius;
    protected Color color;
    protected float glowIntensity = 0.5f;
    protected float glowDirection = 1;

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

        float px = (float) (x - radius);
        float py = (float) (y - radius);
        float diameter = radius * 2;


        // Динамическое свечение
        int alpha = (int)(glowIntensity * 150);

        RadialGradientPaint glow = new RadialGradientPaint(
                new Point((int)x, (int)y),
                radius * 2.2f,
                new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{
                        new Color(
                                color.getRed(),
                                color.getGreen(),
                                color.getBlue(),
                                alpha
                        ),
                        new Color(
                                color.getRed(),
                                color.getGreen(),
                                color.getBlue(),
                                alpha / 2
                        ),
                        new Color(
                                color.getRed(),
                                color.getGreen(),
                                color.getBlue(),
                                0
                        )
                }
        );

        g.setPaint(glow);
        g.fillOval(
                (int)(x - radius * 2.2),
                (int)(y - radius * 2.2),
                (int)(radius * 4.4),
                (int)(radius * 4.4)
        );

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


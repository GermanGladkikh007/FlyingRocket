package org.example.ui;

import java.awt.*;

public class Moon extends Planet {

    public Moon(double x, double y, int radius) {
        super(x, y, radius, new Color(190, 195, 200));
    }

    @Override
    public void draw(Graphics2D g2) {
        // Рисуем Луну с градиентом и свечением
        super.draw(g2);

        Graphics2D g = (Graphics2D) g2.create();

        // Кратеры
        drawCrater(g, -0.35, -0.30, 0.22);
        drawCrater(g, 0.30, -0.15, 0.15);
        drawCrater(g, -0.10, 0.35, 0.18);
        drawCrater(g, 0.42, 0.35, 0.10);
        drawCrater(g, -0.55, 0.20, 0.09);

        g.dispose();
    }

    private void drawCrater(Graphics2D g, double offsetX,
                            double offsetY, double size) {

        int craterRadius = (int)(radius * size);

        int cx = (int)(x + radius * offsetX);
        int cy = (int)(y + radius * offsetY);

        // Внутренняя тёмная часть кратера
        g.setColor(new Color(105, 110, 120, 100));
        g.fillOval(
                cx - craterRadius,
                cy - craterRadius,
                craterRadius * 2,
                craterRadius * 2
        );

        // Светлая граница кратера
        g.setColor(new Color(245, 245, 230, 110));
        g.setStroke(new BasicStroke(2));

        g.drawArc(
                cx - craterRadius,
                cy - craterRadius,
                craterRadius * 2,
                craterRadius * 2,
                20, 180
        );
    }
}

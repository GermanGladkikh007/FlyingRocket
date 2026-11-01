package org.example.ui;

import java.awt.*;
import java.util.Random;

public class RingPlanet extends Planet {
    private Color ringColor;
    private double ringAngle;

    public RingPlanet(double x, double y, int radius,
                      Color color, Color ringColor) {
        super(x, y, radius, color);
        this.ringColor = ringColor;

        // Случайный наклон кольца от -35 до 35 градусов
        Random random = new Random();
        ringAngle = Math.toRadians(random.nextInt(71) - 35);
    }


    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();

        // Поворачиваем кольцо вокруг центра планеты
        g.rotate(ringAngle, x, y);

        int ringWidth = (int)(radius * 3.6);
        int ringHeight = (int)(radius * 0.9);

        int ringX = (int)(x - ringWidth / 2.0);
        int ringY = (int)(y - ringHeight / 2.0);

        // Градиент кольца
        GradientPaint ringGradient = new GradientPaint(
                ringX, ringY, ringColor.brighter(),
                ringX, ringY + ringHeight, ringColor.darker()
        );

        g.setPaint(ringGradient);
        g.setStroke(new BasicStroke(9));

        // Создаём эллипс кольца
        Shape ring = new java.awt.geom.Ellipse2D.Double(
                ringX, ringY, ringWidth, ringHeight
        );

        // Отрисовываем заднюю половину кольца
        Shape backHalf = new Rectangle(
                ringX - 5, ringY - 5,
                ringWidth + 10, ringHeight / 2 + 5
        );

        Graphics2D back = (Graphics2D) g.create();
        back.clip(backHalf);
        back.draw(ring);
        back.dispose();

        // Рисуем планету поверх задней части кольца
        g.dispose();
        super.draw(g2);

        // Отрисовываем переднюю половину кольца
        Graphics2D front = (Graphics2D) g2.create();
        front.rotate(ringAngle, x, y);

        front.setPaint(ringGradient);
        front.setStroke(new BasicStroke(9));

        Shape frontHalf = new Rectangle(
                ringX - 5, ringY + ringHeight / 2,
                ringWidth + 10, ringHeight / 2 + 5
        );

        front.clip(frontHalf);
        front.draw(ring);
        front.dispose();
    }

}
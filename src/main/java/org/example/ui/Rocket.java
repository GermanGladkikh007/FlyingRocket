package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Rocket implements Drawable {


    private double x1;
    private double x2;
    private double y0;
    private double x0;
    private double radius;
    private double y1;
    private double y2;

    private double flameLength = 40;
    private double flameDirection = 1;


    private double rocketOffsetY = 0;
    private double rocketDirection = -1;


    public Rocket(double y0, double x0, double radius) {
        this.y0 = y0;
        this.x0 = x0;
        x1 = x0/2 - x0 * 0.02;
        x2 = x0 + x0/2 + x0 * 0.02;
        this.radius = radius;

        y1 = y0 - Math.sqrt(4 * radius * radius - (x2-x1)*(x2-x1)) / 2; // координата кончика ракеты
        y2 = y0 + Math.sqrt(4 * radius * radius - (x2-x1)*(x2-x1)) / 3.8; // координата низа корпуса
    }


    // корпус ракеты
    private void drawBody(Graphics2D g2) {
        g2.setColor(new Color(245, 245, 250));


        Path2D path2D = new Path2D.Double();
        path2D.moveTo(x1 + Math.sqrt(radius*radius - (y2-y0)*(y2-y0)), y2);
        for(double y = y2; y >= y1; y-=0.01){
            double x = x1 + Math.sqrt(radius*radius - (y-y0)*(y-y0));
            path2D.lineTo(x, y);
        }
        for(double y = y1; y <= y2; y+=0.01){
            double x = x2 - Math.sqrt(radius*radius - (y-y0)*(y-y0));
            path2D.lineTo(x, y);
        }

        path2D.closePath();
        g2.fill(path2D);


        Shape oldClip = g2.getClip();
        g2.clip(path2D);

        double shadowWidth = 12;

        Path2D shadow = new Path2D.Double();
        shadow.moveTo(x2 - Math.sqrt(
                radius * radius - (y2 - y0) * (y2 - y0)
        ),y2);

// Внешний край полоски
        for (double y = y2; y >= y1; y -= 0.5) {
            double dx = Math.sqrt(
                    radius * radius - (y - y0) * (y - y0)
            );

            shadow.lineTo(x1 + dx, y);
        }

// Внутренний край полоски — идём обратно
        for (double y = y1; y <= y2; y += 0.5) {
            double innerRadius = radius - shadowWidth;

            double dx = Math.sqrt(
                    innerRadius * innerRadius - (y - y0) * (y - y0)
            );

            shadow.lineTo(x1 + dx, y);
        }

        shadow.closePath();

        g2.setColor(new Color(200, 200, 210));
        g2.fill(shadow);

        g2.setClip(oldClip);

    }

    private void drawWing(Graphics2D g2) {
        g2.setColor(new Color(245, 245, 250));
        double y_1 = y0;
        double x_1 = x1 + Math.sqrt(radius*radius - (y_1-y0)*(y_1-y0));

        double y_2 = y0 + Math.sqrt(4 * radius * radius - (x2-x1)*(x2-x1)) / 4;
        double x_2 = x_1 + 30;

        double y_3 = y_2 + 15;
        double x_3 = x_2 + 5;


        double a = (y_2 - y_3
                - (y_1 - y_2) * (x_2 - x_3) / (x_1 - x_2))
                / ((x_2 - x_3) * (x_3 - x_1));

        double b = (y_1 - y_2
                - a * (x_1*x_1 - x_2*x_2))
                / (x_1 - x_2);

        double c = y_1 - a*x_1*x_1 - b*x_1;

        Path2D path2D = new Path2D.Double();
        path2D.moveTo(x_1,y_1);

        for(double x = x_1; x <= x_3; x+=0.01){
            double y = a*x*x + b*x + c;
            path2D.lineTo(x, y);
        }
        path2D.lineTo(x1 + Math.sqrt(radius*radius - (y_2-y0)*(y_2-y0)),y_2);

        for(double y = y_2; y >= y_1; y-=0.01){
            double x = x1+Math.sqrt(radius*radius - (y-y0)*(y-y0));
            path2D.lineTo(x, y);
        }
        path2D.closePath();
        g2.fill(path2D);


        g2.setColor(new Color(175, 175, 180));
        g2.setStroke(new BasicStroke(2f));

        Path2D border = new Path2D.Double();
        boolean first = true;

        for (double y = y_1; y <= y_2; y += 0.1) {
            double x = x1 + Math.sqrt(radius * radius - (y - y0) * (y - y0));

            if (first) {
                border.moveTo(x, y);
                first = false;
            } else {
                border.lineTo(x, y);
            }
        }

        g2.draw(border);

//        Shape oldClip = g2.getClip();
//        g2.clip(path2D);
    }


    private void drawEngine(Graphics2D g2,double engineBottomY) {
        double engineTopY = y2;


        // Ширина сопла сверху и снизу
        double topHalfWidth = 27;
        double bottomHalfWidth = 15;

        // Центр ракеты — середина между центрами дуг корпуса
        double centerX = (x1 + x2) / 2.0;

        Path2D nozzle = new Path2D.Double();

        nozzle.moveTo(centerX - topHalfWidth, engineTopY);
        nozzle.lineTo(centerX + topHalfWidth, engineTopY);
        nozzle.lineTo(centerX + bottomHalfWidth, engineBottomY);
        nozzle.lineTo(centerX - bottomHalfWidth, engineBottomY);
        nozzle.closePath();

        // Основная часть сопла
        g2.setColor(new Color(125, 125, 135));
        g2.fill(nozzle);

        // Нижний ободок
        g2.setColor(new Color(190, 190, 200));
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(
                (int)(centerX - bottomHalfWidth),
                (int)engineBottomY,
                (int)(centerX + bottomHalfWidth),
                (int)engineBottomY
        );
    }


    private void drawWindow(Graphics2D g2) {
        // Центр ракеты
        double centerX = (x1 + x2) / 2.0;

        // Положение иллюминатора
        int r = 18;
        int x = (int) centerX - r;
        int y = (int) (y0 - 65) - r;

        // Внешний металлический ободок
        g2.setColor(new Color(145, 145, 155));
        g2.fillOval(x, y, r * 2, r * 2);

        // Светлая внутренняя окантовка
        g2.setColor(new Color(220, 220, 230));
        g2.fillOval(x + 3, y + 3, r * 2 - 6, r * 2 - 6);

        // Стекло
        g2.setColor(new Color(55, 105, 155));
        g2.fillOval(x + 6, y + 6, r * 2 - 12, r * 2 - 12);

        // Блик на стекле
        g2.setColor(new Color(190, 225, 245));
        g2.fillOval(x + 9, y + 8, 5, 7);
    }

    private void drawFlame(Graphics2D g2, double engineBottomY, double flameLength) {
        double centerX = (x1 + x2) / 2.0;

        // Внешнее оранжевое пламя
        Path2D flame = new Path2D.Double();

        flame.moveTo(centerX - 13, engineBottomY);

        flame.curveTo(
                centerX - 25, engineBottomY + flameLength * 0.33,
                centerX - 15, engineBottomY + flameLength * 0.70,
                centerX, engineBottomY + flameLength
        );

        flame.curveTo(
                centerX + 15, engineBottomY + flameLength * 0.70,
                centerX + 25, engineBottomY + flameLength * 0.33,
                centerX + 13, engineBottomY
        );

        flame.closePath();

        g2.setColor(new Color(255, 125, 25));
        g2.fill(flame);

        // Внутреннее жёлтое пламя
        Path2D innerFlame = new Path2D.Double();

        innerFlame.moveTo(centerX - 7, engineBottomY);

        innerFlame.curveTo(
                centerX - 12, engineBottomY + flameLength * 0.25,
                centerX - 7, engineBottomY + flameLength * 0.45,
                centerX, engineBottomY + flameLength * 0.67
        );

        innerFlame.curveTo(
                centerX + 7, engineBottomY + flameLength * 0.45,
                centerX + 12, engineBottomY + flameLength * 0.25,
                centerX + 7, engineBottomY
        );

        innerFlame.closePath();

        g2.setColor(new Color(255, 220, 65));
        g2.fill(innerFlame);
    }

    public void updateFlame() {
        flameLength += flameDirection * 1.5;

        if (flameLength >= 55) {
            flameLength = 55;
            flameDirection = -1;
        } else if (flameLength <= 30) {
            flameLength = 30;
            flameDirection = 1;
        }
    }

    public void updatePosition() {
        rocketOffsetY += rocketDirection * 0.7;

        if (rocketOffsetY <= -12) {
            rocketOffsetY = -12;
            rocketDirection = 1;
        } else if (rocketOffsetY >= 0) {
            rocketOffsetY = 0;
            rocketDirection = -1;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();

        // Перемещаем всю ракету по вертикали
        g.translate(0, rocketOffsetY);

        // Рисуем пламя первым, чтобы оно было позади сопла
        double engineBottomY = y2 + 15;
        drawFlame(g, engineBottomY, flameLength);


        drawBody(g);
        drawEngine(g,engineBottomY);
        drawWing(g);
        drawWindow(g);

        AffineTransform oldTransform = g.getTransform();
        g.translate(2 * x0, 0);
        g.scale(-1, 1);
        drawWing(g); // зеркальное крыло
        g.setTransform(oldTransform);

        g.dispose();
    }
}

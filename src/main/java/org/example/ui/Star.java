package org.example.ui;


import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Star implements Drawable{
    private double x;
    private double y;
    private int size;
    private float brightness;

    private float minBrightness;
    private float maxBrightness;
    private float brightnessDirection;

    private Random random = new Random();

    public Star(double x, double y) {
        this.x = x;
        this.y = y;

        // Размер звезды: от 2 до 5 пикселей
        size = random.nextInt(4) + 2;

        // Индивидуальная яркость каждой звезды
        minBrightness = 0.3f;
        maxBrightness = 1.0f;
        brightness = minBrightness
                + random.nextFloat() * (maxBrightness - minBrightness);

        brightnessDirection = random.nextBoolean() ? 1 : -1;
    }

    public void move(double speed, int panelWidth, int panelHeight) {
        y += speed;

        if (y > panelHeight) {
            Random random = new Random();

            y = -size;
            x = random.nextInt(Math.max(1, panelWidth));
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int value = (int) (255 * brightness);

        g2.setColor(new Color(value, value, value));
        g2.fillOval(
                (int) x,
                (int) y,
                size,
                size
        );
    }

    public void update() {
        brightness += brightnessDirection * 0.015f;

        if (brightness >= maxBrightness) {
            brightness = maxBrightness;
            brightnessDirection = -1;
        } else if (brightness <= minBrightness) {
            brightness = minBrightness;
            brightnessDirection = 1;
        }
    }
}

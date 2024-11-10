import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Circuit extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Circuit.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        noFill();
        noLoop();
    }

    @Override
    public void draw() {
        for (int k = 0; k < NUMBER_OF_ITERATIONS; k++) {
            float x;
            float y;
            do {
                x = width / 2f + constrain(HORIZONTAL_DEVIATION_FACTOR * randomGaussian(), -width / 2f, width / 2f);
                y = height / 2f + constrain(VERTICAL_DEVIATION_FACTOR * randomGaussian(), -height / 2f, height / 2f);
            } while (x < 0 || x > width || y < 0 || y > height);
            PVector p = new PVector(x, y);

            for (int l = 0; l < LINE_LENGTH; l++) {
                point(p.x + SCATTER_FACTOR * randomGaussian(), p.y + SCATTER_FACTOR * randomGaussian());
                float cellSize = CELL_SIZE * (1 + floor(dist(p.x, p.y, width / 2f, height / 2f) / 50));
                PVector speed = PVector.fromAngle(floor(NUMBER_OF_DIRECTIONS * noise(NOISE_ANGLE_FACTOR * floor(p.x / cellSize),
                        NOISE_ANGLE_FACTOR * floor(p.y / cellSize))) / NUMBER_OF_DIRECTIONS * TWO_PI);
                speed.setMag(noise(NOISE_MAGNITUDE_FACTOR * p.x, NOISE_MAGNITUDE_FACTOR * p.y));
                p.add(speed);
                if (p.x < 0 || p.x > width || p.y < 0 || p.y > height) {
                    break;
                }
            }
        }

        saveSketch(this);
    }
}

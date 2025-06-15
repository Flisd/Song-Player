import java.awt.*;
import java.util.*;

public class Button {
    String text;
    int x;
    int y;
    int height;
    int width;

    boolean previousMousePressed = false;
    boolean enabled = true;
    boolean clicked = false;
    boolean particlesEnabled;

    int textX;
    int textY;

    Color background;

    boolean canSpin = false;
    boolean spinClockwise = false;
    boolean spinCounterClockwise = false;
    double spinSpeed = 0.1;
    double angle = 0;

    boolean isGradient = false;
    Color colorA;
    Color colorB;

    boolean rounded;

    boolean canChangeSize = false;
    double sizeMax = 1.05;
    double sizeMin = 1.0;
    double size = sizeMin;
    double sizeSpeed = 0.02;
    boolean increasing = true;

    boolean canRotate = false;
    boolean isRotateAboutPivotClockwise = true;
    double omega = 0;

    double deltaOmega = 2;

    ArrayList<Particle> particles = new ArrayList<>();
    Random random = new Random();

    public Button(int x, int y, int width, int height, String text) {
        this.text = text;
        this.x = x;
        this.y = y;
        textX = x;
        textY = y;
        this.width = width;
        this.height = height;
        background = Color.white;
        colorA = Color.white;
        colorB = Color.WHITE;
    }

    public static void drawRotatedRectangleAboutPivot(double centerX, double centerY, double halfWidth, double halfHeight, double angleDegrees, boolean isClockwise) {
        if (isClockwise)
            angleDegrees = -angleDegrees;


        double angleRadians = Math.toRadians(angleDegrees);
        double cosA = Math.cos(angleRadians);
        double sinA = Math.sin(angleRadians);


        double[][] corners = {
                {-halfWidth, -halfHeight},
                { halfWidth, -halfHeight},
                { halfWidth,  halfHeight},
                {-halfWidth,  halfHeight}
        };

        double[] xPoints = new double[4];
        double[] yPoints = new double[4];

        for (int i = 0; i < 4; i++) {
            double x = corners[i][0];
            double y = corners[i][1];
            xPoints[i] = centerX + (x * cosA - y * sinA);
            yPoints[i] = centerY + (x * sinA + y * cosA);
        }

        StdDraw.polygon(xPoints, yPoints);
    }


    public Button(int x, int y, int width, int height, String text, Color background) {
        this(x, y, width, height, text);
        this.background = background;
    }

    public Button(int x, int y, int width, int height) {
        this.text = "";
        this.x = x;
        this.y = y;
        textX = x;
        textY = y;

        this.width = width;
        this.height = height;
        background = Color.white;
    }

    public void setRotate(boolean canRotate){
        this.canRotate = canRotate;
    }

    public void setRotateClockwise(boolean isRotateAboutPivotClockwise){
        this.isRotateAboutPivotClockwise = isRotateAboutPivotClockwise;
    }

    public void setDeltaOmega(double deltaOmega){
        this.deltaOmega = deltaOmega;
    }

    public void draw() {
        double rotatedX = x;
        double rotatedY = y;

        if (canSpin) {
            rotatedX = x + Math.cos(angle) * width / 2;
            rotatedY = y + Math.sin(angle) * height / 2;
        }

        if (rounded) {
            drawRoundedRectangle(rotatedX, rotatedY, width * size, height * size, 50);
        } if (isGradient && canRotate) {
            // Loop over the height of the button to draw gradient lines
            for (int i = 0; i <= height * size; i++) {
                // Calculate the gradient's position (t for interpolation between colorA and colorB)
                double t = (double) i / (height * size);

                // Interpolate between colorA and colorB based on the position in the gradient
                int red = (int) (colorA.getRed() * (1 - t) + colorB.getRed() * t);
                int green = (int) (colorA.getGreen() * (1 - t) + colorB.getGreen() * t);
                int blue = (int) (colorA.getBlue() * (1 - t) + colorB.getBlue() * t);
                Color gradientColor = new Color(red, green, blue);

                // Calculate the start and end points of the gradient line
                double startX = rotatedX - width * size / 2;
                double startY = rotatedY - height * size / 2 + i;
                double endX = rotatedX + width * size / 2;
                double endY = rotatedY - height * size / 2 + i;

                // Apply the rotation using omega (the rotation angle)
                double angleRadians = Math.toRadians(omega); // Convert omega to radians
                double cosA = Math.cos(angleRadians);
                double sinA = Math.sin(angleRadians);

                // Rotate the start and end points of the gradient line
                double rotatedStartX = cosA * (startX - rotatedX) - sinA * (startY - rotatedY) + rotatedX;
                double rotatedStartY = sinA * (startX - rotatedX) + cosA * (startY - rotatedY) + rotatedY;

                double rotatedEndX = cosA * (endX - rotatedX) - sinA * (endY - rotatedY) + rotatedX;
                double rotatedEndY = sinA * (endX - rotatedX) + cosA * (endY - rotatedY) + rotatedY;

                // Set the pen color for the current gradient step
                StdDraw.setPenColor(gradientColor);

                // Draw the rotated gradient line
                StdDraw.line(rotatedStartX, rotatedStartY, rotatedEndX, rotatedEndY);
            }
            StdDraw.setPenRadius(0.007);
        } else if (isGradient) {
            for (int i = 0; i <= height * size; i++) {
                double t = (double) i / (height * size);
                int red = (int) (colorA.getRed() * (1 - t) + colorB.getRed() * t);
                int green = (int) (colorA.getGreen() * (1 - t) + colorB.getGreen() * t);
                int blue = (int) (colorA.getBlue() * (1 - t) + colorB.getBlue() * t);
                StdDraw.setPenColor(new Color(red, green, blue));
                StdDraw.line(rotatedX - width * size / 2, rotatedY - height * size / 2 + i, rotatedX + width * size / 2, rotatedY - height * size / 2 + i);
                StdDraw.setPenColor(Color.black);
                StdDraw.setPenRadius(0.003);
                StdDraw.rectangle(rotatedX, rotatedY, (width * size) / 2, (height * size) / 2);
                StdDraw.setPenRadius(0.007);
            }
        } else if (canRotate) {
            StdDraw.setPenColor(background);
            drawRotatedRectangleAboutPivot(rotatedX, rotatedY, (width * size) / 2, (height * size) / 2, omega, isRotateAboutPivotClockwise);
        }
        else {
            StdDraw.setPenColor(background);
            StdDraw.filledRectangle(rotatedX, rotatedY, (width * size) / 2, (height * size) / 2);
        }

        // Draw text last (rotation not applied to text for simplicity)
        StdDraw.setPenColor(Color.black);
        StdDraw.text(rotatedX, rotatedY, text);
    }


    public void drawRoundedRectangle(double x, double y, double width, double height, int numSides) {
        double[] xCords = new double[numSides];
        double[] yCords = new double[numSides];

        for (int i = 0; i < numSides; i++) {
            double angle = 2 * Math.PI * i / numSides;
            xCords[i] = x + width / 2 * Math.cos(angle);
            yCords[i] = y + height / 2 * Math.sin(angle);
        }

        StdDraw.polygon(xCords, yCords);
    }



    public void setGradient(Color colorA, Color colorB) {
        this.isGradient = true;
        this.colorA = colorA;
        this.colorB = colorB;
    }

    public void setCanChangeSize(boolean canChangeSize) {
        this.canChangeSize = canChangeSize;
    }

    public void update() {
        if (enabled)
            trackMouseClicks(x - (width >> 1), x + (width >> 1), y - (height >> 1), y + (height >> 1));
        updateParticles();
        if (canSpin) {
            if (spinClockwise) {
                angle += spinSpeed;
            } else if (spinCounterClockwise) {
                angle -= spinSpeed;
            }
        }

        omega += deltaOmega;
        if (omega >= 360) omega -= 360;
        if (omega < 0) omega += 360;


        if (canChangeSize) {
            if (increasing) {
                size += sizeSpeed;
                if (size >= sizeMax) {
                    increasing = false;
                }
            } else {
                size -= sizeSpeed;
                if (size <= sizeMin) {
                    increasing = true;
                }
            }
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public String getText() {
        return text;
    }

    public void setRounded(boolean rounded) {
        this.rounded = rounded;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSpinCounterClockwise(boolean spinCounterClockwise) {
        this.spinCounterClockwise = spinCounterClockwise;
        spinClockwise = !spinCounterClockwise;
    }

    public void setSpinClockwise(boolean spinClockwise) {
        this.spinClockwise = spinClockwise;
        spinCounterClockwise = !spinClockwise;
    }

    public void setCanSpin(boolean canSpin) {
        this.canSpin = canSpin;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void trackMouseClicks(double x1, double x2, double y1, double y2) {
        // Check if the mouse is currently pressed
        boolean isMousePressed = StdDraw.isMousePressed();
        if (!isMousePressed) {
            previousMousePressed = clicked;
            clicked = false;
            return;
        }

        // Get the mouse coordinates
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();

        // Define your range (adjust as needed)
        double minX = x1;
        double maxX = x2;
        double minY = y1;
        double maxY = y2;

        // Check if the mouse click is within the desired range
//        System.out.printf("%d %d %d %d %n", x, y, width, height);
//        System.out.printf("%f %f . %f %f %f %f %n", mouseX, mouseY, minX, maxX, minY, maxY);
        if (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY) {

//            System.out.println("clicked inside " + text);--

            if (!previousMousePressed) {
                previousMousePressed = clicked;
                clicked = true;
                if (particlesEnabled)
                    createParticles();
            } else {
                previousMousePressed = clicked;
                clicked = false;
            }
        } else {

//            System.out.println("clicked out");
            previousMousePressed = clicked;
            clicked = false;
        }

    }

    public void setParticlesEnabled(boolean particlesEnabled) {
        this.particlesEnabled = particlesEnabled;
    }

    public void createParticles() {
        for (int i = 0; i < 15; i++) { // change 100 to 15
            double angle = 2 * Math.PI * random.nextDouble();
            double speed = 15 * random.nextDouble();
            double dx = Math.cos(angle) * speed;
            double dy = Math.sin(angle) * speed;
            Color particleColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            particles.add(new Particle(x, y, dx, dy, particleColor));
        }
    }

    public void drawProgressBar(double progress, Color fillColor) {
        int barWidth = (int) (this.width * Math.max(0, Math.min(1, progress)));
        StdDraw.setPenColor(fillColor);
        StdDraw.filledRectangle(this.x + barWidth / 2.0 - this.width / 2.0, this.y, barWidth, this.height/2);
    }

    public void updateParticles() {
        for (Particle p : particles) {
            p.update();
            p.draw();
        }
        particles.removeIf(p -> p.isOld() || !p.isVisible());
        StdDraw.setPenColor(Color.black); // reset pen color to black
    }

    public void setTextX(int textX) {
        this.textX = textX;
    }

    public void setTextY(int textY) {
        this.textY = textY;
    }

    public void setEnabled(boolean c) {
        enabled = c;
    }

    public void setText(String c) {
        text = c;
    }

    public void canSpin(boolean b) {
        canSpin = b;
    }
}

class Particle {
    double x, y;
    double dx, dy;
    long timestamp;
    Color color;

    public Particle(double x, double y, double dx, double dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isOld() {
        return System.currentTimeMillis() - timestamp > 1500;
    }

    public boolean isVisible() {
        return x >= 0 && x <= Main.width && y >= 0 && y <= Main.height;
    }


    public void update() {
        x += dx;
        y += dy;
        dy -= 0.1; // gravity
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, 2);
    }
}
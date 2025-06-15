public class Main {
    public static int height = Simulation.size;
    public static int width = Simulation.size;
    public static void main(String[] args) {
        int size = Simulation.size;
        // set up canvas
        StdDraw.setCanvasSize(size, size); //set up drawing canvas
        StdDraw.setXscale((double) -size / 2, (double) size / 2); // Center is now <0, 0>
        StdDraw.setYscale((double) -size / 2, (double) size / 2);
        StdDraw.setPenRadius(0.01);

        System.out.println("Starting the simulation...");
        Simulation simulation = new Simulation();

        StdDraw.enableDoubleBuffering();

        while(true) {
            StdDraw.clear(Simulation.backgroundColor);

            simulation.run();

            StdDraw.pause(10);
            StdDraw.show();
        }
    }

}
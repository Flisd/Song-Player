public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the simulation...");
        Simulation simulation = new Simulation();

        while(true) {
            simulation.run();
        }
    }
}
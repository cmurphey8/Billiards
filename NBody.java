//*******************************************************************
//
//   File: NBody.jva
//
//*******************************************************************
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class NBody {

    public static final String PLANETS_FILE = "planets.txt";

    // animation pause (in miliseconds)
    public static final int DELAY = 200;

    // music (2001 theme)
    public static final String MUSIC = "2001theme.wav";

    // background image
    public static final String BACKGROUND = "starfield.jpg";

    // gravitational constant (N m^2 / kg^2)
    public static final double G = 6.67e-11;

                                        // parameters from command line
    public static double T;             // simulate from time 0 to T (s)
    public static double dt;            // time quantum (s)

                                        // parameters from first two lines 
    public static int N;                // number of bodies
    public static double R;             // radius of universe

    public static double[] rx;          // x position (m)
    public static double[] ry;          // y position (m)
    public static double[] vx;          // x velocity (m/s)
    public static double[] vy;          // y velocity (m/s)
    public static double[] mass;        // mass (kg)
    public static String[] image;       // name of gif

    // TODO: read the planet file, new the parallel arrays, and load
    // their values from the file.
    public static void loadPlanets(String planetFileName) {
        Scanner input = null;
        // File IO with exception handling
        try {
            input = new Scanner(new File(planetFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Could not open " + planetFileName);
            System.exit(1);
        }

        N = input.nextInt();    // number of planets
        R = input.nextDouble(); // radius of universe
        input.nextLine();       // cast aside next line

        vx = new double[N];
        vy = new double[N];
        rx = new double[N];
        ry = new double[N];
        mass = new double[N];
        image = new String[N];
        
        // read N tasks
        for (int i = 0; i < N; i++) {
            String[] line = (input.nextLine()).split("  ");
            System.out.println("splits: " + line.length);
            rx[i] = Double.parseDouble(line[0]);
            ry[i] = Double.parseDouble(line[1]);
            vx[i] = Double.parseDouble(line[2]);
            vy[i] = Double.parseDouble(line[3]);
            mass[i] = Double.parseDouble(line[4]);
            image[i] = line[line.length - 1].trim();            
        } // end of for
        input.close();
    }

    public static void runSimulation() {

        // run numerical simulation from 0 to T
        for (double t = 0.0; t < T; t += dt) {

            // the x- and y-components of force
            double[] fx = new double[N];
            double[] fy = new double[N];

            // calculate forces on each object
            for (int i = 0; i < N; i++) {
                fx[i] = 0;
                fy[i] = 0;
                for (int j = 0; j < N; j++) {
                    if (i != j) {
                        double dx = rx[j] - rx[i];
                        double dy = ry[j] - ry[i];
                        double rad = Math.sqrt(dx * dx + dy * dy);
                        double Force = G * mass[i] * mass[j] / (rad * rad);
                        
                        fx[i] += Force * dx / rad;
                        fy[i] += Force * dy / rad;
                    }    
                }
                // update velocities and positions
                vx[i] += dt * fx[i] / mass[i];
                vy[i] += dt * fy[i] / mass[i];

                rx[i] += dt * vx[i];
                ry[i] += dt * vy[i];
            }
            // draw background and then planets
            // pause for a short while, using "animation mode"
            StdDraw.picture(0.0, 0.0, BACKGROUND);
            for (int i = 0; i < N; i++) {
                System.out.println(image[i]);
                StdDraw.picture(rx[i], ry[i], image[i]);
                // System.out.println("NEW STEP: " + t);    
            }
            StdDraw.show(DELAY);
        }
    }

    public static void main(String[] args) {

        // TODO: read T and dt from command line.
        T = 100*31557600.0;
        dt = 125000.0;

        // load planets from file specified in the command line
        String planetFileName = "planets.txt";
        loadPlanets(planetFileName);

        // rescale coordinates that we can use natural x- and y-coordinates
        StdDraw.setXscale(-R, +R);
        StdDraw.setYscale(-R, +R);

        StdAudio.play( MUSIC );

        // turn on animation mode
        StdDraw.show(0);

        // Run simulation
        runSimulation();

        // print final state of universe to standard output
        System.out.printf("%d\n", N);
        System.out.printf("%.2e\n", R);
        for (int i = 0; i < N; i++) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          rx[i], ry[i], vx[i], vy[i], mass[i], image[i]);
        }

    }
}

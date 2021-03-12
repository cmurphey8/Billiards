/*************************************************************************
 *  Dependencies: StdDraw.java StdAudio.java
 *                laser.wav pop.wav
 *                BilliardsTable.png 4Ball.png 6Ball.png 8Ball.png
 * 
 *  PART 1: Complete methods init() & draw() to get up and running
 *  PART 2: Complete methods pocket() and shift() to upgrade our billiards sim
 * 
 *  NOTE: It's okay for now to assume that the billiards balls do not interact with one another!
 * 
 *************************************************************************/

public class Billiards { 

    // declare global array variables
    public static double[] rx;          // x position (m)
    public static double[] ry;          // y position (m)
    public static double[] vx;          // x velocity (m/s)
    public static double[] vy;          // y velocity (m/s)
    public static String[] image;       // name of each ball, including file type
    
    public static int N = 3;                // number of billiards balls in play
    public static double X_EDGE = 0.75;     // walls and pockets x boundary
    public static double Y_EDGE = 1.7;      // walls and pockets y boundary
    public static double TOL = 0.05;        // tolerance from boundary to pocket a ball

    public static void main(String[] args) {    
        // declare size of global arrays
        vx = new double[N];
        vy = new double[N];
        rx = new double[N];
        ry = new double[N];
        image = new String[N];

        // initialize global arrays
        init();
        
        // initialize StdDraw elements
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(300, 600);
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-2.0, 2.0);

        // main animation loop
        while (true) { 
            step();
        } 
    } 

    // set rx components to -.4, 0, .4
    // set ry components to -1, 0, 1
    // set all vx, vy components to .015, .023 
    public static void init() {
        // some helpful constants -> can you init with a loop? 
        double rx0 = -0.4, ry0 = -1;
        double vx0 = 0.015, vy0 = 0.023;
        int image0 = 4;

        // TODO
    }

    public static void step() {   
        for (int i = 0; i < N; i++) {
            // skip turnaround if ball is pocketed
            if (pocket(i)) { continue; }

            // turnaround if ball would pass a boundary
            if (Math.abs(rx[i] + vx[i]) > X_EDGE) { vx[i] = -vx[i]; StdAudio.play("pop.wav"); }
            if (Math.abs(ry[i] + vy[i]) > Y_EDGE) { vy[i] = -vy[i]; StdAudio.play("pop.wav"); }

            // move the ball forward one time-step
            rx[i] = rx[i] + vx[i]; 
            ry[i] = ry[i] + vy[i]; 
        }
        
        // use global arrays to draw a new frame
        draw();
    }

    // draw the billiards table with all remaining balls
    public static void draw() {
        // TODO
    }

    // draw table with necessary params
    public static void table() {
        StdDraw.picture(0, 0, "billiardsTable.png", 2, 4);
    }

    // play runOut(k) simulation if ball would be near enough to a pocket after update
    // NOTE: don't forget about the side pockets!
    public static boolean pocket(int k) {
        // NOTE: see global constants X_EDGE, Y_EDGE, TOL
        // TODO
        return false;
    }

    // simulate a ball exiting
    public static void runOut(int k) {
        // laser sound for pocketing
        StdAudio.play("laser.wav");

        // remove this ball from further iterations
        shift(k);
    }

    // reduce global variable N by 1
    // shift all elements after k in all global arrays left by 1 
    public static void shift(int k) {
        // TODO
    }
} 

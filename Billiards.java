/*************************************************************************
 *  Compilation:  javac Billiards.java
 *  Execution:    java Billiards
 *  Dependencies: StdDraw.java StdAudio.java
 *                laser.wav pop.wav
 *                4Ball.png 6Ball.png 8Ball.png
 *
 *  GOAL: Upgrade this simulation to run 3 balls at once 
 *  NOTE: It's okay for now to assume that the images do not interact with one another!
 * 
 *************************************************************************/

public class Billiards { 

    // TO DO: declare global variables as arrays
    public static double[] rx;          // x position (m)
    public static double[] ry;          // y position (m)
    public static double[] vx;          // x velocity (m/s)
    public static double[] vy;          // y velocity (m/s)
    public static String[] image;       // name of each ball, including file type
    
    // global variables
    // public static double rx = .480, ry = .880;
    // public static double vx = .015, vy = .023;
    // public static String image = "sixBall.png";

    public static int N = 3;

    public static void main(String[] args) {    
        // TO DO:   Declare global array size. 
        // NOTE:    Replace N with the appropriate number.

        vx = new double[N];
        vy = new double[N];
        rx = new double[N];
        ry = new double[N];
        image = new String[N];

        // TO DO: initialize global array parameters with a single Loop
        // NOTE:    Set rx components to -.4, 0, .4
        //          Set ry components to -1, 0, 1
        //          Set all vx, vy components to .015, .023 

        double rx0 = 0.4, ry0 = 1;
        double vx0 = 0.015, vy0 = 0.023;
        int image0 = 4;

        rx[0] = -rx0;
        ry[0] = -ry0;
        vx[0] = vx0;
        vy[0] = vy0; 
        image[0] = image0 + "Ball.png"; 
        for (int i = 1; i < N; i++) {
            rx[i] = rx[i-1] + rx0;
            ry[i] = ry[i-1] + ry0;
        
            vx[i] = vx0;
            vy[i] = vy0;
            
            image[i] = (image0 + 2 * i) + "Ball.png";
        }

        StdDraw.enableDoubleBuffering();
        // set Canvas parameters
        StdDraw.setCanvasSize(300, 600);
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-2.0, 2.0);

        // main animation loop
        while (true) { 
            step();
        } 
    } 

    public static void step() {
        StdDraw.picture(0, 0, "billiardsTable.png", 2, 4);
        for (int i = 0; i < N; i++) {
            // TO DO: do not break until all 3 balls have been pocketed
            if (pocket(i)) { continue; }

            // TO DO: Upgrade simulation to run all balls that have not been pocketed
            if (Math.abs(rx[i] + vx[i]) > .75) { vx[i] = -vx[i]; StdAudio.play("pop.wav"); }
            if (Math.abs(ry[i] + vy[i]) > 1.7) { vy[i] = -vy[i]; StdAudio.play("pop.wav"); }

            rx[i] = rx[i] + vx[i]; 
            ry[i] = ry[i] + vy[i]; 
        }
        draw();
    }

    public static void draw() {
        for (int i = 0; i < N; i++) {
            StdDraw.picture(rx[i], ry[i], image[i]);
        }
        StdDraw.show();
        StdDraw.pause(10); 
    }

    // play runout() simulation if ball is near enough to a pocket
    public static boolean pocket(int k) {
        if (Math.abs(rx[k] + vx[k]) > .7) {
            if (Math.abs(ry[k] + vy[k])> 1.7) {
                runOut(k);
                return true;
            }
            if (Math.abs(ry[k] + vy[k]) < 0.05 ) {
                runOut(k);
                return true;
            }  
        }
        return false;
    }

    // simulate a ball exiting
    public static void runOut(int k) {
        rx[k] = rx[k] + vx[k]; 
        ry[k] = ry[k] + vy[k];
        StdAudio.play("laser.wav");

        shift(k);
        return;
    }

    public static void shift(int k) {
        N--;
        for (int i = k; i < N; i++) {
            vx[i] = vx[i+1];
            vy[i] = vy[i+1];
            rx[i] = rx[i+1];
            ry[i] = ry[i+1];
            image[i] = image[i+1];
        }
    }
} 

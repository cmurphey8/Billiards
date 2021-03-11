/*************************************************************************
 *  Compilation:  javac Billiards.java
 *  Execution:    java Billiards
 *  Dependencies: StdDraw.java StdAudio.java
 *                laser.wav pop.wav
 *                BilliardsTable.png 4Ball.png 6Ball.png 8Ball.png
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
    public static int N = 3;            // number of billiards balls in play

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

    public static void step() {
        for (int i = 0; i < N; i++) {
            // skip turnaround if ball is pocketed
            if (pocket(i)) { continue; }

            // turnaround if ball would pass boundary
            if (Math.abs(rx[i] + vx[i]) > .75) { vx[i] = -vx[i]; StdAudio.play("pop.wav"); }
            if (Math.abs(ry[i] + vy[i]) > 1.7) { vy[i] = -vy[i]; StdAudio.play("pop.wav"); }

            // move the ball forward one time-step
            rx[i] = rx[i] + vx[i]; 
            ry[i] = ry[i] + vy[i]; 
        }
        
        // use global arrays to draw a new frame
        draw();
    }

    // set rx components to -.4, 0, .4
    // set ry components to -1, 0, 1
    // set all vx, vy components to .015, .023 
    public static void init() {
        // some helpful constants -> can you init with a loop? 
        double rx0 = -0.4, ry0 = -1;
        double vx0 = 0.015, vy0 = 0.023;
        int image0 = 4;

        for (int i = 0; i < N; i++) {
            rx[i] = rx0 + Math.abs(rx0) * i;
            ry[i] = ry0 + Math.abs(ry0) * i;
        
            vx[i] = vx0;
            vy[i] = vy0;
            
            image[i] = (image0 + 2 * i) + "Ball.png";
        }
    }

    // draw the billiards table with all remaining balls
    public static void draw() {
        table();
        for (int i = 0; i < N; i++) {
            StdDraw.picture(rx[i], ry[i], image[i]);
        }
        StdDraw.show();
        StdDraw.pause(10); 
    }

    // draw table with necessary params
    public static void table() {
        StdDraw.picture(0, 0, "billiardsTable.png", 2, 4);
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
        // shift this ball forward 2 steps (turnaround is skipped in step() method)
        rx[k] = rx[k] + 2 * vx[k]; 
        ry[k] = ry[k] + 2 * vy[k];
        StdAudio.play("laser.wav");

        // remove this ball from further iterations
        shift(k);

        return;
    }

    // reduce global variable N by 1
    // shift all elements after k left by 1
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

import java.util.Scanner;
import java.io.Console;
import javax.swing.JOptionPane; 

public class Molasses {
    public static void main(String[] args){

        final double g = -9.8; // gravity in m/s
        final double Cd = 1;   // drag coefficient
        final double d = 1400; // density of molasses in kg/m^3
        final double deltaT = .0001;  // time increment in s

        while(true){
            JOptionPane.showMessageDialog(null, "Find out how far would YOU fall into a giant pool of molasses!");

            //prompts the user for inputs
            double weight = Double.parseDouble(JOptionPane.showInputDialog("How much do you weigh? (in pounds) "));
            double waist = Double.parseDouble(JOptionPane.showInputDialog("What is your waist circumference? (in inches) "));
            double height = Double.parseDouble(JOptionPane.showInputDialog("How tall are you? (in inches) "));

            //converts to useful metric units (from archaic imperial system)
            double m = weight*.4536;      // mass in kg
            double h = height*.0254;        // height in m
            double r = (waist*.0254)/(2*Math.PI);  // radius of human cylinder in m

            //sets initial acceleration, velocity, and position
            double a = g;  // acceleration in m/s^2
            double v = 0;  // velocity in m/s
            double x = 0;  // depth sunk in m
            double timeCounter = 0;  // time elapsed in s

            double Fg = 0; // force of gravity in N
            double Fb = 0; // force of buoyancy in N
            double Fd = 0; // drag force in N

            // determines if person is destined to drown
            boolean drowned = false;
            if( m / (Math.PI*Math.pow(r,2)) >= d ){
                JOptionPane.showMessageDialog(null,"You would drown if you didn't exert a force!");
                drowned = true;
            }

            // calculates acceleration, velocity, and position until velocity becomes positive
            while(v < 0.001 && !drowned){
                v += a*deltaT;
                x += v*deltaT;

                Fg = m*g;
                Fb = d*Math.PI*Math.pow(r,2)*Math.min(Math.abs(x),h)*g;
                Fd = (v/Math.abs(v))*.5*d*Math.pow(v,2)*Cd*Math.PI*Math.pow(r,2);
                
                a = ( Fg - Fb - Fd )/m;

                timeCounter += deltaT;
            }

            //calculates stable floating depth (independent of other simulation)
            double stableDepth = m/(d*Math.PI*Math.pow(r,2));

            //prints rounded results
            if( !drowned ){
                JOptionPane.showMessageDialog(null,"Maximum Depth Sunk (in inches): " + Math.round(((-x)/.0254)*100.0)/100.0 
                    + "\nTime to Maximum Depth (in seconds): " + Math.round(timeCounter*100.0)/100.0
                    + "\nStable Floating Depth (in inches): " + Math.round(((stableDepth)/.0254)*100.0)/100.0);
            }

            //allows user to reset repeat
            int exit = JOptionPane.showConfirmDialog(null, "Try again?");
            if (exit == JOptionPane.NO_OPTION)
            {
              System.exit(0);
              }
            if (exit == JOptionPane.CANCEL_OPTION)
            {
                System.exit(0);
            }
        }
    }
}

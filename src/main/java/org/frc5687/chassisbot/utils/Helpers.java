package org.frc5687.chassisbot.utils;

import org.frc5687.chassisbot.Constants;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * A collection of helper/ math methods used throughout the robot code
 */
public class Helpers{
    /**
     * Logs an action to Drive Station
     * @param message the message to send to Drive Station
     */
    protected void LogAction(String message) {
        DriverStation.reportError(message + "\r\n", false);
    }



    /**
     * Applies a deadband threshold to a given value
     * @param input raw value from joystick
     * @param deadband the deadband threshold
     * @return double the adjusted value
     */
    public static double applyDeadband(double input, double deadband){
        return (Math.abs(input) >= Math.abs(deadband)) ? input : 0;
    }

    /**
     * Applies a transform to the input to provide better sensitivity at low speeds.
     * @param input the raw input value from a joystick
     * @param range the range over which to scale the input
     * @return the adjusted control value
     */
    public static double applySensitivityTransform(double input, double range) {
        // See http://www.chiefdelphi.com/forums/showthread.php?p=921992

        // The transform can only work on values between -1 and 1.
        if (input>1) { return range; }
        if (input <-1) { return -1 * range; }

        // The sensitivity factor MUST be between 0 and 1!
        double factor = Math.max(Math.min(Constants.Calibration.SENSITIVITY_FACTOR, range),0);

        return factor*input*input*input + (range-factor)*input;
    }

    /**
     * Checks if a value is within tolerance of another value
     * @param value the value to check
     * @param target the target or desired value
     * @param tolerance the acceptable tolerance around the target
     * @return true if the value is within tolerance of the target value
     */
    public static boolean IsValueWithinTolerance(double value, double target, double tolerance)
    {
        return Math.abs(value - target) <= tolerance;
    }
}

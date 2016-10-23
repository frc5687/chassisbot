package org.frc5687.chassisbot;

import org.frc5687.chassisbot.utils.Gamepad;
import org.frc5687.chassisbot.utils.Helpers;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import org.frc5687.chassisbot.utils.JoystickLight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private Gamepad gamepad;
    private Joystick joystick;

    // Drive Train Elements
    public static final int HORNS_DIRECTION = 1;
    public static final int INTAKE_DIRECTION = -1;
    private static int currentDirection = INTAKE_DIRECTION; //Initial drive direction

    // Drive Train Buttons
    public static final int REVERSE = Gamepad.Buttons.BACK.getNumber();

    // Hell-Effect Override Button
    public static final int OVERRIDE = Gamepad.Buttons.START.getNumber();


    // Boulder Buttons
    public static final int CAPTURE = 3;
    public static final int BOWL = 2;
    public static final int PRIME = 5;
    public static final int UNPRIME = 6;
    public static final int FIRE = 9; // Previous: 1, not responsive  9
    public static final int RECOVER = 4;

    public static final int CAPTURE_LIGHT = 2;
    public static final int INTAKE_IN_LIGHT = 1;
    public static final int INTAKE_OUT_LIGHT = 3;


    // Intake Lifter Buttons
    public static final int LOWER_INTAKE_AUTO = 11;
    //public static final int LOWER_INTAKE = 11;
    public static final int RAISE_INTAKE = 12;
    // Prime Speed Buttons
    public static final int LOW_PRIME_SPEED = 10; // 0.80
    //public static final int DEFAULT_PRIME_SPEED = 9; // 0.84
    // Lights Buttons
    public static final int SWITCH_RING_LIGHT = 8;
    public static final int SWITCH_FLASHLIGHT = 7;
    // Camera switch
    public static int RESET_CAMERA = Gamepad.Buttons.A.getNumber();

    private JoystickButton overrideButton;
    private JoystickLight capturedLight;
    private JoystickLight intakeInLight;
    private JoystickLight intakeOutLight;

    private boolean rumbling = false;
    private long rumbleStopTime = 0;


    /**
     * Create a new instance of the operator interface
     */
    public OI() {
        gamepad = new Gamepad(0);
        joystick = new Joystick(1);

        // Gamepad Buttons
        JoystickButton reverseButton = new JoystickButton(gamepad, REVERSE);
        overrideButton = new JoystickButton(gamepad, OVERRIDE);

        // Joystick Buttons
        JoystickButton captureButton = new JoystickButton(joystick, CAPTURE);
        JoystickButton bowlButton = new JoystickButton(joystick, BOWL);
        JoystickButton primeButton = new JoystickButton(joystick, PRIME);
        JoystickButton unprimeButton = new JoystickButton(joystick, UNPRIME);
        JoystickButton fireButton = new JoystickButton(joystick, FIRE);
        JoystickButton recoverButton = new JoystickButton(joystick, RECOVER);
        JoystickButton lowerIntakeButton = new JoystickButton(joystick, LOWER_INTAKE_AUTO);

        capturedLight = new JoystickLight(joystick, CAPTURE_LIGHT);
        intakeInLight = new JoystickLight(joystick, INTAKE_IN_LIGHT);
        intakeOutLight = new JoystickLight(joystick, INTAKE_OUT_LIGHT);

        //JoystickButton normalSpeedButton = new JoystickButton(joystick, DEFAULT_PRIME_SPEED);

        // Drive Train Commands
        // Boulder Commands

        //captureButton.toggleWhenPressed(new CaptureBoulder());
        //bowlButton.whenPressed(new Bowl());

    }

    /**
     * Gets the current direction of the drive
     * @return the direction, 1 for forward or -1 for reverse
     */
    public int getDirection() {
        return currentDirection;
    }

    /**
     * Sets the current direction of the drive
     * @param direction the desired direction of the drive
     */
    public void setDirection(int direction) {
        currentDirection = direction;
    }

    public void rumble (float strength, long length) {

        rumbleStopTime = System.currentTimeMillis() + length;

        gamepad.setRumble(RumbleType.kLeftRumble, strength);
        gamepad.setRumble(RumbleType.kRightRumble, strength);

    }

    public void endRumble (){

        if (rumbleStopTime>0&&System.currentTimeMillis()>rumbleStopTime) {
            gamepad.setRumble(RumbleType.kLeftRumble, 0);
            gamepad.setRumble(RumbleType.kRightRumble, 0);

            rumbleStopTime = 0;
        }
    }

    public boolean getOverride() {
        return overrideButton.get();
    }
    /**
     * Gets the desired speed for the left side of the drive
     * @return the control value for the right drive motors
     */
    public double getLeftSpeed(){
        if (currentDirection == INTAKE_DIRECTION) {
            return currentDirection * transformStickToSpeed(Gamepad.Axes.LEFT_Y);
        }
        return transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
    }

    /**
     * Gets the desired speed for the right side of the drive
     * @return the control value for the right drive motors
     */
    public double getRightSpeed(){
        if (currentDirection == INTAKE_DIRECTION) {
            return currentDirection * transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
        }
        return transformStickToSpeed(Gamepad.Axes.LEFT_Y);
    }

    /**
     * Gets the desired speed for the intake
     * @return the control value for the intake motor
     */
    public double getIntakeSpeed() {
        // Joystick's y-axis is set to control intake speed
        return Helpers.applyDeadband(joystick.getRawAxis(1), Constants.Deadbands.INTAKE_STICK);
    }


    public void setCapturedLight(boolean value) {
        capturedLight.set(value);
    }

    public void setIntakeInLight(boolean value) {
        intakeInLight.set(value);
    }

    public void setIntakeOutLight(boolean value) {
        intakeOutLight.set(value);
    }

    /**
     * Get the requested stick position from the gamepad, apply deadband and sensitivity transforms, and return the result.
     * @param stick the gamepad axis to adjust and use
     * @return the adjusted control value from the gamepad
     */
    private double transformStickToSpeed(Gamepad.Axes stick) {
        double result = gamepad.getRawAxis(stick);
        result = Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
        result = Helpers.applySensitivityTransform(result);
        return result;
    }
}


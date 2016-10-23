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


    // Boulder Buttons
    public static final int COLLECT = 1;  // Green button
    public static final int BOWL = 2; // Yellow
    public static final int CANCEL = 12; // Red

    public static final int INTAKE_OUT = 6; // Yellow
    public static final int INTAKE_IN = 8; // Red


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

        // Joystick Buttons
        JoystickButton collectButton = new JoystickButton(joystick, COLLECT);
        JoystickButton bowlButton = new JoystickButton(joystick, BOWL);
        JoystickButton cancelButton = new JoystickButton(joystick, CANCEL);

        capturedLight = new JoystickLight(joystick, CAPTURE_LIGHT);
        intakeInLight = new JoystickLight(joystick, INTAKE_IN_LIGHT);
        intakeOutLight = new JoystickLight(joystick, INTAKE_OUT_LIGHT);

        //JoystickButton normalSpeedButton = new JoystickButton(joystick, DEFAULT_PRIME_SPEED);

        // Drive Train Commands
        // Boulder Commands

        //captureButton.toggleWhenPressed(new CaptureBoulder());
        //bowlButton.whenPressed(new Bowl());

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
        return transformStickToSpeed(Gamepad.Axes.LEFT_Y);
    }

    /**
     * Gets the desired speed for the right side of the drive
     * @return the control value for the right drive motors
     */
    public double getRightSpeed(){
        return transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
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


package org.frc5687.chassisbot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.chassisbot.commands.BowlBoulder;
import org.frc5687.chassisbot.commands.CancelIntake;
import org.frc5687.chassisbot.commands.CollectBoulder;
import org.frc5687.chassisbot.commands.ExpandPiston;
import org.frc5687.chassisbot.commands.RetractPiston;
import org.frc5687.chassisbot.utils.Gamepad;
import org.frc5687.chassisbot.utils.Helpers;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import org.frc5687.chassisbot.utils.JoystickLight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private Gamepad gamepad;
    private Joystick joystick;


    // Boulder Buttons
    public static final int COLLECT = 2;  // Green button
    public static final int BOWL = 1; // Yellow
    public static final int CANCEL = 4; // Red

    public static final int CAPTURE_LIGHT = 2;
    public static final int INTAKE_IN_LIGHT = 1;
    public static final int INTAKE_OUT_LIGHT = 3;

    public static final int EXPAND_PISTON = 6;
    public static final int RETRACT_PISTON = 5;


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



    private JoystickButton collectButton;
    private JoystickButton bowlButton;
    private JoystickButton cancelButton;

    private JoystickLight capturedLight;
    private JoystickLight intakeInLight;
    private JoystickLight intakeOutLight;

    private JoystickButton leftAccel;
    private JoystickButton rightAccel;

    private JoystickButton expandPistonButton;
    private JoystickButton retractPistonButton;

    private boolean rumbling = false;
    private long rumbleStopTime = 0;


    /**
     * Create a new instance of the operator interface
     */
    public OI() {
        gamepad = new Gamepad(0);
        joystick = new Joystick(1);

        // Gamepad Buttons
        leftAccel = new JoystickButton(gamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        rightAccel = new JoystickButton(gamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());

        // Joystick Buttons
        collectButton = new JoystickButton(joystick, COLLECT);
        bowlButton = new JoystickButton(joystick, BOWL);
        cancelButton = new JoystickButton(joystick, CANCEL);

        expandPistonButton = new JoystickButton(gamepad, EXPAND_PISTON);
        retractPistonButton = new JoystickButton(gamepad, RETRACT_PISTON);

        capturedLight = new JoystickLight(joystick, CAPTURE_LIGHT);
        intakeInLight = new JoystickLight(joystick, INTAKE_IN_LIGHT);
        intakeOutLight = new JoystickLight(joystick, INTAKE_OUT_LIGHT);



        // Drive Train Commands

        // Boulder Commands
        collectButton.whenPressed(new CollectBoulder());
        cancelButton.whenPressed(new CancelIntake());
        bowlButton.whenPressed(new BowlBoulder());

        expandPistonButton.whenPressed(new ExpandPiston());
        retractPistonButton.whenPressed(new RetractPiston());
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

    public void UpdateDashboard() {
        SmartDashboard.putBoolean("Collect button", collectButton.get());
        SmartDashboard.putBoolean("Bowl button", bowlButton.get());
        SmartDashboard.putBoolean("Stop button", cancelButton.get());
        SmartDashboard.putBoolean("Buttons 1", joystick.getRawButton(1));
        SmartDashboard.putBoolean("Buttons 2", joystick.getRawButton(2));
        SmartDashboard.putBoolean("Buttons 3", joystick.getRawButton(3));
        SmartDashboard.putBoolean("Buttons 4", joystick.getRawButton(4));
        SmartDashboard.putBoolean("Buttons 5", joystick.getRawButton(5));
        SmartDashboard.putBoolean("Buttons 6", joystick.getRawButton(6));
        SmartDashboard.putBoolean("Buttons 7", joystick.getRawButton(7));
        SmartDashboard.putBoolean("Buttons 8", joystick.getRawButton(8));
        SmartDashboard.putBoolean("Buttons 9", joystick.getRawButton(9));
        SmartDashboard.putBoolean("Buttons 10", joystick.getRawButton(10));
        SmartDashboard.putBoolean("Buttons 11", joystick.getRawButton(11));
        SmartDashboard.putBoolean("Buttons 12", joystick.getRawButton(12));
        SmartDashboard.putBoolean("Buttons 13", joystick.getRawButton(13));
        SmartDashboard.putBoolean("Buttons 14", joystick.getRawButton(14));
        SmartDashboard.putBoolean("Buttons 15", joystick.getRawButton(15));
        SmartDashboard.putBoolean("Buttons 16", joystick.getRawButton(16));
        SmartDashboard.putBoolean("Buttons 17", joystick.getRawButton(17));
        SmartDashboard.putBoolean("Buttons 18", joystick.getRawButton(18));
        SmartDashboard.putBoolean("Buttons 19", joystick.getRawButton(19));
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
        boolean accel = leftAccel.get() || rightAccel.get();
        double result =  gamepad.getRawAxis(stick);
        result = Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
        result = Helpers.applySensitivityTransform(result, accel ? 1.0 : 0.7);
        return result;
    }
}

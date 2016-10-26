package org.frc5687.chassisbot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import org.frc5687.chassisbot.Constants;
import org.frc5687.chassisbot.RobotMap;
import org.frc5687.chassisbot.commands.RunIntakeManually;
import org.frc5687.chassisbot.utils.Helpers;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.frc5687.chassisbot.Robot.oi;

/**
 * Class for boulder intake subsystem
 * @author wil
 */
public class Intake extends Subsystem {

    /**
     * VictorSP speed controller for intake motor
     */
    private VictorSP intakeMotor;
    private AnalogInput boulderSensor;

    public Intake() {
        intakeMotor = new VictorSP(RobotMap.Intake.INTAKE_MOTOR);
        intakeMotor.setInverted(false);
        boulderSensor = new AnalogInput(RobotMap.Intake.INFRARED_SENSOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunIntakeManually());
    }

    /**
     * Checks if boulder is detected
     * @return Whether or not the infrared sensor sees anything
     */
    public boolean isDetected() {
        return boulderSensor.getValue() < Constants.InfraRedConstants.DETECTION_THRESHOLD;
    }

    /**
     * Checks if boulder is captured
     * @return Whether or not the ball is captured
     */
    public boolean isCaptured(){
        boolean withinTolerance = Helpers.IsValueWithinTolerance(
                boulderSensor.getValue(),
                Constants.InfraRedConstants.CAPTURED_OPTIMAL,
                Constants.InfraRedConstants.CAPTURED_TOLERANCE);

        boolean beyondCaptured = boulderSensor.getValue() > Constants.InfraRedConstants.CAPTURED_OPTIMAL;
        return withinTolerance || beyondCaptured;
    }


    /**
     * Updates intake data to smart dashboard
     */
    public void updateDashboard() {
        SmartDashboard.putNumber("IR distance", boulderSensor.getValue());
        if (!isDetected()){
            SmartDashboard.putString("Boulder", "Not Detected");
        }
        else if (isCaptured()){
            SmartDashboard.putString("Boulder", "Captured");
        } else {
            SmartDashboard.putString("Boulder", "Detected");
        }
        SmartDashboard.putBoolean("BoulderCaptured", isCaptured());

        oi.setCapturedLight(isCaptured());
    }

    /**
     * Sets the speed of the intake
     * @param speed the desired speed value
     */
    public void setSpeed(double speed) {
        if (speed == Constants.Intake.CAPTURE_SPEED) {
            DriverStation.reportError("Intake in", false);
        } else if (speed == Constants.Intake.BOWL_SPEED) {
            DriverStation.reportError("Intake out", false);
        }

        oi.setIntakeInLight(speed == Constants.Intake.CAPTURE_SPEED);
        oi.setIntakeOutLight(speed == Constants.Intake.BOWL_SPEED);

        intakeMotor.set(speed);
    }

    /**
     * Stops the intake motor
     */
    public void stop() {
        setSpeed(0);
    }
}

package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Date;

import static org.frc5687.chassisbot.Robot.driveTrain;
import static org.frc5687.chassisbot.Robot.imu;


/**
 * Created by Ben Bernard on 10/27/2016.
 */
public class AutoDrive extends Command implements PIDOutput {

    private long endTime = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double speed = 0;
    private double inchesDriven = 0;
    private double inchesAtStart = 0;
    protected boolean driveByTime;

    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kF = 0.0;
    private static final double kToleranceDegrees = 0.0f;
    private double rotateToAngleRate = 0;
    private double targetAngle = 0;

    public AutoDrive(double speed) {
        requires(driveTrain);
        this.speed = speed;
    }

    /**
     * Drive at a specified speed for a time specified in milliseconds.
     *
     * @param speed Speed to drive (range -1 to +1
     * @param millisToDrive Milliseconds to drive
     */
    public AutoDrive(double speed, int millisToDrive) {
        this(speed);
        this.timeToDrive = millisToDrive;
        this.driveByTime = true;
    }

    /**
     * Drive at a specified speed for a distance specified in inches.
     *
     * @param speed Speed to drive (range 0 to +1
     * @param inchesToDrive Inches to drive (negative for reverse)
     */
    public AutoDrive(double speed, double inchesToDrive) {
        this(speed);
        this.inchesToDrive = inchesToDrive;
        this.driveByTime = false;
    }

    @Override
    protected void initialize() {
        if (driveByTime) {
            DriverStation.reportError("Driving at " + speed + " for " + timeToDrive + " milliseconds.\n", false);
            endTime = (new Date()).getTime() + timeToDrive;
        } else {
            DriverStation.reportError("Driving at " + speed + " for " + inchesToDrive + " inches.\n", false);
            setDistance(inchesToDrive);
        }
        targetAngle = imu.getYaw();
    }

    protected void setDistance(double inchesToDrive) {
        inchesAtStart = driveTrain.getRightDistance();
        this.inchesToDrive = inchesToDrive;
        driveByTime = false;
    }

    @Override
    protected void execute() {
        int directionFactor = driveByTime || (inchesToDrive>=0) ? 1 : -1;
        driveTrain.tankDrive(directionFactor * speed, directionFactor * speed);
    }

    @Override
    protected boolean isFinished() {
        boolean result = true;
        if (driveByTime) {
            long now = (new Date()).getTime();
            result = now > endTime;
        } else if (inchesToDrive<0){
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            result = inchesDriven <= inchesToDrive;
        } else if (inchesToDrive>0) {
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            result = inchesDriven >= inchesToDrive;
        }

        if (result) {
            DriverStation.reportError("AutoDrive done.\n", false);
        }
        return result;
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoDrive done.\n", false);
        driveTrain.tankDrive(0,0);

    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    public void pidWrite(double output) {
        synchronized (this) {
            SmartDashboard.putNumber("AutoAlign/PID Output", output);
            rotateToAngleRate = output;
        }
    }
}

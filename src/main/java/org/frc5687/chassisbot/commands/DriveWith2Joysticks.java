package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.chassisbot.OI;
import org.frc5687.chassisbot.Robot;
import static org.frc5687.chassisbot.Robot.driveTrain;
import static org.frc5687.chassisbot.Robot.oi;

/**
 * Command for basic driver-control of the robot chassis
 * Created by Baxter on 1/23/2016.
 */

public class DriveWith2Joysticks extends Command {
    /*
     * Constructor
     */
    public DriveWith2Joysticks() {
        requires(driveTrain);
    }

    /*
     * Sets up the command
     * Called just before this Command runs the first time(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    protected void initialize() {
        driveTrain.resetDriveEncoders();
    }

    /*
     * Executes the command
     * Called repeatedly when this Command is scheduled to run(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    protected void execute() {
        driveTrain.tankDrive(oi.getLeftSpeed(), oi.getRightSpeed());
    }

    /*
     * Check if this command is finished running
     * Make this return true when this Command no longer needs to run execute()(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    protected boolean isFinished() {
        return false;
    }

    /*
     * Command execution clean-up
     * Called once after isFinished returns true(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    protected void end() {
    }

    /*
     * Handler for when command is interrupted
     * Called when another command which requires one or more of the same(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    protected void interrupted() {
    }
}

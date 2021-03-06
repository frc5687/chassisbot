package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.frc5687.chassisbot.Robot.intake;
import static org.frc5687.chassisbot.Robot.oi;

/**
 * Command for basic driver-control of the robot chassis
 * Created by Baxter on 1/23/2016.
 */

public class RunIntakeManually extends Command {
    /*
     * Constructor
     */
    public RunIntakeManually() {
        requires(intake);
    }

    /*
     * Sets up the command
     * Called just before this Command runs the first time(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    protected void initialize() {

    }

    /*
     * Executes the command
     * Called repeatedly when this Command is scheduled to run(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    protected void execute() {
        intake.setSpeed(oi.getIntakeSpeed());
        // oi.sendButtons();
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

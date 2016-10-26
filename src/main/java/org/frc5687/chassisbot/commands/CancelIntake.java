package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.frc5687.chassisbot.Robot.intake;
import static org.frc5687.chassisbot.Robot.oi;

/**
 * Created by Ben Bernard on 10/24/2016.
 */
public class CancelIntake extends Command {

    public CancelIntake() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.stop();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        oi.setCapturedLight(intake.isCaptured());
        intake.stop();
        oi.rumble(.3f,500);
    }

    @Override
    protected void interrupted() {
        end();
    }

}

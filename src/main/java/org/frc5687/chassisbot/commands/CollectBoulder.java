package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc5687.chassisbot.Constants;

import static org.frc5687.chassisbot.Robot.intake;
import static org.frc5687.chassisbot.Robot.oi;

/**
 * Created by Ben Bernard on 10/24/2016.
 */
public class CollectBoulder extends Command {

    public CollectBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.CAPTURE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return intake.isCaptured();
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

package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc5687.chassisbot.Constants;

import static org.frc5687.chassisbot.Robot.intake;
import static org.frc5687.chassisbot.Robot.oi;


/**
 * Created by Ben Bernard on 10/24/2016.
 */
public class BowlBoulder extends Command {
    private long endTime;

    public BowlBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis()+ Constants.Intake.BOWL_TIME;
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.BOWL_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis()>endTime;
    }

    @Override
    protected void end() {
        oi.setCapturedLight(intake.isCaptured());
        intake.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}

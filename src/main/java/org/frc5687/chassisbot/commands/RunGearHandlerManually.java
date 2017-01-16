package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import static org.frc5687.chassisbot.Robot.gearHandler;
import static org.frc5687.chassisbot.Robot.oi;

/**
 * Created by Ben Bernard on 1/16/2017.
 */
public class RunGearHandlerManually extends Command {

    public  RunGearHandlerManually() {
        requires(gearHandler);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    protected void execute() {
        if (oi.isGearInPressed()) { gearHandler.close(); }
        else if (oi.isGearOutPressed()) { gearHandler.open(); }
        else { gearHandler.stop(); }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

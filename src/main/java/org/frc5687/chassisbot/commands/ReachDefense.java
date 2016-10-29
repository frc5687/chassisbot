package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.frc5687.chassisbot.Constants;

/**
 * Created by Ben Bernard on 10/27/2016.
 */
public class ReachDefense extends CommandGroup {

        public ReachDefense() {
            addSequential(new AutoDrive(Constants.Autonomous.REACH_SPEED, 2000));

        }
}

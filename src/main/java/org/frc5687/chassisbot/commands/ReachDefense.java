package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Ben Bernard on 10/27/2016.
 */
public class ReachDefense extends CommandGroup {

        public ReachDefense() {
            addSequential(new AutoDrive(.5, 24.0f));

        }
}

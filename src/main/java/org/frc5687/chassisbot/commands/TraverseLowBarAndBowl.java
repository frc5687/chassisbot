package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.frc5687.chassisbot.Constants;

/**
 * Created by Ben Bernard on 10/27/2016.
 */
public class TraverseLowBarAndBowl extends CommandGroup {

        public TraverseLowBarAndBowl() {
            addSequential(new AutoTraverse());

            addSequential(new AutoDrive(Constants.Autonomous.TRAVERSE_SPEED, 80.0f));

            // Turn towards the tower
            addSequential(new AutoAlign(60f));

            // Run forward
            addSequential(new AutoDrive(Constants.Autonomous.TRAVERSE_SPEED, 60.0f));

            addSequential(new BowlBoulder());
        }
}

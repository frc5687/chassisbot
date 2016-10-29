package org.frc5687.chassisbot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Ben Bernard on 10/27/2016.
 */
public class TraverseLowBarAndBowl extends CommandGroup {

        public TraverseLowBarAndBowl() {
            addSequential(new AutoTraverse());

            addSequential(new AutoDrive(.7, 80.0f));

            // Turn towards the tower
            addSequential(new AutoAlign(60f));

            // Run forward
            addSequential(new AutoDrive(.7, 60.0f));

            addSequential(new BowlBoulder());
        }
}

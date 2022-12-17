package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team1540.bobafett.Constants.*;
import org.team1540.bobafett.commands.elevator.Elevator;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

public class ApproachAprilTag extends SequentialCommandGroup {
    public ApproachAprilTag(Drivetrain drivetrain, ChickenPhotonCamera camera) {
        addCommands(
                new FindAprilTag(drivetrain, camera),
                new WaitCommand(0.5),

                new TurnToAprilTag(drivetrain, camera, VisionConstants.APRIL_TAG_ID),
                new WaitCommand(0.5),

                new DriveToAprilTag(drivetrain, camera, VisionConstants.APRIL_TAG_ID, VisionConstants.APRIL_TAG_AREA/2),
                new WaitCommand(0.5),

                new TurnToAprilTag(drivetrain, camera, VisionConstants.APRIL_TAG_ID),
                new WaitCommand(0.5),

                new DriveToAprilTag(drivetrain, camera, VisionConstants.APRIL_TAG_ID, VisionConstants.APRIL_TAG_AREA)
        );
    }
}

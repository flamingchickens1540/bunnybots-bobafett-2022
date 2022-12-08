package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.team1540.bobafett.Constants;
import org.team1540.bobafett.commands.elevator.Elevator;
import org.team1540.bobafett.commands.elevator.MoveToTop;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

public class Auto extends SequentialCommandGroup {
    public Auto(Drivetrain drivetrain, Elevator elevator, ChickenPhotonCamera camera) {
        addCommands(
                // Turn to face the AprilTag
                new AprilTagPIDTurn(drivetrain, camera, Constants.VisionConstants.APRIL_TAG_ID),
                // Move elevator to top limit switch while driving to the AprilTag
                new ParallelCommandGroup(new MoveToTop(elevator),
                        new DriveToAprilTag(drivetrain, camera, Constants.VisionConstants.APRIL_TAG_ID)));
    }
}

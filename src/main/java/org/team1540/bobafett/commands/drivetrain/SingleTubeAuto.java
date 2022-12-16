package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team1540.bobafett.commands.claw.Claw;
import org.team1540.bobafett.commands.elevator.Elevator;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

public class SingleTubeAuto extends SequentialCommandGroup {
    public SingleTubeAuto(Drivetrain drivetrain, Elevator elevator, ChickenPhotonCamera camera, Claw claw) {
        addCommands(
                // Approach the AprilTag
                new ApproachAprilTag(drivetrain, camera),

                // Wait for drivetrain to stabilize
                new WaitCommand(0.5),

                // Place tube in the top bin
                new PlaceTube(drivetrain, elevator, claw)
        );
    }
}

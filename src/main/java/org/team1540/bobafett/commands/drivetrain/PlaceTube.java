package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team1540.bobafett.Constants;
import org.team1540.bobafett.commands.claw.Claw;
import org.team1540.bobafett.commands.elevator.Elevator;
import org.team1540.bobafett.commands.elevator.MoveToBottom;
import org.team1540.bobafett.commands.elevator.MoveToTop;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

public class PlaceTube extends SequentialCommandGroup {
    public PlaceTube(Drivetrain drivetrain, Elevator elevator, ChickenPhotonCamera camera, Claw claw) {
        addCommands(
                new TurnToAprilTag(drivetrain, camera, Constants.VisionConstants.APRIL_TAG_ID),
                new MoveToTop(elevator),
                new MoveSlightly(drivetrain, 0.5),
                new WaitCommand(0.5),
                new InstantCommand(() -> claw.set(true)),
                new MoveSlightly(drivetrain, -0.5),
                new WaitCommand(0.5),
                new MoveToBottom(elevator)
        );
    }
}

package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team1540.bobafett.Constants.*;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

public class FindAprilTag extends CommandBase {

    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera camera;

    public FindAprilTag(Drivetrain drivetrain, ChickenPhotonCamera camera) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.setPercent(0.5, 0.5);
    }

    @Override
    public boolean isFinished() {
        return camera.getTarget(VisionConstants.APRIL_TAG_ID) != null;
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
    }
}

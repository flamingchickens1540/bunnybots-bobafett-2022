package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

/**
 * Drives to an AprilTag until it is in claw range
 * This command should only be run after the robot has turned to face the AprilTag
 */
public class DriveToAprilTag extends CommandBase {

    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera camera;
    private final int targetID;
    private PhotonTrackedTarget aprilTag;
    private NeutralMode originalBrakeMode;

    public DriveToAprilTag(Drivetrain drivetrain, ChickenPhotonCamera camera, int targetID) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.targetID = targetID;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        aprilTag = camera.getTarget(targetID);
        if (aprilTag != null) drivetrain.setPercent(0.5, 0.5);
        originalBrakeMode = drivetrain.getBrakeType();
        drivetrain.setBrakeMode(NeutralMode.Brake);
    }

    @Override
    public boolean isFinished() {
        return aprilTag == null || aprilTag.getArea() >= Constants.VisionConstants.APRIL_TAG_AREA;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
        drivetrain.setBrakeMode(originalBrakeMode);
    }
}

package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants.*;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

/**
 * Drives to an AprilTag until it is in claw range
 * This command should only be run after the robot has turned to face the AprilTag
 */
public class DriveToAprilTag extends CommandBase {

    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera camera;
    private final int targetID;
    private final double targetArea;
    private PhotonTrackedTarget aprilTag;
    private NeutralMode originalBrakeMode;
    private final PIDController pidController = new PIDController(
            DriveConstants.DRIVE_KP,
            DriveConstants.DRIVE_KI,
            DriveConstants.DRIVE_KD
    );

    public DriveToAprilTag(Drivetrain drivetrain, ChickenPhotonCamera camera, int targetID, double targetArea) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.targetID = targetID;
        this.targetArea = targetArea;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        aprilTag = camera.getTarget(targetID);
        originalBrakeMode = drivetrain.getBrakeMode();
        drivetrain.setBrakeMode(NeutralMode.Brake);
        drivetrain.setYaw(0);
    }

    @Override
    public void execute() {
        if (aprilTag != null) {
            double out = pidController.calculate(drivetrain.getYaw());
            drivetrain.setPercent(0.5 - out, 0.5 + out);
        }
    }

    @Override
    public boolean isFinished() {
        return aprilTag == null || aprilTag.getArea() >= targetArea;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
        drivetrain.setBrakeMode(originalBrakeMode);
    }
}

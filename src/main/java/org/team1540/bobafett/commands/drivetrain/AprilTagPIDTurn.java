package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants;
import org.team1540.bobafett.utils.ChickenPhotonCamera;

/**
 * Command for turning the robot towards an AprilTag with a specified fiducial ID
 * using a PID controller. Uses a Pigeon 2.0 for rotational sensing.
 */

public class AprilTagPIDTurn extends CommandBase {

    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera camera;
    private final PIDController pid = new PIDController(
            Constants.DriveConstants.DRIVE_KP, Constants.DriveConstants.DRIVE_KI, Constants.DriveConstants.DRIVE_KD);
    private final int targetId;
    private double setpoint;

    public AprilTagPIDTurn(Drivetrain drivetrain, ChickenPhotonCamera camera, int targetId) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.setpoint = drivetrain.getYaw();
        this.targetId = targetId;
        addRequirements(drivetrain);
    }

    /** Looks for the target that we are searching for in what the camera can see currently.
     * If we find the target with the fiducial id specified in the constructor, we set its
     * current yaw (degree angle away from the camera) to be the setpoint for the pid.*/
    @Override
    public void initialize() {
        drivetrain.setYaw(0);
        PhotonTrackedTarget target = camera.getTarget(targetId);
        if (target != null) {
            setpoint = target.getYaw();
        } else setpoint = drivetrain.getYaw(); // Set setpoint to current heading if no target is found
    }

    /** If we are not within 1.5 degrees of the target and if we have found a target,
     * set motor speeds to what the pid has calculated, basing angular measurements
     * from data obtained from the pigeon.
     */
    @Override
    public void execute() {
        double output = pid.calculate(drivetrain.getYaw(), setpoint);
        drivetrain.setPercent(output, -1*output);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getYaw() - targetId) < 1.5;
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
    }
}

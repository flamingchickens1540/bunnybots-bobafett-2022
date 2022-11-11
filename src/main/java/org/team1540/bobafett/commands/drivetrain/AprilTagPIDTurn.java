package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants;

import java.util.List;

/**
 * Command for turning the robot towards an AprilTag with a specified fiducial ID
 * using a PID controller. Uses a Pigeon 2.0 for rotational sensing.
 */

public class AprilTagPIDTurn extends CommandBase {

    private final Drivetrain drivetrain;
    private final PhotonCamera camera;
    private final Pigeon2 pigeon;
    private final PIDController pid = new PIDController(
            Constants.VisionConstants.KP, Constants.VisionConstants.KI, Constants.VisionConstants.KD); // This somehow works without the i and d
    private final int targetId;
    private double setpoint;
    private boolean targetFound = false;
    private boolean finished = false;

    public AprilTagPIDTurn(Drivetrain drivetrain, PhotonCamera camera, Pigeon2 pigeon, int targetId) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.pigeon = pigeon;
        this.setpoint = pigeon.getYaw();
        this.targetId = targetId;
        addRequirements(drivetrain);
    }

    /** Looks for the target that we are searching for in what the camera can see currently.
     * If we find the target with the fiducial id specified in the contructor, we set its
     * current yaw (degree angle away from the camera) to be the setpoint for the pid.*/
    @Override
    public void initialize() {
        pigeon.setYaw(0);
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            List<PhotonTrackedTarget> targets = result.getTargets();
            for (PhotonTrackedTarget target : targets) {
                if (target.getFiducialId() == targetId) {
                    setpoint = target.getYaw();
                    targetFound = true;
                    break;
                }
            }
        }
    }

    /** If we are not within 1.5 degrees of the target and if we have found a target,
     * set motor speeds to what the pid has calculated, basing angular measurements
     * from data obtained from the pigeon.
     */
    @Override
    public void execute() {
        if (!finished && Math.abs(pigeon.getYaw() - setpoint) > 1.5 && targetFound) {
            double output = pid.calculate(pigeon.getYaw(), setpoint);
            drivetrain.setPercent(output, -1*output);
        } else finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}

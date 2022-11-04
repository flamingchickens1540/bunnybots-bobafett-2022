package org.team1540.bobafett.commands.vision;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants;
import org.team1540.bobafett.commands.drivetrain.Drivetrain;

import java.util.List;

/**
 * Command for turning the robot towards an AprilTag with a specified fiducial ID
 * using a PID controller. Uses a Pigeon 2.0 for rotational sensing.
 */
public class AprilTagPIDTurn extends CommandBase {

    private final Drivetrain drivetrain;
    private final PhotonCamera camera;
    private final Pigeon2 pigeon; // Haven't tested this yet
    private final PIDController pid = new PIDController(
            Constants.VisionConstants.KP, Constants.VisionConstants.KI, Constants.VisionConstants.KD); // This somehow works without the i and d
    private final int targetId;
    private double setpoint;
    private boolean finished;

    public AprilTagPIDTurn(Drivetrain drivetrain, PhotonCamera camera, Pigeon2 pigeon, int targetId) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.pigeon = pigeon;
        this.setpoint = pigeon.getYaw();
        this.targetId = targetId;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        pigeon.setYaw(0);
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            List<PhotonTrackedTarget> targets = result.getTargets();
            for (PhotonTrackedTarget target : targets) {
                if (target.getFiducialId() == targetId) {
                    setpoint = target.getYaw();
                    break;
                }
            }
        }
    }

    @Override
    public void execute() {
        if (!finished || Math.abs(pigeon.getYaw() - setpoint) > 3) {
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

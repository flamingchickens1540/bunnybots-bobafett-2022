package frc.robot.commands.vision;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.drivetrain.Drivetrain;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.List;

public class AprilTagPID extends CommandBase {

    private final Drivetrain drivetrain;
    private final PhotonCamera camera;
    private final AHRS navx; // Need to change this to a Pigeon
    private final PIDController pid = new PIDController(0.02, 0, 0); // This somehow works without the i and d
    private final int targetId;
    private double setpoint;
    private boolean finished;

    public AprilTagPID(Drivetrain drivetrain, PhotonCamera camera, AHRS navx, int targetId) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.navx = navx;
        this.setpoint = navx.getYaw();
        this.targetId = targetId;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        navx.zeroYaw();
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
        if (!finished || Math.abs(navx.getYaw() - setpoint) > 3) {
            double output = pid.calculate(navx.getYaw(), setpoint);
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

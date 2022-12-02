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
            Constants.VisionConstants.KP, Constants.VisionConstants.KI, Constants.VisionConstants.KD); // This somehow works without the i and d
    private final int targetId;
    private double setpoint;
    private boolean targetFound = false;
    private boolean finished = false;

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
            targetFound = true;
        } else setpoint = 0;
    }

    /** If we are not within 1.5 degrees of the target and if we have found a target,
     * set motor speeds to what the pid has calculated, basing angular measurements
     * from data obtained from the pigeon.
     */
    @Override
    public void execute() {
        if (!finished && Math.abs(drivetrain.getYaw() - setpoint) > 1.5 && targetFound) {
            double output = pid.calculate(drivetrain.getYaw(), setpoint);
            drivetrain.setPercent(output, -1*output);
        } else finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
    }
}

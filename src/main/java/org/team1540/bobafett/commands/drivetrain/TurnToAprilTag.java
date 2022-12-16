package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.team1540.bobafett.Constants.*;
import org.team1540.bobafett.utils.ChickenPhotonCamera;
import org.team1540.bobafett.utils.RollingAverage;

/**
 * Command for turning the robot towards an AprilTag with a specified fiducial ID
 * using a PID controller. Uses a Pigeon 2.0 for rotational sensing.
 */

public class TurnToAprilTag extends CommandBase {

    private final Drivetrain drivetrain;
    private final ChickenPhotonCamera camera;
    private final PIDController pidController = new PIDController(
            DriveConstants.DRIVE_KP, DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD);
    private final int targetId;
    private final RollingAverage rollingAverage;
    private double setpoint;
    private NeutralMode originalBrakeMode;

    public TurnToAprilTag(Drivetrain drivetrain, ChickenPhotonCamera camera, int targetId) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.setpoint = drivetrain.getYaw();
        this.targetId = targetId;
        this.rollingAverage = new RollingAverage(10);
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
            setpoint = -1 * target.getYaw();
        } else setpoint = drivetrain.getYaw(); // Set setpoint to current heading if no target is found
        originalBrakeMode = drivetrain.getBrakeMode();
        drivetrain.setBrakeMode(NeutralMode.Brake);
    }

    /** If we are not within 1.5 degrees of the target and if we have found a target,
     * set motor speeds to what the pid has calculated, basing angular measurements
     * from data obtained from the pigeon.
     */
    @Override
    public void execute() {
        double output = pidController.calculate(drivetrain.getYaw(), setpoint);
        rollingAverage.add(Math.abs(drivetrain.getYaw() - setpoint));
        drivetrain.setPercent(-1*output, output);
        pidController.setPID(
                SmartDashboard.getNumber("Drivetrain/kP", DriveConstants.DRIVE_KP),
                SmartDashboard.getNumber("Drivetrain/kI", DriveConstants.DRIVE_KI),
                SmartDashboard.getNumber("Drivetrain/kD", DriveConstants.DRIVE_KD)
        );
    }

    @Override
    public boolean isFinished() {
        return rollingAverage.getAverage() < 1;
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
        drivetrain.setBrakeMode(originalBrakeMode);
    }
}

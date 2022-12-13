package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team1540.bobafett.Constants;

public class PigeonTurn extends CommandBase {

    private final Drivetrain drivetrain;
    private final double setpoint;
    private final PIDController pidController = new PIDController(
            SmartDashboard.getNumber("Drivetrain/kP", Constants.DriveConstants.DRIVE_KP),
            SmartDashboard.getNumber("Drivetrain/kI", Constants.DriveConstants.DRIVE_KI),
            SmartDashboard.getNumber("Drivetrain/kD", Constants.DriveConstants.DRIVE_KD)
    );
    private NeutralMode originalBrakeMode;

    public PigeonTurn(Drivetrain drivetrain, double degrees) {
        this.drivetrain = drivetrain;
        setpoint = degrees;
        addRequirements(drivetrain);
        pidController.setSetpoint(degrees);
        drivetrain.setBrakeMode(NeutralMode.Brake);
        SmartDashboard.putNumber("Drivetrain/kP", Constants.DriveConstants.DRIVE_KP);
        SmartDashboard.putNumber("Drivetrain/kI", Constants.DriveConstants.DRIVE_KI);
        SmartDashboard.putNumber("Drivetrain/kD", Constants.DriveConstants.DRIVE_KD);
    }

    public void initialize() {
        drivetrain.setYaw(0);
        originalBrakeMode = drivetrain.getBrakeType();
    }

    public void execute() {
        double pidOut = pidController.calculate(drivetrain.getYaw());
        drivetrain.setPercent(pidOut, -1*pidOut);
        pidController.setP(SmartDashboard.getNumber("Drivetrain/kP", Constants.DriveConstants.DRIVE_KP));
        pidController.setI(SmartDashboard.getNumber("Drivetrain/kI", Constants.DriveConstants.DRIVE_KI));
        pidController.setD(SmartDashboard.getNumber("Drivetrain/kD", Constants.DriveConstants.DRIVE_KD));
    }

    //public boolean isFinished() {
    //   return Math.abs(drivetrain.getYaw() - setpoint) < 1.5;
    //}

    public void end(boolean interrupted) {
        drivetrain.stop();
        drivetrain.setBrakeMode(originalBrakeMode);
    }
}

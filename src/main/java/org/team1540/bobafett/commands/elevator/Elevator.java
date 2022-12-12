package org.team1540.bobafett.commands.elevator;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants;

public class Elevator extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(
            Constants.ElevatorConstants.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final RelativeEncoder encoder = motor.getEncoder();
    private final SparkMaxPIDController pidController = motor.getPIDController();
    private final SparkMaxLimitSwitch topLimitSwitch = motor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private final SparkMaxLimitSwitch bottomLimitSwitch = motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);

    public Elevator() {
        motor.setInverted(true);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        motor.setSmartCurrentLimit(40);
        encoder.setPosition(0);
        // Initialize PID constants
        pidController.setP(Constants.ElevatorConstants.ELEVATOR_KP);
        pidController.setI(Constants.ElevatorConstants.ELEVATOR_KI);
        pidController.setD(Constants.ElevatorConstants.ELEVATOR_KD);
        // Put PID constants on SmartDashboard
        SmartDashboard.setDefaultNumber("Elevator/kP", Constants.ElevatorConstants.ELEVATOR_KP);
        SmartDashboard.setDefaultNumber("Elevator/kI", Constants.ElevatorConstants.ELEVATOR_KI);
        SmartDashboard.setDefaultNumber("Elevator/kD", Constants.ElevatorConstants.ELEVATOR_KD);
    }

    public boolean getTopLimitSwitch() {
        return topLimitSwitch.isPressed();
    }

    public boolean getBottomLimitSwitch() {
        return bottomLimitSwitch.isPressed();
    }

    public double getRotations() {
        return encoder.getPosition();
    }

    public void setPercent(double speed) {
        motor.set(speed);
    }

    public void setPidReference(double setpoint, CANSparkMax.ControlType controlType) {
        pidController.setReference(setpoint, controlType);
    }

    public void setReferencePosition() {
        encoder.setPosition(0);
    }

    public void stop() {
        motor.set(0);
    }

    public void hold() {
        pidController.setReference(getRotations(), CANSparkMax.ControlType.kPosition);
    }

    public void periodic() {
        pidController.setP(SmartDashboard.getNumber("Elevator/kP", Constants.ElevatorConstants.ELEVATOR_KP));
        pidController.setI(SmartDashboard.getNumber("Elevator/kI", Constants.ElevatorConstants.ELEVATOR_KI));
        pidController.setD(SmartDashboard.getNumber("Elevator/kD", Constants.ElevatorConstants.ELEVATOR_KD));
    }
}

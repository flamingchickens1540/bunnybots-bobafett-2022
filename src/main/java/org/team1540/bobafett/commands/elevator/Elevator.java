package org.team1540.bobafett.commands.elevator;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants.*;

public class Elevator extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(
            ElevatorConstants.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
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
        pidController.setP(ElevatorConstants.ELEVATOR_KP);
        pidController.setI(ElevatorConstants.ELEVATOR_KI);
        pidController.setD(ElevatorConstants.ELEVATOR_KD);
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
}

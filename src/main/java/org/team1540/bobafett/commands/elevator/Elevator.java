package org.team1540.bobafett.commands.elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants;

public class Elevator extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(
            Constants.ElevatorConstants.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final RelativeEncoder encoder = motor.getEncoder();
    private final SparkMaxPIDController pidController = motor.getPIDController(); // Use this to write an elevator pid
    private final DigitalInput topLimitSwitch = new DigitalInput(Constants.ElevatorConstants.TOP_LIMIT_SWITCH_ID);
    private final DigitalInput bottomLimitSwitch = new DigitalInput(Constants.ElevatorConstants.BOTTOM_LIMIT_SWITCH_ID);
    private double originalPosition;

    public Elevator() {
        motor.setInverted(true);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setOriginalPosition(double position) {
        originalPosition = position;
    }

    public boolean getTopLimitSwitch() {
        return topLimitSwitch.get();
    }

    public boolean getBottomLimitSwitch() {
        return bottomLimitSwitch.get();
    }

    public double getRotations() {
        return encoder.getPosition();
    }

    public double getRotationsFromTop() {
        return encoder.getPosition() - originalPosition;
    }

    public void setPercent(double speed) {
        if (speed > 0 && topLimitSwitch.get() || speed < 0 && bottomLimitSwitch.get()) motor.set(0);
        else motor.set(speed);
    }

    public void stop() {
        setPercent(0);
    }
}

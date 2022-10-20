package frc.robot.commands.elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DigitalInput topLimitSwitch = new DigitalInput(0);
    private final DigitalInput bottomLimitSwitch = new DigitalInput(1);

    public Elevator() {
        motor.setInverted(false);
    }

    public void setPercent(double speed) {
        if (speed > 0 && topLimitSwitch.get() || speed < 0 && bottomLimitSwitch.get()) motor.set(0);
        else motor.set(speed);
    }
}

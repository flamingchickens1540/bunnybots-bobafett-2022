package frc.robot.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    private final VictorSPX leftFront = new VictorSPX(1);
    private final VictorSPX rightFront = new VictorSPX(2);
    private final VictorSPX leftRear = new VictorSPX(3);
    private final VictorSPX rightRear = new VictorSPX(4);

    public Drivetrain() {
        leftFront.setInverted(false);
        leftRear.setInverted(false);
        rightFront.setInverted(true);
        rightRear.setInverted(true);
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);
    }

    public void setPercent(double left, double right) {
        leftFront.set(ControlMode.PercentOutput, left);
        rightFront.set(ControlMode.PercentOutput, right);
    }

    public void stop() {
        setPercent(0, 0);
    }
}

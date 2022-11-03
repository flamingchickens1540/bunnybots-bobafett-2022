package frc.robot.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    private final VictorSPX leftFront = new VictorSPX(1);
    private final VictorSPX rightFront = new VictorSPX(2);
    private final VictorSPX leftRear = new VictorSPX(3);
    private final VictorSPX rightRear = new VictorSPX(4);
    private final VictorSPX[] leftDrive = {leftFront, leftRear};
    private final VictorSPX[] rightDrive = {rightFront, rightRear};
    private final VictorSPX[] drive = {leftFront, rightFront, leftRear, rightRear};
    private final Pigeon2 pigeon = new Pigeon2(0);

    public Drivetrain() {
        for (VictorSPX motor : leftDrive) motor.setInverted(false);
        for (VictorSPX motor : rightDrive) motor.setInverted(true);
        for (VictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Brake);
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);
    }

    public void setPercent(double left, double right) {
        leftFront.set(ControlMode.PercentOutput, left);
        rightFront.set(ControlMode.PercentOutput, right);
    }

    public void resetHeading() {
        pigeon.setYaw(0);
    }

    public void stop() {
        setPercent(0, 0);
    }
}

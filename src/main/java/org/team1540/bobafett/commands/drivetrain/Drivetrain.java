package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants;

public class Drivetrain extends SubsystemBase {

    private final VictorSPX leftFront = new VictorSPX(Constants.DriveConstants.LEFT_FRONT);
    private final VictorSPX rightFront = new VictorSPX(Constants.DriveConstants.RIGHT_FRONT);
    private final VictorSPX leftRear = new VictorSPX(Constants.DriveConstants.LEFT_REAR);
    private final VictorSPX rightRear = new VictorSPX(Constants.DriveConstants.RIGHT_REAR);
    private final VictorSPX[] leftDrive = {leftFront, leftRear};
    private final VictorSPX[] rightDrive = {rightFront, rightRear};
    private final VictorSPX[] drive = {leftFront, rightFront, leftRear, rightRear};
    private final Pigeon2 pigeon;

    public Drivetrain(Pigeon2 pigeon) {
        for (VictorSPX motor : leftDrive) motor.setInverted(true);
        for (VictorSPX motor : rightDrive) motor.setInverted(false);
        for (VictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Brake);
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);
        this.pigeon = pigeon;
    }

    public Drivetrain(NeutralMode brakeType, Pigeon2 pigeon) {
        for (VictorSPX motor : leftDrive) motor.setInverted(true);
        for (VictorSPX motor : rightDrive) motor.setInverted(false);
        for (VictorSPX motor : drive) motor.setNeutralMode(brakeType);
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);
        this.pigeon = pigeon;
    }

    public void setPercent(double left, double right) {
        leftFront.set(ControlMode.PercentOutput, left);
        rightFront.set(ControlMode.PercentOutput, right);
    }

    public double getYaw() {
        return pigeon.getYaw();
    }

    public void setYaw(double yaw) {
        pigeon.setYaw(yaw);
    }

    public void stop() {
        setPercent(0, 0);
    }
}

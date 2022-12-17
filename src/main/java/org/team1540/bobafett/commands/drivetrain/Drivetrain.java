package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants.*;
import org.team1540.bobafett.utils.ChickenVictorSPX;

public class Drivetrain extends SubsystemBase {

    private final ChickenVictorSPX leftFront = new ChickenVictorSPX(DriveConstants.LEFT_FRONT);
    private final ChickenVictorSPX rightFront = new ChickenVictorSPX(DriveConstants.RIGHT_FRONT);
    private final ChickenVictorSPX leftRear = new ChickenVictorSPX(DriveConstants.LEFT_REAR);
    private final ChickenVictorSPX rightRear = new ChickenVictorSPX(DriveConstants.RIGHT_REAR);
    private final ChickenVictorSPX[] leftDrive = {leftFront, leftRear};
    private final ChickenVictorSPX[] rightDrive = {rightFront, rightRear};
    private final ChickenVictorSPX[] drive = {leftFront, rightFront, leftRear, rightRear};
    private final Pigeon2 pigeon;

    public Drivetrain(Pigeon2 pigeon) {
        for (ChickenVictorSPX motor : leftDrive) motor.setInverted(false);
        for (ChickenVictorSPX motor : rightDrive) motor.setInverted(true);
        for (ChickenVictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Brake);
        leftRear.follow(leftFront);
        rightRear.follow(rightFront);
        this.pigeon = pigeon;
    }

    public Drivetrain(NeutralMode brakeType, Pigeon2 pigeon) {
        for (ChickenVictorSPX motor : leftDrive) motor.setInverted(false);
        for (ChickenVictorSPX motor : rightDrive) motor.setInverted(true);
        for (ChickenVictorSPX motor : drive) motor.setNeutralMode(brakeType);
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

    public void setBrakeMode(NeutralMode brakeMode) {
        for (ChickenVictorSPX motor : drive) motor.setNeutralMode(brakeMode);
    }

    public void toggleBrakeMode() {
        if (leftFront.getNeutralMode() == NeutralMode.Brake)
            for (ChickenVictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Coast);
        else for (ChickenVictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Brake);
    }

    public NeutralMode getBrakeMode() {
        return leftFront.getNeutralMode();
    }

    public void stop() {
        setPercent(0, 0);
    }

    @Override
    public void periodic() {
    }
}

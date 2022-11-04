package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

    public Drivetrain() {
        for (VictorSPX motor : leftDrive) motor.setInverted(true);
        for (VictorSPX motor : rightDrive) motor.setInverted(false);
        for (VictorSPX motor : drive) motor.setNeutralMode(NeutralMode.Brake);
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

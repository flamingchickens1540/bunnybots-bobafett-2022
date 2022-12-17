package org.team1540.bobafett.commands.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveSlightly extends CommandBase {

    private final Drivetrain drivetrain;
    private final double speed;
    private NeutralMode originalBrakeMode;
    private int counter;
    public MoveSlightly(Drivetrain drivetrain, double speed) {
        this.drivetrain = drivetrain;
        this.speed = speed;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        originalBrakeMode = drivetrain.getBrakeMode();
        drivetrain.setBrakeMode(NeutralMode.Brake);
        drivetrain.setPercent(speed, speed);
        counter = 0;
    }

    @Override
    public void execute() {
        counter++;
        System.out.println(counter);
    }

    @Override
    public boolean isFinished() {
        return counter >= 20;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
        drivetrain.setBrakeMode(originalBrakeMode);
    }
}

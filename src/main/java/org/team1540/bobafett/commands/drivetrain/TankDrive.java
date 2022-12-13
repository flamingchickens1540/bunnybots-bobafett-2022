package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Standard drive, left stick controls left side of drivetrain, right stick controls right side.
 */
public class TankDrive extends CommandBase {

    private final Drivetrain drivetrain;
    private final XboxController controller;
    private final double deadzone = 0.15;

    public TankDrive(Drivetrain drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double left = Math.abs(controller.getLeftY()) > deadzone ? Math.pow(controller.getLeftY(), 3) : 0;
        double right = Math.abs(controller.getRightY()) > deadzone ? Math.pow(controller.getRightY(), 3) : 0;
        double forward = Math.abs(controller.getRightTriggerAxis()) > deadzone ? Math.pow(controller.getRightTriggerAxis(), 3) : 0;
        double backward = Math.abs(controller.getLeftTriggerAxis()) > deadzone ? Math.pow(controller.getLeftTriggerAxis(), 3) : 0;
        drivetrain.setPercent(0.5*(left - forward + backward), 0.5*(right - forward + backward));
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
    }
}

package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Arguably more intuitive driving system than Tank Drive.
 * Uses left stick for throttle and right stick for turning
 */
public class ArcadeDrive extends CommandBase {
    private final XboxController controller;
    private final Drivetrain drivetrain;
    private final double deadzone = 0.15;

    public ArcadeDrive(Drivetrain drivetrain, XboxController controller) {
        this.controller = controller;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double turn = Math.abs(controller.getRightX()) > deadzone ? 0.5*controller.getRightX() : 0;
        double throttle = Math.abs(controller.getLeftY()) > deadzone ? 0.5*controller.getLeftY() : 0;
        drivetrain.setPercent(throttle - turn, throttle + turn);
    }

    @Override
    public void end(boolean isInterrupted) {
        drivetrain.stop();
    }
}

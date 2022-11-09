package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Arguably more intuitive driving system than Tank Drive.
 * Uses left stick for throttle and right stick for turning
 */
public class ArcadeDrive extends CommandBase {
    private final XboxController controller;
    private final Drivetrain drivetrain;

    public ArcadeDrive(Drivetrain drivetrain, XboxController controller) {
        this.controller = controller;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double throttle = 0.5*controller.getRightX();
        double turn = 0.5*controller.getLeftY();
        drivetrain.setPercent(turn - throttle, turn + throttle);
    }
}

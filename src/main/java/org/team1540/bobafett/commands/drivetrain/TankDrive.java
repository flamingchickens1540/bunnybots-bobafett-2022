package org.team1540.bobafett.commands.drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Standard drive, left stick controls left side of drivetrain, right stick controls right side.
 */
public class TankDrive extends CommandBase {

    private final Drivetrain drivetrain;
    private final XboxController controller;

    public TankDrive(Drivetrain drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.setPercent(controller.getLeftY() * 0.5, controller.getRightY() * 0.5);
    }
}

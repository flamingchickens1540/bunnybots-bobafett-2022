package org.team1540.bobafett.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Move extends CommandBase {

    private final Elevator elevator;
    private final XboxController controller;

    public Move(Elevator elevator, XboxController controller) {
        this.elevator = elevator;
        this.controller = controller;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        System.out.println(elevator.getRotations());
        elevator.setPercent(controller.getLeftTriggerAxis()*0.5 - controller.getRightTriggerAxis()*0.5);
    }
}

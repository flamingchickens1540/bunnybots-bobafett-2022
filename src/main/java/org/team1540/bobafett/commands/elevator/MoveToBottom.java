package org.team1540.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToBottom extends CommandBase {

    private final Elevator elevator;

    public MoveToBottom(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    public void execute() {
        elevator.setPercent(0.25);
    }

    public boolean isFinished() {
        return elevator.getBottomLimitSwitch();
    }

    public void end(boolean interrupted) {
        elevator.stop();
    }
}

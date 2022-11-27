package org.team1540.bobafett.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveToBottom extends CommandBase {

    private final Elevator elevator;

    public MoveToBottom(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setPercent(-0.5);
    }

    @Override
    public boolean isFinished() {
        return elevator.getBottomLimitSwitch();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
        elevator.setReferencePosition();
    }
}

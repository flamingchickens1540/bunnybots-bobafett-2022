package org.team1540.bobafett.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

// Use this command at the beginning of the match to set the original position of the elevator
public class MoveToTop extends CommandBase {

    private final Elevator elevator;

    public MoveToTop(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setPercent(0.25);
    }

    @Override
    public boolean isFinished() {
        return !elevator.getTopLimitSwitch();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
        elevator.setOriginalPosition(elevator.getRotations());
    }
}

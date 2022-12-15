package org.team1540.bobafett.commands.elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team1540.bobafett.Constants.*;

public class ElevatorCommand extends CommandBase {
    private final Joystick joystick;
    private final Elevator elevator;
    private final double deadzone = 0.15;

    public ElevatorCommand(Elevator elevator, Joystick joystick) {
        this.elevator = elevator;
        this.joystick = joystick;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        double out = (Math.abs(joystick.getY()) > deadzone) ? Math.pow(joystick.getY(), 3) : 0;
        if (elevator.getBottomLimitSwitch() && out == 0) elevator.setPercent(0);
        else elevator.setPercent(0.5*out + ElevatorConstants.ELEVATOR_HOLD_SPEED);
    }
}

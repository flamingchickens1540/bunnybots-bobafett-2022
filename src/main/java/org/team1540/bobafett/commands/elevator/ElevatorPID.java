package org.team1540.bobafett.commands.elevator;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorPID extends CommandBase {

    private final Elevator elevator;
    private final double setpoint;

    public ElevatorPID(Elevator elevator, double setpoint) {
        this.elevator = elevator;
        this.setpoint = setpoint;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setPidReference(setpoint, CANSparkMax.ControlType.kPosition); // Not sure if I need to continuously call this
    }

    public void end(boolean interrupted) {
        elevator.stop();
    }
}

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
    public void initialize() {
        elevator.setPidReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public boolean isFinished() {
        return Math.abs(setpoint - elevator.getRotations()) <= 2;
    }

    public void end(boolean interrupted) {
        elevator.hold();
    }
}

package org.team1540.bobafett.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class CloseClaw extends CommandBase {

    private final Claw claw;

    public CloseClaw(Claw claw) {
        this.claw = claw;
        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.set(false);
    }

    @Override
    public void end(boolean isInterrupted) {
        claw.set(true);
    }
}

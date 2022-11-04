package org.team1540.bobafett.commands.claw;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants;

public class Claw extends SubsystemBase {
    private final Solenoid solenoid = new Solenoid(
            0, PneumaticsModuleType.CTREPCM, Constants.ClawConstants.SOLENOID_CHANNEL);

    public void toggleClosed() {
        solenoid.set(!solenoid.get());
    }
}

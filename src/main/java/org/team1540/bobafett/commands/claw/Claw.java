package org.team1540.bobafett.commands.claw;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team1540.bobafett.Constants;

/** Highly advanced and difficult to explain subsystem that uses pneumatics
 * to control a claw that grabs things.
 */

public class Claw extends SubsystemBase {
    private final Solenoid solenoid = new Solenoid(
            0, PneumaticsModuleType.CTREPCM, Constants.ClawConstants.SOLENOID_CHANNEL
    );

    public Claw() {
        set(true);
    }

    public void set(boolean isOpen) {
        solenoid.set(!isOpen);
    }
}

package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
    private final Solenoid solenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 4);

    public void toggleClosed() {
        solenoid.set(!solenoid.get());
    }
}

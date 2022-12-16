package org.team1540.bobafett.utils;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ChickenVictorSPX extends VictorSPX {

    private NeutralMode neutralMode;
    /**
     * Constructor
     * Initializes the motor to brake mode
     * @param deviceNumber [0,62]
     */
    public ChickenVictorSPX(int deviceNumber) {
        super(deviceNumber);
        setNeutralMode(NeutralMode.Brake);
        neutralMode = NeutralMode.Brake;
    }

    @Override
    public void setNeutralMode(NeutralMode neutralMode) {
        super.setNeutralMode(neutralMode);
        this.neutralMode = neutralMode;
    }

    public NeutralMode getNeutralMode() {
        return neutralMode;
    }
}
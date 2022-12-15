package org.team1540.bobafett.utils;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ChickenVictorSPX extends VictorSPX {

    private NeutralMode neutralMode;
    /**
     * Constructor
     *
     * @param deviceNumber [0,62]
     */
    public ChickenVictorSPX(int deviceNumber) {
        super(deviceNumber);
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
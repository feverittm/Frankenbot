package frc.robot.misc;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * remove this if you aren't using it
 */
public class GearBox {

    public WPI_TalonSRX talon;
    public WPI_VictorSPX victor1;

    // Set some gearboxes
    public GearBox(WPI_TalonSRX talon, WPI_VictorSPX victor1) {
        this.talon = talon;
        this.victor1 = victor1;
    }

}

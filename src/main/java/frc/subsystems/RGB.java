package frc.subsystems;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RGB extends SubsystemBase {

    private PWMSparkMax lights;

    public RGB() {
        lights = new PWMSparkMax(7);
    }

    public void beLit() {
        lights.set(0.59);
    }


}

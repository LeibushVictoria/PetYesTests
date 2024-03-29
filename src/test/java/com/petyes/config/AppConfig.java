package com.petyes.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/${env}.properties"
})
public interface AppConfig extends Config {

    String webUrl();
    String apiUrl();
    String breederPhoneNumber();
    String customerPhoneNumber();
    String adminPhoneNumber();
    String userPassword();
    String adminPassword();
    int specialization();
    int breed();
}
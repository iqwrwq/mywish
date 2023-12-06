package de.shopitech.mywish.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialAdminUserCreator implements ApplicationRunner {

    private final DefaultAdminService defaultAdminService;

    @Autowired
    public InitialAdminUserCreator(DefaultAdminService defaultAdminService) {
        this.defaultAdminService = defaultAdminService;
    }

    @Override
    public void run(ApplicationArguments args) {
        defaultAdminService.createDefaultAdminUser();
    }
}

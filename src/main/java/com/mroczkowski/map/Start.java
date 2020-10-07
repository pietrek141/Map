package com.mroczkowski.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Start {

    public Start(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {

/*        AppUser appAdmin = new AppUser();
        appAdmin.setUsername("Piotr");
        appAdmin.setPassword(passwordEncoder.encode("Piotr123"));
        appAdmin.setRole(Role.ADMIN);

        AppUser appUser = new AppUser();
        appUser.setUsername("Bogdan");
        appUser.setPassword(passwordEncoder.encode("Bogdan123"));
        appUser.setRole(Role.USER);

        appUserRepo.save(appAdmin);
        appUserRepo.save(appUser);*/
    }
}

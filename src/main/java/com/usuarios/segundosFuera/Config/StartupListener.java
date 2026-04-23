package com.usuarios.segundosFuera.Config;

import com.usuarios.segundosFuera.Services.SFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupListener {

    @Autowired
    private SFService sfService;

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationStart() {
        System.out.println("Aplicación iniciada. Verificando usuarios con suscripción expirada...");
        sfService.checkAndUpdateExpiredUsers();
        System.out.println("Verificación de expiración completada.");
    }
}

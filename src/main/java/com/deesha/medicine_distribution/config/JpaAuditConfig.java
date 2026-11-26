package com.deesha.medicine_distribution.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditConfig {

//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> {
////            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////            if (auth == null || !auth.isAuthenticated()
////                    || auth instanceof AnonymousAuthenticationToken) {
////                return Optional.of("system");
////            }
////            return Optional.of(auth.getName());
//            return null;
//        };


//    }

    @Bean
    public AuditorAware<String> auditorAware() {
        System.out.println(">>> AuditorAware bean created");
        return () -> Optional.of("test-user");
    }
}
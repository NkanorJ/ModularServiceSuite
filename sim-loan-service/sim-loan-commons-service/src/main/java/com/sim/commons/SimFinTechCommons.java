package com.sim.commons;

import com.sim.commons.messages.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Import({MessageSourceService.class})
public class SimFinTechCommons {

    private final MessageSource messageSource;

    @Bean
    public MessageSourceService messageSourceService() {
        return new MessageSourceService(messageSource);
    }

}

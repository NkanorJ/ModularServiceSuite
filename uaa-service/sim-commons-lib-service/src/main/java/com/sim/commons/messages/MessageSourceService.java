package com.sim.commons.messages;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSourceService {

    private final MessageSource messageSource;

    public String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, new Object[]{}, messageKey.replace(".", " "), LocaleContextHolder.getLocale());
    }
}

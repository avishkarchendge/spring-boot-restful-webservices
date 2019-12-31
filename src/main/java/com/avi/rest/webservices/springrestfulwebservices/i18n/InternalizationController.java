package com.avi.rest.webservices.springrestfulwebservices.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalizationController {

    @Autowired
    private MessageSource messageSource;

//    @GetMapping("/internalization")
//    public String getLocaleData(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
//        return messageSource.getMessage("good.morning.message", null, locale);
//    }

    @GetMapping("/internalization")
    public String getLocaleData() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

}

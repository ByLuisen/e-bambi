package com.e.bambi.shared.infrastructure.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/swagger-ui")
public class OpenApiController {
    @GetMapping(path="/swagger-ui.css", produces = "text/css")
    public String getCss() {
        String orig = toText(getClass().getResourceAsStream("/META-INF/resources/webjars/swagger-ui/5.18.3/swagger-ui.css"));
        String append = toText(getClass().getResourceAsStream("/static/swagger-ui/themes/dark-mode.css"));
        return orig + append;
    }

    static String toText(InputStream in) {
        return new BufferedReader( new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }
}

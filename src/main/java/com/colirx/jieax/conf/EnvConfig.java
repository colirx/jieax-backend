package com.colirx.jieax.conf;

import com.colirx.jieax.JieaxBackendApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

@Configuration
public class EnvConfig {

    private static final Logger log = LoggerFactory.getLogger(JieaxBackendApplication.class);

    @Bean
    public MapPropertySource dotEnv(ConfigurableEnvironment env) {
        Dotenv dotenv = Dotenv.configure()
            // 默认情况下 .env 文件位于项目根目录下，直接读取
            .directory("./")
            .filename(".env")
            .load();
        HashMap<String, Object> envProperties = new HashMap<>();
        dotenv.entries().forEach(entry -> {
            envProperties.put(entry.getKey(), entry.getValue());
        });
        MapPropertySource dotEnvPropertySource = new MapPropertySource("dotenv", envProperties);
        env.getPropertySources().addFirst(dotEnvPropertySource);

        log.info("Loaded .env properties into Spring environment.");
        log.warn("Loaded .env properties into Spring environment.");
        log.error("Loaded .env properties into Spring environment.");
        return dotEnvPropertySource;
    }
}

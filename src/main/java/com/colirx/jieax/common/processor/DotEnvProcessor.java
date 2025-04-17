package com.colirx.jieax.common.processor;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotEnvProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 加载 .env 文件
        Map<String, Object> envProperties = new HashMap<>();
        Dotenv dotenv = Dotenv.configure()
            // 默认情况下 .env 文件位于项目根目录下，直接读取
            .directory("./")
            .filename(".env")
            .load();
        dotenv.entries().forEach(entry -> envProperties.put(entry.getKey(), entry.getValue()));

        // 注册到 Spring 环境中
        MapPropertySource propertySource = new MapPropertySource("dotenv", envProperties);
        environment.getPropertySources().addFirst(propertySource);
    }
}

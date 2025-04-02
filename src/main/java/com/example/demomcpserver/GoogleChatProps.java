package com.example.demomcpserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "googlechat")
public record GoogleChatProps(String webhookUrl) {
}

package com.example.demomcpserver;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class McpConfig {

	@Bean
	public ToolCallbackProvider tools(CurrentDateService currentDateService, AppleCalendarService appleCalendarService,
			GoogleChatWebhookService googleChatWebhookService, HotelRecommendationService hotelRecommendationService,
			OnePieceSpoilerService onePieceSpoilerService) {
		return MethodToolCallbackProvider.builder()
			.toolObjects(currentDateService, appleCalendarService, googleChatWebhookService, onePieceSpoilerService,
					hotelRecommendationService)
			.build();
	}

}

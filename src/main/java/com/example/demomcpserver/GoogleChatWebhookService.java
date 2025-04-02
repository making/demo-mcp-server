package com.example.demomcpserver;

import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GoogleChatWebhookService {

	private final RestClient restClient;

	public GoogleChatWebhookService(RestClient.Builder restClientBuilder, GoogleChatProps props) {
		this.restClient = restClientBuilder.baseUrl(props.webhookUrl()).build();
	}

	@Tool(description = "Send a message to Google Chat")
	public void sendMessage(@ToolParam(description = "message text") String text) {
		this.restClient.post()
			.contentType(MediaType.APPLICATION_JSON)
			.body(Map.of("text", text))
			.retrieve()
			.toBodilessEntity();
	}

}

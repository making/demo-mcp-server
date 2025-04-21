package com.example.demomcpserver;

import java.time.Instant;
import java.time.ZoneId;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class CurrentDateService {

	@Tool(description = "Returns the current date in the specified time zone.")
	public String getCurrentDate(@ToolParam(description = """
			The time-zone ID, region IDs of the form '{area}/{city}', such as 'Asia/Tokyo' or 'America/New_York'.
			""") String zoneId) {
		return Instant.now().atZone(ZoneId.of(zoneId)).toString();
	}

}

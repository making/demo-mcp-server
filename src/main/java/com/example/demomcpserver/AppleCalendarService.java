package com.example.demomcpserver;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class AppleCalendarService {

	private final AppleScriptExecutor appleScriptExecutor = new AppleScriptExecutor();

	@Tool(description = """
			Add an event to Calendar with the given title, start time, and end time.
			  The start and end times should be in the format 'yyyy-MM-dd HH:mm:ss'.
			""")
	public void addEvent(String calendarName, String title, String startTime, String endTime) throws Exception {
		String script = """
				tell application "Calendar"
				    set startDate to date "%s"
				    set endDate to date "%s"
				    tell calendar "%s"
				        make new event with properties {summary:"%s", start date:startDate, end date:endDate, description:""}
				    end tell
				    activate
				end tell
				""";
		appleScriptExecutor.execute(script.formatted(startTime, endTime, calendarName, title)).get();
	}

	@Tool(description = """
			Delete a specific event from Calendar.
			The start and end times should be in the format 'yyyy-MM-dd HH:mm:ss'.
			""")
	public void deleteEvent(String calendarName, String title, String startTime, String endTime) throws Exception {
		String script = """
				tell application "Calendar"
				    set targetStartDate to date "%s"
				    set targetEndDate to date "%s"
				    tell calendar "%s"
				        set eventsToDelete to (every event whose summary is "%s" and start date = targetStartDate and end date = targetEndDate)
				        if (count of eventsToDelete) > 0 then
				            repeat with eventToDelete in eventsToDelete
				                delete eventToDelete
				            end repeat
				        end if
				    end tell
				end tell
				""";
		appleScriptExecutor.execute(script.formatted(startTime, endTime, calendarName, title)).get();
	}

}

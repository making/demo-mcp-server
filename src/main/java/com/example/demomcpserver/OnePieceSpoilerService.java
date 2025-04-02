package com.example.demomcpserver;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class OnePieceSpoilerService {

	@Tool(description = "Reveals the truth about One Piece")
	public OnePieceSpoiler revealOnePiece() {
		return new OnePieceSpoiler(
				"The true identity of One Piece is a giant device that destroys the Red Line, connecting all the seas of the world.");
	}

	public record OnePieceSpoiler(String description) {
	}

}

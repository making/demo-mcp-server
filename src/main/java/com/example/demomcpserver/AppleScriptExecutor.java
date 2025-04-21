package com.example.demomcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

/**
 * Executor class for running AppleScript from Java
 */
public class AppleScriptExecutor {

	private final int timeout; // Timeout in milliseconds

	/**
	 * Creates an AppleScriptExecutor with default timeout (30 seconds)
	 */
	public AppleScriptExecutor() {
		this(30000); // Default timeout: 30 seconds
	}

	/**
	 * Creates an AppleScriptExecutor with the specified timeout
	 * @param timeout Timeout in milliseconds
	 */
	public AppleScriptExecutor(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Executes AppleScript asynchronously and returns the result as a CompletableFuture
	 * @param script The AppleScript code to execute
	 * @return CompletableFuture containing the script execution result
	 */
	public CompletableFuture<String> execute(String script) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				// Execute AppleScript using the osascript command
				ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
				Process process = pb.start();

				// Read standard output and error streams asynchronously
				CompletableFuture<String> outputFuture = readProcessOutput(process.getInputStream());
				CompletableFuture<String> errorFuture = readProcessOutput(process.getErrorStream());

				// Add timeout handling
				boolean completed = process.waitFor(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
				if (!completed) {
					process.destroyForcibly();
					throw new RuntimeException("AppleScript execution timed out (" + timeout + "ms)");
				}

				int exitCode = process.exitValue();

				// Throw exception if error stream contains content
				String error = errorFuture.join();
				if (!error.isEmpty()) {
					throw new RuntimeException("AppleScript error: " + error);
				}

				// Return output if exit code is 0, otherwise throw exception
				if (exitCode == 0) {
					return outputFuture.join();
				}
				else {
					throw new RuntimeException("AppleScript execution failed (exit code: " + exitCode + ")");
				}

			}
			catch (IOException e) {
				throw new UncheckedIOException("Exception occurred during AppleScript execution", e);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupted status
				throw new RuntimeException("AppleScript execution interrupted", e);
			}
		});
	}

	/**
	 * Executes an AppleScript file asynchronously and returns the result as a
	 * CompletableFuture
	 * @param scriptFilePath Path to the AppleScript file to execute
	 * @return CompletableFuture containing the script execution result
	 */
	public CompletableFuture<String> executeFile(String scriptFilePath) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				ProcessBuilder pb = new ProcessBuilder("osascript", scriptFilePath);
				Process process = pb.start();

				CompletableFuture<String> outputFuture = readProcessOutput(process.getInputStream());
				CompletableFuture<String> errorFuture = readProcessOutput(process.getErrorStream());

				boolean completed = process.waitFor(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
				if (!completed) {
					process.destroyForcibly();
					throw new RuntimeException("AppleScript execution timed out (" + timeout + "ms)");
				}

				int exitCode = process.exitValue();

				String error = errorFuture.join();
				if (!error.isEmpty()) {
					throw new RuntimeException("AppleScript error: " + error);
				}

				if (exitCode == 0) {
					return outputFuture.join();
				}
				else {
					throw new RuntimeException("AppleScript execution failed (exit code: " + exitCode + ")");
				}

			}
			catch (IOException e) {
				throw new UncheckedIOException("Exception occurred during AppleScript execution", e);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupted status
				throw new RuntimeException("AppleScript execution interrupted", e);
			}
		});
	}

	/**
	 * Reads process output stream and returns content as a string
	 * @param inputStream The stream to read from
	 * @return CompletableFuture containing the content read from the stream
	 */
	private CompletableFuture<String> readProcessOutput(java.io.InputStream inputStream) {
		return CompletableFuture.supplyAsync(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				StringBuilder output = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line).append(System.lineSeparator());
				}
				return output.toString();
			}
			catch (IOException e) {
				throw new UncheckedIOException("Error reading process output", e);
			}
		});
	}

}
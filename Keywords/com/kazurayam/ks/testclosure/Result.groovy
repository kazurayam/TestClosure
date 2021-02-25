package com.kazurayam.ks.testclosure

import java.time.LocalDateTime

class Result {
	
	private String name;
	private LocalDateTime timestamp;
 
	public Result(String name, LocalDateTime timestamp) {
		super();
		this.name = name;
		this.timestamp = timestamp;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
 
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
 
	@Override
	public String toString() {
		return "Result [name=" + name + ", value=" + timestamp.toString() + "]";
	}
}

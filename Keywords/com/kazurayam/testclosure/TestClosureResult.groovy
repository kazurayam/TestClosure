package com.kazurayam.testclosure

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


public class TestClosureResult {

	private final String name
	private final LocalDateTime startAt
	private LocalDateTime stopAt
	private String message

	public TestClosureResult(String name, LocalDateTime startAt) {
		this.name = name
		this.startAt = startAt
		stopAt = LocalDateTime.MAX
		message = ""
	}

	public String getName() {
		return name
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public TestClosureResult setStopAt(LocalDateTime stopAt) {
		this.stopAt = stopAt
		return this
	}

	public LocalDateTime getStopAt() {
		return this.stopAt
	}

	public TestClosureResult setMessage(String message) {
		this.message = message
		return this
	}

	public String getMessage() {
		return this.message
	}

	/**
	 * @return Period between startAt and stopAt, in milliseconds
	 */
	public long periodMilli() {
		long startAtEpockMilli = ZonedDateTime.of(this.startAt, ZoneId.systemDefault()).toInstant().toEpochMilli()
		long stopAtEpockMilli = ZonedDateTime.of(this.stopAt, ZoneId.systemDefault()).toInstant().toEpochMilli()
		return stopAtEpockMilli - startAtEpockMilli
	}

	@Override
	public boolean equals(Object o) {
		if (o.is(this)) {
			return true
		}
		if (!(o instanceof TestClosureResult)) {
			return false
		}
		TestClosureResult other = (TestClosureResult)o
		return this.name == other.name &&
				this.startAt == other.startAt &&
				this.stopAt == other.stopAt &&
				this.message == other.message
	}

	@Override
	public int hashCode() {
		int result = 17
		result = 31 * result + this.name.hashCode()
		result = 31 * result + this.startAt.hashCode()
		result = 31 * result + this.stopAt.hashCode()
		result = 31 * result + this.message.hashCode()
		return result
	}

	@Override
	public String toString() {
		return "TestClosureResult(name=\"${name}\", startAt=${startAt}, stopAt=${stopAt}, message=\"${message}\")"
	}
}

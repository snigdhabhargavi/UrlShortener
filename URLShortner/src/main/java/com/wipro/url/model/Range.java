package com.wipro.url.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Range {
	private long start;
	private long end;
	
	@Autowired
	public Range(@Value("0")long start, @Value("0")long end)
	{
		this.start = start;
		this.end = end;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}
	
	public void finalize()
	{
		System.out.println("I am going to die" + this.toString());
	}
	
}

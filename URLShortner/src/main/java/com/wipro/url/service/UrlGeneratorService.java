package com.wipro.url.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.wipro.url.model.Range;

@Service
public class UrlGeneratorService {
	private static final int CHAR_COUNT = 62;
	private static final char[] arr = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9' };

	@Autowired
	private Range range;
	@Autowired
	private SharedRangeService srs;
	@Value("#{range.start}")
	private long pointer;

	private synchronized void resetRange() {
		range = srs.getUniqueRange();
		pointer = range.getStart();
	}

	public synchronized String getNewURL(String original) {
		if (pointer > range.getEnd())
			resetRange();
		long count = pointer;
		StringBuilder counterString = new StringBuilder();
		while (count > 0) {

			counterString.append(arr[(int) count % CHAR_COUNT]);
			count /= CHAR_COUNT;
		}
		while (counterString.length() < 7) {
			counterString.append('a');
		}

		pointer++;
		original = original + counterString.reverse();
		System.out.println(original);
		String newurl = Hashing.murmur3_32().hashString(original, StandardCharsets.UTF_8).toString();
		return newurl;
	}
}

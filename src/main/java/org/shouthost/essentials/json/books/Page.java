package org.shouthost.essentials.json.books;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private List<String> lines = new ArrayList<String>();

	public List<String> getContent() {
		return lines;
	}

	public void setContent(String content) {
		this.lines.add(content);
	}

}

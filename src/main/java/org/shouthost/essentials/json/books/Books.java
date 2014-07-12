package org.shouthost.essentials.json.books;

import java.util.ArrayList;
import java.util.List;

public class Books {
	private String title = "Essential Book";
	private String Author = "Minecraft";
	private List<Page> page = new ArrayList<Page>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public List<Page> getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page.add(page);
	}

}

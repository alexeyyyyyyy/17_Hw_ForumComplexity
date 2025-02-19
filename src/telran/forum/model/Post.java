package telran.forum.model;

import java.time.LocalDateTime;

public class Post {
	private int postId;
	private String author;
	private String title;
	private String content;
	private LocalDateTime date;
	private int like;

	public Post(int postId, String author, String title, String content) {
		this.postId = postId;
		this.author = author;
		this.title = title;
		this.content = content;
		date = LocalDateTime.now();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getPostId() {
		return postId;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public int getLike() {
		return like;
	}

	public int addLike() {
		return ++like;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", author=" + author + ", title=" + title + ", content=" + content + ", date="
				+ date + ", like=" + like + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + postId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Post)) {
			return false;
		}
		Post other = (Post) obj;
		if (postId != other.postId) {
			return false;
		}
		return true;
	}

}

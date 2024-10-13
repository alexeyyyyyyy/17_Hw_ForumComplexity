package telran.forum.dao;

import java.time.LocalDate;

import telran.forum.model.Post;

public interface Forum {
	boolean addPost(Post post);

	boolean removePost(int postId);

	boolean updatePost(int postId, String newContent);

	Post getPostById(int postId);

	Post[] getPostsByAuthor(String author);

	Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo);
	
	Post[] getPostsByTitle(String title);
	
	Post[] getPostsByTitle(String title, LocalDate dateFrom, LocalDate dateTo);

	int size();

}

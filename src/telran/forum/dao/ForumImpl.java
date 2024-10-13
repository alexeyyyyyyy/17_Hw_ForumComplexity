package telran.forum.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

import telran.forum.model.Post;

public class ForumImpl implements Forum {
	private static final int INITIAL_CAPACITY = 10;
	private static Comparator<Post> comparator = (p1, p2) -> {
		int res = p1.getAuthor().compareTo(p2.getAuthor());
		res = res != 0 ? res : p1.getDate().compareTo(p2.getDate());
		return res != 0 ? res : Integer.compare(p1.getPostId(), p2.getPostId());
	};
	private static Comparator<Post> comparatorByTitle = (p1,p2) -> {
		int res = p1.getTitle().compareTo(p2.getTitle());
		res = res != 0 ? res : p1.getDate().compareTo(p2.getDate());
		return res != 0 ? res : Integer.compare(p1.getPostId(), p2.getPostId());
	};
	
	private Post[] posts;
	private Post[] postSortedByTitle;
	private int size;

	public ForumImpl() {
		posts = new Post[INITIAL_CAPACITY];
		postSortedByTitle = new Post[INITIAL_CAPACITY];
	}

	@Override // O(log(n)) - log
	public boolean addPost(Post post) {
		if (post == null || getPostById(post.getPostId()) != null) {
			return false;
		}
		if (posts.length == size) {
			posts = Arrays.copyOf(posts, posts.length * 2);
		}
		int index = Arrays.binarySearch(posts, 0, size, post, comparator);
		index = index >= 0 ? index : -index - 1;
		System.arraycopy(posts, index, posts, index + 1, size - index);
		posts[index] = post;
		
		index = Arrays.binarySearch(postSortedByTitle, 0, size, post, comparatorByTitle);
		index = index >= 0 ? index : -index - 1;
		System.arraycopy(postSortedByTitle, index, postSortedByTitle, index + 1, size - index);
		postSortedByTitle[index] = post;
		
		size++;
		return true;
	}

	@Override //O(n) - linear
	public boolean removePost(int postId) {
		int index = searchById(postId);
		if (index < 0) {
			return false;
		}
		System.arraycopy(posts, index + 1, posts, index, size - index - 1);
		posts = Arrays.copyOf(posts, posts.length - 1);
		
		
		for (int i = 0; i < size; i++) {
			if (postSortedByTitle[i].getPostId() == postId) {
				index =  i;
			}
		}
//		if(index < 0) {
//			return false;
//		}
		
		System.arraycopy(postSortedByTitle, index + 1, postSortedByTitle, index, size - index - 1);
		postSortedByTitle = Arrays.copyOf(postSortedByTitle, postSortedByTitle.length - 1);
		size--;
		return true;
	}

	@Override  // O(n) - linear
	public boolean updatePost(int postId, String newContent) {
		int index = searchById(postId);
		if (index < 0) {
			return false;
		}
		posts[index].setContent(newContent);
		return true;
	}

	@Override // O(n) - linear
	public Post getPostById(int postId) {
		int index = searchById(postId);
		return index < 0 ? null : posts[index];
	}

	@Override // O(log(n)) - log
	public Post[] getPostsByAuthor(String author) {
		Post pattern = new Post(Integer.MIN_VALUE, author, null, null);
		pattern.setDate(LocalDateTime.MIN);
		int from = -Arrays.binarySearch(posts, 0, size, pattern, comparator) - 1; 
		pattern = new Post(Integer.MAX_VALUE, author, null, null);
		pattern.setDate(LocalDateTime.MAX);
		int to = -Arrays.binarySearch(posts, 0, size, pattern, comparator) - 1;
		return Arrays.copyOfRange(posts, from, to);
	}

	@Override // O(log(n)) - log
	public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
		Post pattern = new Post(Integer.MIN_VALUE, author, null, null);
		pattern.setDate(dateFrom.atStartOfDay());
		int from = -Arrays.binarySearch(posts, 0, size, pattern, comparator) - 1;
		pattern = new Post(Integer.MAX_VALUE, author, null, null);
		pattern.setDate(LocalDateTime.of(dateTo, LocalTime.MAX));
		int to = -Arrays.binarySearch(posts, 0, size, pattern, comparator) - 1;
		return Arrays.copyOfRange(posts, from, to);
	}

	@Override  // O(1) - const
	public int size() {
		return size;
	}

	private int searchById(int postId) { // O(n) - linear
		for (int i = 0; i < size; i++) {
			if (posts[i].getPostId() == postId) {
				return i;
			}
		}
		return -1;
	}

	@Override // O(log(n)) - log
	public Post[] getPostsByTitle(String title) {
		Post pattern = new Post(Integer.MIN_VALUE, null, title, null);
		pattern.setDate(LocalDateTime.MIN);
		int from = -Arrays.binarySearch(postSortedByTitle,0,size, pattern,comparatorByTitle)-1;
		
		
		pattern = new Post(Integer.MAX_VALUE, null, title, null);
		pattern.setDate(LocalDateTime.MAX);
		int to = -Arrays.binarySearch(postSortedByTitle,0,size, pattern,comparatorByTitle)-1;
		return Arrays.copyOfRange(postSortedByTitle, from , to);
	}

	@Override // O(log(n)) - log
	public Post[] getPostsByTitle(String title, LocalDate dateFrom, LocalDate dateTo) {
		Post pattern = new Post(Integer.MIN_VALUE, null, title, null);
		pattern.setDate(dateFrom.atStartOfDay());
		int from = -Arrays.binarySearch(postSortedByTitle,0,size, pattern,comparatorByTitle)-1;
		
		
		pattern = new Post(Integer.MAX_VALUE, null, title, null);
		pattern.setDate(LocalDateTime.of(dateTo, LocalTime.MAX));
		int to = -Arrays.binarySearch(postSortedByTitle,0,size, pattern,comparatorByTitle)-1;
		return Arrays.copyOfRange(postSortedByTitle, from , to);
	}

}

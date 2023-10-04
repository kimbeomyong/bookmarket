package com.market.card;

import java.util.ArrayList;

import com.market.bookitem.Book;

public interface CartInterface {
//	void PrintBookList(Book[] p);
	void PrintBookList(ArrayList<Book> bookList);
	
	boolean isCartInBook(String id);

	void insertBook(Book p);
	
	void removeCart(int numId);
	
	void deleteBook();
	
}

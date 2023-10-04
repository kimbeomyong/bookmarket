package com.market.card;

import com.market.bookitem.Book;

public class CartItem {
	private Book itemBook;
	private String bookID;
	private int quantity;
	private int totalPrice;
	
	public CartItem() {}
	
//	public CartItem(String[] itemBook) {
//		this.itemBook = itemBook;
//		this.bookID = itemBook[0];
//		this.quantity = 1;
//		updateTotalPrice();
//	}
//
//	public String[] getItemBook() {
//		return itemBook;
//	}
//
//	public void setItemBook(String[] itemBook) {
//		this.itemBook = itemBook;
//	}

	public CartItem(Book itemBook) {
		this.itemBook = itemBook;
		this.bookID = itemBook.getBookId();
		this.quantity = 1;
		updateTotalPrice();
	}
	
	public Book getItemBook() {
		return itemBook;
	}
	
	public void setItemBook(Book itemBook) {
		this.itemBook = itemBook;
	}
	
	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
		this.updateTotalPrice();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.updateTotalPrice();
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void updateTotalPrice() {
//		totalPrice = Integer.parseInt(this.itemBook[3])* this.quantity;
		totalPrice = this.itemBook.getUnitPrice() * this.quantity;
	}
	
	
}

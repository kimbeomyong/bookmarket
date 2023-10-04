package com.market.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.market.bookitem.Book;
import com.market.card.Cart;
import com.market.exception.CartException;
import com.market.member.Admin;
import com.market.member.User;

public class WelcomeBookMarket {
	static final int NUM_BOOK = 3;
	static final int NUM_ITEM = 8;
	static Cart cart = new Cart();
	static User user;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);// 입력받기
		String userName; // 고객이름
		int userMobile; // 연락처
		int numberSelection; // 번호선택
		// Book[] bookInfoList = new Book[NUM_BOOK];
		//Book[] bookInfoList;
		ArrayList<Book> bookInfoList;
		int totalBookCount = 0;

		System.out.println("Book Marker 고객 정보 입력");
		System.out.print("고객의 이름을 입력하세요 : ");
		userName = input.next();

		System.out.print("연락처를 입력하세요 :");
		userMobile = input.nextInt();

		user = new User(userName, userMobile);

		boolean quit = false;

		while (!quit) {
			menuIntroduction();
			try {
				System.out.println("메뉴 번호를 선택해주세요 :");
				numberSelection = input.nextInt();

				if (numberSelection < 1 || numberSelection > 9) {
					System.out.println("1 부터 9까지의 숫자를 입력하세요.");
				} else {
					switch (numberSelection) {
					case 1:
						// System.out.println("현재 고객 정보");
						menuGuestInfo(userName, userMobile);
						break;
					case 2:
						// System.out.println("2. 장바구니 상품 목록보기:");
						menuCartItemList();
						break;
					case 3:
						// System.out.println("3. 장바구니 비우기");
						menuCartClear();
						break;
					case 4:
						// System.out.println("4. 장바구니에 항목 추가하기 :");
						// menuCartAddItem(bookInfoList);
						totalBookCount = totalFileToBookList();
						//bookInfoList = new Book[totalBookCount];
						bookInfoList = new ArrayList<Book>();
						menuCartAddItem(bookInfoList);
						break;
					case 5:
						// System.out.println("5. 장바구니의 항목 수량 줄이기");
						menuCartRemoveItemCount();
						break;
					case 6:
						// System.out.println("6. 장바구니의 항목 삭제하기");
						menuCartRemoveItem();
						break;
					case 7:
						// System.out.println("7. 영수증 표시하기");
						menuCartBill();
						break;
					case 8:
						// System.out.println("8. 종료");
						menuExit();
						quit = true; // 종료
						break;
					case 9:
						menuAdminLogin();
						break;
					}

				}
			} catch (CartException e) {
				System.out.println(e.getMessage());
				quit = true;
			} catch (Exception e) {
				System.out.println("잘못된 메뉴 선택으로 종료합니다.");
				quit = true;
			}

		}

	}

	public static void menuIntroduction() {

		System.out.println("***********************************************");
		System.out.println("\t\t" + "book Market Menu");

		System.out.println("***********************************************");
		System.out.println("1. 고객 정보 확인하기 \t 4.장바구니에 항목 추가하기");
		System.out.println("2. 장바구니 상품 목록 보기 \t 5. 장바구니에 항목 수량 줄이기");

		System.out.println("3. 장바구니 비우기      \t 6. 장바구니 항목 삭제하기");
		System.out.println("7. 영수증 표시하기      \t 8. 종료");
		System.out.println("9. 관리자 로그인");

		System.out.println("***********************************************");
	}

	public static void menuGuestInfo(String name, int mobile) {
		System.out.println("현재 고객 정보 :");
		System.out.println("이름 : " + user.getName() + ", 연락처 :" + user.getPhone());
	}

	public static void menuCartItemList() {
		if (cart.cartCount >= 0) {
			cart.printCart();
		}
	}

	public static void menuCartClear() throws CartException {
		// System.out.println("장바구니 비우기");
		if (cart.cartCount == 0) {
			// System.out.println("장바구니 항목에 없습니다.");
			throw new CartException("장바구니에 항목이 없습니다.");
		} else {
			System.out.println("장바구니에 모든 항목을 삭제하겟습니까? Y | N");
			Scanner input = new Scanner(System.in);
			String str = input.nextLine();

			if (str.toUpperCase().equals("Y") || str.toUpperCase().equals("y")) {
				System.out.println("장바구니에 모든 항목을 삭제했습니다.");
				cart.deleteBook();
			}
		}

	}

	public static void menuCartAddItem(ArrayList<Book> book) {
		// System.out.println("장바구니에 항목 추가하기 :");

		bookList(book);

		cart.PrintBookList(book);
		boolean quit = false;

		while (!quit) {
			Scanner input = new Scanner(System.in);
			System.out.print("장바구니에 추가할 도서의 ID를 입력하세요 :");
			String inputStr = input.nextLine();

			boolean flag = false;

			int numId = -1;

			for (int i = 0; i < book.size(); i++) {
				if (inputStr.equals(book.get(i).getBookId())) {
					numId = i;
					flag = true;
					break;
				}
			}
			if (flag) {
				System.out.println("장바구니에 추가하겟습니까? Y ㅣ N ");
				inputStr = input.nextLine();

				if (inputStr.toUpperCase().equals("Y") || inputStr.toUpperCase().equals("y")) {
					System.out.println(book.get(numId).getBookId() + "도서가 장바구니에 추가되었습니다.");

					if (!isCartInBook(book.get(numId).getBookId())) {
						// cartItem[cartCount++] = new CartItem(book[numId]);
						cart.insertBook(book.get(numId));
					}
				}
				quit = true;
			} else {
				System.out.println("다시 입력해 주세요");
			}

		}
	}

	public static void menuCartRemoveItemCount() {
		System.out.println("5. 장바구니의 항목 수량 줄이기");
	}

	public static void menuCartRemoveItem() throws CartException {
		// System.out.println("6. 장바구니의 항목 삭제하기");
		if (cart.cartCount == 0) {
			// System.out.println("장바구니에 항목이 없습니다.");
			throw new CartException("장바구니에 항목이 없습니다.");
		} else {
			menuCartItemList();
			boolean quit = false;
			while (!quit) {
				System.out.print("장바구니에서 삭제할 도서의 ID를 입력하세요 :");
				Scanner input = new Scanner(System.in);
				String str = input.nextLine();
				boolean flag = false;
				int numId = -1;

				for (int i = 0; i < cart.cartCount; i++) {
					if (str.equals(cart.cartItem.get(i).getBookID())) {
						numId = i;
						flag = true;
						break;
					}
				}

				if (flag) {
					System.out.println("장바구니의 항목을 삭제하시겟습니까? Y | N");
					str = input.nextLine();
					if (str.toUpperCase().equals("Y") || str.toUpperCase().equals("y")) {
						System.out.println(cart.cartItem.get(numId).getBookID() + "장바구니에서 도서가 삭제되었습니다.");
						cart.removeCart(numId);
					}
					quit = true;
				} else {
					System.out.println("다시 입력해 주세요");
				}
			}
		}
	}

	public static void menuCartBill() throws CartException {
		// System.out.println("7. 영수증 표시하기");
		if (cart.cartCount == 0) {
			// System.out.println("장바구니에 항목이 없습니다.");
			throw new CartException("장바구니에 항목이 없습니다.");
		} else {
			System.out.println("배송받을 분은 고객정보와 같습니까? Y | N");
			Scanner input = new Scanner(System.in);
			String str = input.nextLine();

			if (str.toUpperCase().equals("Y") || str.toUpperCase().equals("y")) {
				System.out.print("배송지를 입력해주세요.");
				String address = input.nextLine();
				printBill(user.getName(), String.valueOf(user.getPhone()), address);
			} else {
				System.out.println("배송받을 고객명을 입력하세요.");
				String name = input.nextLine();
				System.out.println("배송받을 고객의 연락처를 입력하세요.");
				String phone = input.nextLine();
				System.out.println("배송받을 고객의 배송지를 입력해주세요");
				String address = input.nextLine();
				printBill(name, phone, address);
			}
		}
	}

	public static void menuExit() {
		System.out.println("8. 종료");
	}

	public static void bookList(ArrayList<Book> book) {
		setFileToBookList(book);
	}

	public static boolean isCartInBook(String bookId) {
		return cart.isCartInBook(bookId);
	}

	public static void menuAdminLogin() {
		System.out.println("관리자 정보를 입력하세요");

		Scanner input = new Scanner(System.in);
		System.out.print("아이디 : ");
		String adminId = input.next();

		System.out.println("비밀번호 : ");
		String adminPW = input.next();

		Admin admin = new Admin(user.getName(), user.getPhone()); 
		if(adminId.equals(admin.getId())&& adminPW.equals(admin.getPassword())) {
			String[] writeBook = new String[8];
			System.out.println("도서 정보를 추가하겟습니까? Y | N ");
			String str = input.next();
			if(str.toUpperCase().equals("Y")) {
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
				String strDate = formatter.format(date);
				writeBook[0] = "book"+strDate;
				System.out.println("도서ID : " + writeBook[0]);
				String str1 = input.nextLine();
				System.out.println("번호 부분만 입력 예)123-12-1234-01-1");
				System.out.print("ISBN : ");
				writeBook[1] = input.nextLine();
				writeBook[1] = "ISBN " + writeBook[1];
				
				System.out.print("도서명 : ");
				writeBook[2] = input.nextLine();
				System.out.print("가격(숫자) : ");
				writeBook[3] = input.nextLine();
				System.out.print("저자 : ");
				writeBook[4] = input.nextLine();
				System.out.print("설명 : ");
				writeBook[5] = input.nextLine();
				System.out.print("분야 : ");
				writeBook[6] = input.nextLine();
				System.out.print("출판일(예:2023/01/01) : ");
				writeBook[7] = input.nextLine();
				
				try {
					FileWriter fw = new FileWriter("book.txt",true);
					
					for(int i = 0;i<8;i++) {
						fw.write(writeBook[i] + "\n");
					}
					fw.close();
					System.out.println("새도서 정보가 저장되었습니다.");
				}catch(Exception e) {
					System.out.println(e);
				}
			}else {
				System.out.println("이름 : "+ admin.getName() + ",  연락처 : "+ admin.getPhone());
				System.out.println("아이디 : "+ admin.getId() + ",  연락처 : "+ admin.getPassword());
			}
		}else {
			System.out.println("관리자 정보가 일치하지 않습니다."); 
		}
	}
		
		

	public static void printBill(String name, String phone, String address) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);
		System.out.println();
		System.out.println("-----------------배송 받을 고객 정보----------------");
		System.out.println("고객명: " + name + "   \t\t 연락처 : " + phone);
		System.out.println("배송지: " + address + "\t\t 발송일 : " + strDate);
		cart.printCart();
		int sum = 0;
		for (int i = 0; i < cart.cartCount; i++) {
			sum += cart.cartItem.get(i).getTotalPrice();
		}
		System.out.println("\t\t\t주문총 금액 : " + sum + "원\n");
		System.out.println("-----------------------------------------------");
		System.out.println();
	}

	public static int totalFileToBookList() {
		try {
			FileReader fr = new FileReader("book.txt");
			BufferedReader reader = new BufferedReader(fr);

			String str;
			int num = 0;
			while ((str = reader.readLine()) != null) {
				if (str.contains("ISBN")) {
					++num;
				}
			}
			reader.close();
			fr.close();
			return num;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	
	public static void setFileToBookList(ArrayList<Book> bookList) {
		try {
			FileReader fr = new FileReader("book.txt");
			BufferedReader reader = new BufferedReader(fr);

			String bookId;
			String[] readBook = new String[8];
			int count = 0;

			while ((bookId = reader.readLine()) != null) {
				if (bookId.contains("book")) {
					readBook[0] = bookId;
					readBook[1] = reader.readLine();;
					readBook[2] = reader.readLine();
					readBook[3] = reader.readLine();
					readBook[4] = reader.readLine();
					readBook[5] = reader.readLine();
					readBook[6] = reader.readLine();
					readBook[7] = reader.readLine();
				}
//				booklist[count++] = new Book(readBook[0], readBook[1], readBook[2], Integer.parseInt(readBook[3]),
//						readBook[4], readBook[5], readBook[6], readBook[7]);
				Book book =new Book(readBook[0],readBook[1],readBook[2],Integer.parseInt(readBook[3]),readBook[4],readBook[5],readBook[6],readBook[7]);
				bookList.add(book);
			}
			reader.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
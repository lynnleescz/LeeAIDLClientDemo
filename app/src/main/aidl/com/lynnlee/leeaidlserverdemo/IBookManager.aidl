// IBookManager.aidl
package com.lynnlee.leeaidlserverdemo;
import com.lynnlee.leeaidlserverdemo.Book;

interface IBookManager {
    List<Book> getBookList();

    void addBook(in Book book);
}

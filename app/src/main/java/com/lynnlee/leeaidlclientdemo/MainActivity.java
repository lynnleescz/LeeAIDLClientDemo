package com.lynnlee.leeaidlclientdemo;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lynnlee.leeaidlserverdemo.Book;
import com.lynnlee.leeaidlserverdemo.IBookManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lee-Cliend";
    private int mBookId;
    private IBookManager mIBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            mIBookManager = IBookManager.Stub.asInterface(service);
            mBookId = 0;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            mBookId = 0;
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.lynnlee.leeaidlserverdemo.RemoteService");
        intent.setPackage("com.lynnlee.leeaidlserverdemo");
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void addBook(View view) {
        try {
            mIBookManager.addBook(new Book(++mBookId, "Android AIDL " + mBookId));
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBookList(View view) {
        try {
            java.util.List<Book> books = mIBookManager.getBookList();
            Log.e(TAG, "getBookList, book list : " + books.toString());
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }
}


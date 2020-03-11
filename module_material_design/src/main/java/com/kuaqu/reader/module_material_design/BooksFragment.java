package com.kuaqu.reader.module_material_design;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabButton;
    private List<Book> bookList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_fragment, null);
        initView();
        initData();
        return view;
    }

    private void initData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    Book book = new Book("失控", "凯文.凯莉", "最新修订版", "2010-8-10", "20.30", R.mipmap.book1);
                    Book book2 = new Book("黑客与画家", "阮一峰", "最新修订版", "2010-8-10", "20.30", R.mipmap.book2);
                    Book book3 = new Book("七周七语言", "代维", "最新修订版", "2010-8-10", "20.30", R.mipmap.book3);
                    bookList.add(book);
                    bookList.add(book2);
                    bookList.add(book3);
                }
                mProgressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);

    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mAdapter = new MyAdapter(getContext(), bookList);
        mRecyclerView.setAdapter(mAdapter);

        setUpFAB(view);



    }

    private void setUpFAB(View view) {
        mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "底部按钮", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), UCMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Book book = bookList.get(position);
            Intent intent = new Intent(getContext(), BookDetailActivity.class);
            intent.putExtra("book", (Serializable) book);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));
            ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
        }
    };
}

package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.model.Movie;
import com.example.movieapp.view.MovieAdapter;
import com.example.movieapp.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Layout
    private SwipeRefreshLayout mySwipeRefreshLayout;

    // Binding
    private ActivityMainBinding myBinding;

    // View Model
    private MainActivityViewModel myViewModel;

    // Recycler
    private List<Movie> myMovieList;
    private RecyclerView myRecyclerView;
    private MovieAdapter myMovieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );

        // Provides an view model instance associated with a specific life cycle scoop (activity/fragment)
        myViewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);


        getPopularMovies();

        setSwipeToRefresh();
    }

    private void setSwipeToRefresh() {
        mySwipeRefreshLayout = myBinding.swipeLayout;
        mySwipeRefreshLayout.setColorSchemeResources(R.color.black);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });
    }

    private void getPopularMovies() {

        // Use the view model to observe the changes that are fetched using myViewModel.getAllMovies()
        myViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> moviesFromLiveData) {
                myMovieList = (List<Movie>) moviesFromLiveData;
                displayMoviesInRecyclerView();
            }
        });
    }

    private void displayMoviesInRecyclerView() {
        myRecyclerView = myBinding.recyclerview;

        myMovieAdapter = new MovieAdapter(this, myMovieList);

        myRecyclerView.setAdapter(myMovieAdapter);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Notify the adapter
        myMovieAdapter.notifyDataSetChanged();

    }


}
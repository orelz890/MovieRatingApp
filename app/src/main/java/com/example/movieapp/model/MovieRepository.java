package com.example.movieapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.secure.APIKeys;
import com.example.movieapp.serviceapi.MovieApiService;
import com.example.movieapp.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    // used to abstract the data source details and
    // provides a clean API for the ViewModel (presentation) to
    // fetch and manage data

    // Single Source Of Truth Principle - single place in the app
    // responsible for managing and storing the data.

    private List<Movie> movies = new ArrayList<>();

    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<Result> call = movieApiService.getPopularMovies(APIKeys.getMovieApiKey());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();

                if (result != null && result.getResults() != null) {
                    movies = (List<Movie>) result.getResults();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}

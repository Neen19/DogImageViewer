package com.example.javanetwork;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ImageView imageViewDog;
    private TextView nextImageButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();
        viewModel.getDogImageMutableLiveData().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageViewDog);
            }
        });

        viewModel.getIsInternet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean inInternet) {
                if (inInternet) Toast.makeText(MainActivity.this,"error",  Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.INVISIBLE);
            }
        });

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadDogImage();
            }
        });


    }


    private void initViews() {
        imageViewDog = findViewById(R.id.imageViewDog);
        nextImageButton = findViewById(R.id.nextImageButton);
        progressBar = findViewById(R.id.progressBar);
    }


}
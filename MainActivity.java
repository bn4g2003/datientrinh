package com.nguyenvietnhatbang.multiplethread_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nguyenvietnhatbang.multiplethread_ex.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Random random = new Random();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int precent = message.arg1;
            int randNumb = (int) message.obj;
            ImageView imageView = new ImageView(MainActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);


            if(randNumb % 2 == 0) {
                imageView.setImageDrawable(getDrawable(R.drawable.baseline_1x_mobiledata_24));
                binding.containerLayout.addView(imageView);
            }
            else {
                imageView.setImageDrawable(getDrawable(R.drawable.baseline_accessible_forward_24));
                binding.containerLayout2.addView(imageView);
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();
    }
    private void addEvent(){
        binding.btndraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBackground();
            }
        });
    }
    private void drawBackground(){
        int numbOfviews = Integer.parseInt(binding.edtdraw.getText().toString());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1 ; i <= numbOfviews; i++ ){
                    Message message = handler.obtainMessage();
                    message.arg1 = i*100/numbOfviews;
                    message.obj = random.nextInt(100);
                    handler.sendMessage(message);
                    SystemClock.sleep(100);
                }
            }
        });
        thread.start();
    }
}
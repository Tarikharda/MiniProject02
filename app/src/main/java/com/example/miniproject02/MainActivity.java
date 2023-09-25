package com.example.miniproject02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnSave, btn_profile, btnStar1, btnStar2, btnStar3, btnStar4, btnStar5;

    static int counter = 1;
    static boolean saved = false;
    static boolean iStar1 = false, iStar2 = false, iStar3 = false, iStar4 = false, iStar5 = false;
    static String URL;
    TextView tvFullName, tvCity;
    ImageView ivUserProfile;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btn_save);
        btn_profile = findViewById(R.id.btn_profile);
        btnStar1 = findViewById(R.id.btn_star1);
        btnStar2 = findViewById(R.id.btn_star2);
        btnStar3 = findViewById(R.id.btn_star3);
        btnStar4 = findViewById(R.id.btn_star4);
        btnStar5 = findViewById(R.id.btn_star5);
        tvFullName = findViewById(R.id.tv_FullName);
        tvCity = findViewById(R.id.tv_city);
        ivUserProfile = findViewById(R.id.iv_user_profile);
        btnStar1.setOnClickListener(this);
        btnStar2.setOnClickListener(this);
        btnStar3.setOnClickListener(this);
        btnStar4.setOnClickListener(this);
        btnStar5.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        URL = String.format("https://dummyjson.com/users/%d", new Random().nextInt(100));
        getRandomUser();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_star1) {
            if (iStar1) {
                if (!iStar2 && !iStar3 && !iStar4 && !iStar5) {
                    btnStar1.setImageResource(R.drawable.unstar);
                    iStar1 = false;
                }
            } else {
                btnStar1.setImageResource(R.drawable.star);
                iStar1 = true;
            }
        } else if (view.getId() == R.id.btn_star2) {
            if (!iStar2 && iStar1) {
                btnStar2.setImageResource(R.drawable.star);
                iStar2 = true;
            } else {
                if (!iStar3 && !iStar4 && !iStar5) {
                    btnStar2.setImageResource(R.drawable.unstar);
                    iStar2 = false;
                }
            }
        } else if (view.getId() == R.id.btn_star3) {
            if (!iStar3 && iStar2) {
                btnStar3.setImageResource(R.drawable.star);
                iStar3 = true;
            } else {
                if (!iStar4 && !iStar5) {
                    btnStar3.setImageResource(R.drawable.unstar);
                    iStar3 = false;
                }
            }
        } else if (view.getId() == R.id.btn_star4) {
            if (!iStar4 && iStar3) {
                btnStar4.setImageResource(R.drawable.star);
                iStar4 = true;
            } else {
                if (!iStar5) {
                    btnStar4.setImageResource(R.drawable.unstar);
                    iStar4 = false;
                }
            }
        } else if (view.getId() == R.id.btn_star5) {
            if (!iStar5 && iStar4) {
                btnStar5.setImageResource(R.drawable.star);
                iStar5 = true;
            } else {
                btnStar5.setImageResource(R.drawable.unstar);
                iStar5 = false;
            }
        }
        if (view.getId() == R.id.btn_save) {
            if (saved) {
                btnSave.setImageResource(R.drawable.unsave);
                saved = false;
            } else {
                btnSave.setImageResource(R.drawable.save);
                saved = true;
            }
        }
    }

    public void getRandomUser() {
        RequestQueue queue = Volley.newRequestQueue(this);
        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        tvFullName.setText(response.getString("firstName") +" "+ response.getString("lastName"));
                        JSONObject addressObject = response.getJSONObject("address");
                        tvCity.setText("From "+addressObject.getString("city"));
                        String imgUrl = response.getString("image");
                        Picasso.get().load(imgUrl).into(ivUserProfile);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonObjectRequest);
    }
}

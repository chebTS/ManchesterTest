package ua.ck.manchester.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ua.ck.manchester.R;


/**
 * Created by cheb on 3/9/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.txt_register).setOnClickListener(this);
        findViewById(R.id.txt_foreget_pass).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.txt_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.txt_foreget_pass:
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                break;
        }
    }
}

package ua.ck.manchester.activities;

import android.os.Bundle;
import android.view.View;

import ua.ck.manchester.R;


/**
 * Created by cheb on 3/9/16.
 */
public class ForgetPassActivity extends BaseToolbarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findViewById(R.id.btn_restore).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_restore:
                finish();
                break;
        }
    }


    @Override
    protected void setUpViews() {

    }

    @Override
    protected int getTitleResource() {
        return R.string.title_forget;
    }
}

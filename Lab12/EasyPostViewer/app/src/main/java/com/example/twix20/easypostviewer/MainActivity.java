package com.example.twix20.easypostviewer;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.JsonplaceholderService;
import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.JsonplaceholderServiceFactory;
import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    JsonplaceholderService apiService = JsonplaceholderServiceFactory.Create();
    EditText etUsernameOrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsernameOrId = (EditText)findViewById(R.id.editText);
    }

    public void btnGo(View view){
        String usernameOrId = etUsernameOrId.getText().toString().trim();

        fetchUser(usernameOrId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Intent intent = new Intent(getBaseContext(), PostListActivity.class);
                    intent.putExtra("USER_ID", user.getId());
                    startActivity(intent);
                },
                throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("API Error");
                    builder.setMessage("User " + usernameOrId + " doesn't exist!");
                    builder.show();
                });
    }

    public Single<User> fetchUser(String usernameOrId){
        return apiService.getUsers()
                .flatMapIterable(x -> x)
                .filter(u -> u.getUsername().equalsIgnoreCase(usernameOrId) || Integer.toString(u.getId()).equals(usernameOrId))
                .firstOrError();
    }
}
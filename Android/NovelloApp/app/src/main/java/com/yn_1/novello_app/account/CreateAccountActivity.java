package com.yn_1.novello_app.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.volley_requests.VolleyCommand;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for creating an account. Reachable only through the login activity.
 */
public class CreateAccountActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    EditText usernameInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        constraintLayout = findViewById(R.id.constraintLayout);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        passwordConfirmInput = findViewById(R.id.confirmPassword);
        createAccount = findViewById(R.id.createAccount);

        createAccount.setOnClickListener(v -> {
            JsonObjectRequester userAddRequester = new JsonObjectRequester();
            JsonObjectCommand command = new JsonObjectCommand();
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String passwordConfirmation = passwordConfirmInput.getText().toString();
            if (password.equals(passwordConfirmation)) {
                JSONObject userJson = new JSONObject();
                try {
                    userJson.put("username", username);
                    userJson.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                userAddRequester.postRequest("user", userJson, command, null, null);
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Account not created! Passwords do not match!");
                alert.show();
            }
        });

    }

    /**
     * Navigates to the login screen upon successful login or prints an error alert upon failure.
     * @param success true if account created succesfully
     */
    public void accountCreationResult(boolean success) {

        if (success) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Account not created for an unknown reason!");
            alert.show();
        }

    }

    /**
     * Class used to get result of request
     */
    private class JsonObjectCommand implements VolleyCommand<JSONObject> {

        @Override
        public void execute(JSONObject data) {
            accountCreationResult(data != null);
        }

        @Override
        public void onError(VolleyError error) {

        }

    }

}

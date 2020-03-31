package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton;
    private TextView linkRegister;
    private ProgressBar loading;
    SessionManager sessionManager;
    private static String urlLogin="http://192.168.1.101/PHP/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);
        loading=findViewById(R.id.barLoading);
        email=findViewById(R.id.eMail);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.registerButton);
        linkRegister=findViewById(R.id.linkRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();
                String mPassword=password.getText().toString().trim();
                if(!mEmail.isEmpty() || !mPassword.isEmpty()) {
                    Login(mEmail,mPassword);
                }else {
                    email.setError("Please Insert E-mail");
                    password.setError("Please Insert Password");
                }

            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }


    private void Login(final String email, final String password) {
        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, urlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String username =object.getString("Username").trim();
                                    String email = object.getString("Email").trim();

                                    sessionManager.createSession(username, email);
                                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("Username",username);
                                    intent.putExtra("Email",email);
                                    startActivity(intent);

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            loginButton.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Email",email);
                params.put("Password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

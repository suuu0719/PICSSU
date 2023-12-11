package com.example.picssu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.picssu.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private EditText studentNumField;
    private EditText passwordField;
    private Button loginBtn;
    private String studentNum;
    private String password;
    private RetrofitInterface retrofitInterface;
    private SharedPreferences preferences;
    private FirebaseFirestore database;
    private String imgUrl = "profile.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentNumField = binding.loginStudentNum;
        passwordField = binding.password;
        loginBtn = binding.Loginbutton;
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        database = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(view -> {
            studentNum = String.valueOf(studentNumField.getText());
            password = String.valueOf(passwordField.getText());

            if (studentNum.isEmpty() && password.isEmpty()) {
                showToast("학번과 비밀번호를 입력하세요.");
            } else if (studentNum.isEmpty()){
                showToast("학번을 입력하세요.");
            } else if (password.isEmpty()) {
                showToast("비밀번호를 입력하세요.");
            } else {
                loginUser();
            }
        });
    }

    private void loginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setStudentNum(studentNum);
        loginRequest.setPassword(password);

        retrofitInterface.postLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getCode() != null && loginResponse.getCode().equals("올바르지 않은 로그인")) {
                            showToast("학번 또는 비밀번호가 올바르지 않습니다.");
                        } else {
                            handleLoginResponse(loginResponse);
                        }
                    } else {
                        showToast("로그인 응답이 null입니다.");
                    }
                } else {
                    showToast("로그인에 실패했습니다. 네트워크를 확인해주세요.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showToast("로그인에 실패했습니다. 네트워크를 확인해주세요.");
            }
        });
    }

    private void handleLoginResponse(LoginResponse loginResponse) {
        String department = loginResponse.getParentDept();
        String major = loginResponse.getDept();
        String name = loginResponse.getName();

        if (TextUtils.isEmpty(department) || TextUtils.isEmpty(major) || TextUtils.isEmpty(name)) {
            showToast("학번 또는 비밀번호가 올바르지 않습니다.");
        } else {
            Member member = new Member(studentNum, department, name, major);
            ArrayList<String> memberList = new ArrayList<>();

            database.collection("Member")
                    .whereIn("studentNum", Arrays.asList(studentNum))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                if (task.getResult().getDocuments().size() == 0) {
                                    Member member = new Member(studentNum, department, name, major);
                                    Log.d("LoginActivity", "파이어 스토어접근");
                                    database.collection("Member").add(member).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Log.d("LoginActivity", "파이어스토어 저장");
                                            saveUserInfoAndStartActivity(studentNum, department, name, major);
                                        }
                                    });
                                } else {
                                    Log.d("LoginActivity", "파이어 스토어접근");
                                    saveUserInfoAndStartActivity(studentNum, department, name, major);
                                }
                            } else {
                                showToast("파이어스토어 오류");
                            }
                        }
                    });
        }
    }

    private void saveUserInfoAndStartActivity(String studentNum, String department, String name, String major) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("studentNum", studentNum);
        editor.putString("depart", department);
        editor.putString("name", name);
        editor.putString("major", major);
        editor.putString("img", imgUrl);
        // 항상 commit & apply를 해주어야 저장이 된다.
        editor.commit();
        editor.apply();
        Log.d("LoginActivity", preferences.getString("studentNum", ""));
        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}

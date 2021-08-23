package com.example.mealkit.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mealkit.R;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.util.exception.KakaoException;

public class AccountFragment extends Fragment {
    private static final String TAG="사용자";
    private ImageButton btn_kakao, btn_login_out;

    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        btn_kakao = root.findViewById(R.id.kakaolink_btn);
        btn_kakao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().loginWithKakaoTalk(getActivity(),(oAuthToken, error) -> {
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error);
                    } else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                System.out.println("로그인 완료");
                                Session session = Session.getCurrentSession();
                                session.addCallback(sessionCallback);
                                session.open(AuthType.KAKAO_TALK, getActivity());
                            }
                            return null;
                        });
                    }
                    return null;
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
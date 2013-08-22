package zc.twocolor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class appBackActivity extends Activity {
       
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ɾ�����ʱ����
                //requestWindowFeature(Window.FEATURE_NO_TITLE);  //ɾ��������
                setContentView(R.layout.app_back);

                new Handler().postDelayed(new Runnable(){

                 @Override
                 public void run() {
                     Intent mainIntent = new Intent(appBackActivity.this,TwoColor.class);
                     appBackActivity.this.startActivity(mainIntent);
                     appBackActivity.this.finish();
                 }

                }, 5000);

        }
}
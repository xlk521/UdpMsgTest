package com.example.udpmsgtest;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;

public class BaseActivity extends Activity {

	public ProgressDialog progressDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_base);
	}
	//显示loading
	public void showProgress(Context context,int dialogTitle,int dialogMsg ){
		if (progressDialog == null){
			progressDialog = new ProgressDialog(context);
//				progressDialog.setTitle(dialogTitle);
			progressDialog.setTitle(null);
			progressDialog.setMessage(getString(dialogMsg));
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	}
	
	//loading取消
	public void dismessProgress(){
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	//显示loading
	public void showProgress(String loadStr){
		dismessProgress();
		if (progressDialog == null && !BaseActivity.this.isFinishing()){
			progressDialog = new ProgressDialog(BaseActivity.this);
			progressDialog.setMessage(loadStr);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	}
}

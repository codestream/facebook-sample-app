package com.example.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.app.model.FacebookUser;
import com.facebook.Request;
import com.facebook.Session;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class FriendDetailsActivity extends Activity{
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_detail);
		listView = (ListView) findViewById(R.id.list_friend_details);
		new GetFriendsDetailsTask(this).execute();
	}
	
	private class GetFriendsDetailsTask extends AsyncTask<Void, Integer, ArrayList<FacebookUser>> {
		private Context context;
		private ProgressDialog dialog;
		
		public GetFriendsDetailsTask(Context context){
			this.context = context;
			this.dialog = new ProgressDialog(context);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Loading user data...");
			dialog.setIndeterminate(true);
			dialog.show();
		}
		
		@Override
		protected ArrayList<FacebookUser> doInBackground(Void... params) {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			String userId = extras.getString("uid");
			Bitmap bitmap = null;
			List<FacebookUser> list = new ArrayList<FacebookUser>();
			try {
				URL url = new URL("http://graph.facebook.com/"+ userId +"/picture?type=large");
				try {
					bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					String userName = extras.getString("fname");
					String birthDate = extras.getString("fbdate");
					String location = extras.getString("floc");
					FacebookUser facebookUser = new FacebookUser();
					facebookUser.setUserPhoto(bitmap);
					facebookUser.setUserName(userName);
					facebookUser.setUserBirthDay(birthDate);
					facebookUser.setLocation(location);
					list.add(facebookUser);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return (ArrayList<FacebookUser>) list;
		}
		
		@Override
		protected void onPostExecute(ArrayList<FacebookUser> result) {
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			FriendDetailsDataAdapter adapter = new FriendDetailsDataAdapter(context, result);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}
}

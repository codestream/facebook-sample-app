package com.example.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.model.FacebookUser;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphLocation;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class HomeActivity extends Activity implements Session.StatusCallback{
	private ListView userProfileData;
	LoginButton button;
	Button friendsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (LoginButton) findViewById(R.id.btn_login);
		userProfileData = (ListView) findViewById(R.id.user_profile_data);
		friendsButton = (Button) findViewById(R.id.btn_get_friends);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Session currentSession = Session.getActiveSession();
				if (currentSession == null || currentSession.getState().isClosed()){
					Session session = new Session.Builder(getBaseContext()).build();
					Session.setActiveSession(session);
					currentSession = session;
				}

				if (currentSession.isOpened()) {
					// Do whatever u want. User has logged in

				} else if (!currentSession.isOpened()) {
					// Ask for username and password
					OpenRequest op = new Session.OpenRequest(HomeActivity.this);

					op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
					op.setCallback(null);

					List<String> permissions = new ArrayList<String>();
					permissions.add("email");
					permissions.add("user_birthday");
					permissions.add("user_location");
					permissions.add("friends_about_me");
					permissions.add("friends_birthday");
					permissions.add("friends_hometown");
					permissions.add("friends_location");
					op.setPermissions(permissions);

					Session session = new Session.Builder(HomeActivity.this).build();
					Session.setActiveSession(session);
					session.openForPublish(op);
				}
			}
		});
	}
	
	

	@Override
	public void call(Session session, SessionState state, Exception exception) {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Session.getActiveSession() != null)
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);

		Session currentSession = Session.getActiveSession();
		if (currentSession == null || currentSession.getState().isClosed()) {
			Session session = new Session.Builder(getApplicationContext()).build();
			Session.setActiveSession(session);
			currentSession = session;
		}

		if (currentSession.isOpened()) {
			Session.openActiveSession(this, true, new Session.StatusCallback() {

				@SuppressWarnings("deprecation")
				@Override
				public void call(final Session session, SessionState state,
						Exception exception) {

					if (session.isOpened()) {

						Request.executeMeRequestAsync(session,
								new Request.GraphUserCallback() {

							@Override
							public void onCompleted(GraphUser user,
									Response response) {
								if (user != null) {
									button.setVisibility(View.GONE);
									userProfileData.setVisibility(View.VISIBLE);
									new GetUserDataTask(HomeActivity.this, user).execute();
								}
							}
						});
					}
				}
			});
		}
	}
	
	private class GetUserDataTask extends AsyncTask<Void, Integer, ArrayList<FacebookUser>> {
		private Context context;
		private ProgressDialog progressDialog;
		private GraphUser graphUser;

		public GetUserDataTask(Context context, GraphUser user){
			this.context = context;
			this.progressDialog = new ProgressDialog(context);
			this.graphUser = user;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Loading user data...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected ArrayList<FacebookUser> doInBackground(Void... params) {
			Bitmap bitmap = null;
			List<FacebookUser> list = new ArrayList<FacebookUser>();
			try {
				URL url = new URL("http://graph.facebook.com/"+ graphUser.getId() +"/picture?type=large");
				try {
					bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					String username = graphUser.getFirstName(); 
					String userLastname = graphUser.getLastName();
					String userEmail = graphUser.asMap().get("email").toString();
					String birthDay = graphUser.getBirthday();
					String location = graphUser.getLocation().getProperty("name").toString();
					StringBuilder builder = new StringBuilder();
					builder.append(username).append(" ").append(userLastname);
					FacebookUser facebookUser = new FacebookUser();
					facebookUser.setUserPhoto(bitmap);
					facebookUser.setUserName(builder.toString());
					facebookUser.setUserEmail(userEmail);
					facebookUser.setUserBirthDay(birthDay);
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
			if(progressDialog.isShowing()){
				progressDialog.dismiss();
			}
			UserDataAdapter adapter = new UserDataAdapter(context, result);
			userProfileData.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}
}
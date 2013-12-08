package com.example.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.app.model.FacebookUser;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class FriendsActivity extends Activity{
	private ListView friendsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		friendsList = (ListView) findViewById(R.id.list_friends);
		getFriends();
	}
	
	private void getFriends(){
		Session activeSession = Session.getActiveSession();
		if(activeSession.getState().isOpened()){
	        Request friendRequest = Request.newMyFriendsRequest(activeSession, 
	            new GraphUserListCallback(){
	                @Override
	                public void onCompleted(List<GraphUser> users,
	                        Response response) {
	                	Log.i("INFO", response.toString());
	                	List<FacebookUser> facebookUsers = new ArrayList<FacebookUser>();
	                	Bitmap bitmap = null;
	                    for(GraphUser user : users){
	                    	FacebookUser facebookUser = new FacebookUser();
	                    	try {
								URL url = new URL("http://graph.facebook.com/"+ user.getId() +"/picture?type=large");
								try {
									bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
									String firstName = user.getName();
									String birthDay = user.getBirthday();
									String location = user.getLocation().getProperty("name").toString();
									facebookUser.setId(user.getId());
									facebookUser.setUserPhoto(bitmap);
									facebookUser.setUserName(firstName);
									facebookUser.setLocation(location);
									facebookUser.setUserBirthDay(birthDay);
									facebookUsers.add(facebookUser);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
	                    }
	                    FriendsListAdapter adapter = new FriendsListAdapter(FriendsActivity.this, (ArrayList<FacebookUser>) facebookUsers);
	                    friendsList.setAdapter(adapter);
	                    adapter.notifyDataSetChanged();
	                }
	        });
	        final Bundle params = new Bundle();
	        params.putString("fields", "id, name, picture, birthday, location");
	        friendRequest.setParameters(params);
	        friendRequest.executeAsync();
	        friendsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int argument,
						long id) {
					final TextView friendId = (TextView) view.findViewById(R.id.friend_id);
					final TextView friendName = (TextView) view.findViewById(R.id.text_friend_credentials);
					final TextView friendLocation = (TextView) view.findViewById(R.id.friend_location);
					final TextView friendBirthDay = (TextView) view.findViewById(R.id.friend_birthday);
					Intent intent = new Intent(FriendsActivity.this, FriendDetailsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("uid", friendId.getText().toString());
					bundle.putString("fname", friendName.getText().toString());
					bundle.putString("floc", friendLocation.getText().toString());
					bundle.putString("fbdate", friendBirthDay.getText().toString());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
	    }
	}
}

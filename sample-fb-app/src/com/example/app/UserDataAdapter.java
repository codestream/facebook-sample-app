package com.example.app;

import java.util.ArrayList;
import java.util.List;

import com.example.app.model.FacebookUser;
import com.facebook.model.GraphLocation;
import com.facebook.widget.LoginButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserDataAdapter extends ArrayAdapter {
	private Context context;
	private ArrayList<FacebookUser> objects;

	public UserDataAdapter(Context context, ArrayList<FacebookUser> objects) {
		super(context, R.layout.user_profile_items, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder viewHolder;
		if(view == null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.user_profile_items, parent, false);
			viewHolder.userPhoto = (ImageView) view.findViewById(R.id.image_user_photo);
			viewHolder.userName = (TextView) view.findViewById(R.id.text_user_name);
			viewHolder.email = (TextView) view.findViewById(R.id.text_self_email);
			viewHolder.birthDate = (TextView) view.findViewById(R.id.text_birth_date);
			viewHolder.userLocation = (TextView) view.findViewById(R.id.text_user_location);
			viewHolder.logoutButton = (LoginButton) view.findViewById(R.id.btn_logout);
			viewHolder.friendsButton = (Button) view.findViewById(R.id.btn_get_friends);
			
			Bitmap userPhoto = objects.get(position).getUserPhoto();
			String username = objects.get(position).getUserName();
			String userEmail = objects.get(position).getUserEmail();
			String userBirthDay = objects.get(position).getUserBirthDay();
			String location = objects.get(position).getLocation();
			viewHolder.userPhoto.setImageBitmap(userPhoto);
			viewHolder.userName.setText(username);
			viewHolder.email.setText(userEmail);
			viewHolder.birthDate.setText(userBirthDay);
			viewHolder.userLocation.setText(location);
			
			viewHolder.friendsButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(), FriendsActivity.class);
					getContext().startActivity(intent);
				}
			});
		}
		return view;
	}
	
	private class ViewHolder {
		ImageView userPhoto;
		TextView userName;
		TextView email;
		TextView birthDate;
		TextView userLocation;
		Button logoutButton;
		Button friendsButton;
	}
}

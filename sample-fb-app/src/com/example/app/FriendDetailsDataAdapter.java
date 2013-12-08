package com.example.app;

import java.util.ArrayList;
import java.util.List;

import com.example.app.model.FacebookUser;
import com.facebook.widget.LoginButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendDetailsDataAdapter extends ArrayAdapter {
	private Context context;
	private ArrayList<FacebookUser> objects;

	public FriendDetailsDataAdapter(Context context,
			ArrayList<FacebookUser> objects) {
		super(context, R.layout.friends_details_items, objects);
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
			view = inflater.inflate(R.layout.friends_details_items, parent, false);
			viewHolder.userPhoto = (ImageView) view.findViewById(R.id.image_friend_photo_detail);
			viewHolder.userName = (TextView) view.findViewById(R.id.text_friend_credentials_detail);
			viewHolder.birthDate = (TextView) view.findViewById(R.id.friend_birthday_detail);
			viewHolder.userLocation = (TextView) view.findViewById(R.id.friend_location_detail);
			
			Bitmap userPhoto = objects.get(position).getUserPhoto();
			String username = objects.get(position).getUserName();
			String userBirthDay = objects.get(position).getUserBirthDay();
			String location = objects.get(position).getLocation();
			viewHolder.userPhoto.setImageBitmap(userPhoto);
			viewHolder.userName.setText(username);
			viewHolder.birthDate.setText(userBirthDay);
			viewHolder.userLocation.setText(location);
		}
		
		return view;
	}
	
	private class ViewHolder {
		ImageView userPhoto;
		TextView userName;
		TextView birthDate;
		TextView userLocation;
	}
}

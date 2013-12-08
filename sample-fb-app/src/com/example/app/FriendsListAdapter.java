package com.example.app;

import java.util.ArrayList;
import java.util.List;

import com.example.app.model.FacebookFriend;
import com.example.app.model.FacebookUser;
import com.facebook.model.GraphUser;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsListAdapter extends ArrayAdapter{
	private Context context;
	private ArrayList<FacebookUser> facebookFriends;

	public FriendsListAdapter(Context context, ArrayList<FacebookUser> objects) {
		super(context, R.layout.friends_list_items, objects);
		this.context = context;
		this.facebookFriends = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.friends_list_items, parent, false);
			holder.friendPhoto = (ImageView) view.findViewById(R.id.image_friend_photo);
			holder.friendName = (TextView) view.findViewById(R.id.text_friend_credentials);
			holder.friendId = (TextView) view.findViewById(R.id.friend_id);
			holder.friendLocation = (TextView) view.findViewById(R.id.friend_location);
			holder.friendBirthDay = (TextView) view.findViewById(R.id.friend_birthday);
			
			Bitmap photo = facebookFriends.get(position).getUserPhoto();
			String friendFirstName = facebookFriends.get(position).getUserName();
			String friendId = facebookFriends.get(position).getId();
			String friendLocation = facebookFriends.get(position).getLocation();
			String friendBirthDay = facebookFriends.get(position).getUserBirthDay();
			
			holder.friendPhoto.setImageBitmap(photo);
			holder.friendName.setText(friendFirstName);
			holder.friendId.setText(friendId);
			holder.friendLocation.setText(friendLocation);
			holder.friendBirthDay.setText(friendBirthDay);
			
			
		}
		return view;
	}
	
	private class ViewHolder {
		ImageView friendPhoto;
		TextView friendName;
		TextView friendId;
		TextView friendLocation;
		TextView friendBirthDay;
	}
}

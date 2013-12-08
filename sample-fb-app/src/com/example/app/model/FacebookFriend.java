package com.example.app.model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class FacebookFriend implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Bitmap friendPhoto;
	private String friendName;
	private String friendLastName;
	
	public FacebookFriend(){}

	public Bitmap getFriendPhoto() {
		return friendPhoto;
	}

	public void setFriendPhoto(Bitmap friendPhoto) {
		this.friendPhoto = friendPhoto;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendLastName() {
		return friendLastName;
	}

	public void setFriendLastName(String friendLastName) {
		this.friendLastName = friendLastName;
	}
}

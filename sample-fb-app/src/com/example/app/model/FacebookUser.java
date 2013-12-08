package com.example.app.model;

import java.io.Serializable;

import com.facebook.model.GraphLocation;

import android.graphics.Bitmap;

public class FacebookUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;

	private Bitmap userPhoto;
	private String userName;
	private String userLastName;
	private String userEmail;
	private String userBirthDay;
	private String location;
	
	public FacebookUser(){}

	public Bitmap getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(Bitmap userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserBirthDay() {
		return userBirthDay;
	}

	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((userBirthDay == null) ? 0 : userBirthDay.hashCode());
		result = prime * result
				+ ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result
				+ ((userLastName == null) ? 0 : userLastName.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userPhoto == null) ? 0 : userPhoto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FacebookUser)) {
			return false;
		}
		FacebookUser other = (FacebookUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (userBirthDay == null) {
			if (other.userBirthDay != null) {
				return false;
			}
		} else if (!userBirthDay.equals(other.userBirthDay)) {
			return false;
		}
		if (userEmail == null) {
			if (other.userEmail != null) {
				return false;
			}
		} else if (!userEmail.equals(other.userEmail)) {
			return false;
		}
		if (userLastName == null) {
			if (other.userLastName != null) {
				return false;
			}
		} else if (!userLastName.equals(other.userLastName)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		if (userPhoto == null) {
			if (other.userPhoto != null) {
				return false;
			}
		} else if (!userPhoto.equals(other.userPhoto)) {
			return false;
		}
		return true;
	}
}

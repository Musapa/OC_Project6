package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.domain.User;

public class UserSelectDto extends User {

	private boolean selected = false;

	public UserSelectDto() {
	}

	public UserSelectDto(User user, boolean selected) {
		super(user);
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

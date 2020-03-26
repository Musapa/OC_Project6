package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.domain.User;

public class UserSelectDto extends User {

	private boolean selected = false;

	public UserSelectDto() {
	}

	public UserSelectDto(User user) {
		super(user);
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

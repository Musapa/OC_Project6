package com.openclassrooms.paymybuddy.dto;

import java.util.ArrayList;
import java.util.List;

public class ConnectionDto {

	private List<UserSelectDto> users = new ArrayList<>();
	
    public void addUser(UserSelectDto user) {
        this.users.add(user);
    }

	public List<UserSelectDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserSelectDto> users) {
		this.users = users;
	}
 
}

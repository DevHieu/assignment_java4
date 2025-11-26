package com.asm.dao;

import com.asm.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    boolean checkUsernameExist(String username);

    boolean checkEmailExist(String email);

}
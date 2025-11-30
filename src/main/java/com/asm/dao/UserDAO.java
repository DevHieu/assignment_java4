package com.asm.dao;

import com.asm.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    boolean checkUsernameExist(String username);

    boolean checkEmailExist(String email);

    List<User> searchByKeyword(String keyword);
  
    List<User> findByRole(boolean admin);
  
    List<User> findPage(int page, int size);
}


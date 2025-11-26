package com.asm.dao;

import java.util.List;

import com.asm.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    // Tìm kiếm theo từ khóa
    List<User> searchByKeyword(String keyword);
    List<User> findByRole(boolean admin);
    List<User> findPage(int page, int size);
}

package com.asm.dao;

import java.util.List;

public interface CrudDAO<T, ID> {
  /** Truy vấn tất cả */
  List<T> findAll();

  /** Truy vấn theo mã */
  T findById(ID id);

  /** Thêm mới */
  void create(T item);

  /** Cập nhật */
  void update(T item);

  /** Xóa theo mã */
  void deleteById(ID id);
}

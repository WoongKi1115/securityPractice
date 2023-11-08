package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 jpaRepository가 들고 있음
// @Repository 어노테이션이 없어도 Ioc가 됨. 이유는 JpaRepository를 상속 받았기 때문.
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}

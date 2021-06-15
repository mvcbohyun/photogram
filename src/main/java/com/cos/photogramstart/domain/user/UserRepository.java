package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//어노테이션이 없어도 ioc 등록이 자동으로 된다.JpaRepository

public interface UserRepository extends JpaRepository<User, Integer>{

	// JPA query method
	User findByUsername(String username);

	User findById(Long id);
}




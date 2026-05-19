package com.angela.stardewvalley.user.repo;

import com.angela.stardewvalley.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Long> {
    @Modifying
    @Query( value = "insert into tb_user(username,password) values(?1,?2)",nativeQuery = true)
    int register(String username, String password);

    User findByUsernameAndPassword(String username, String password);
}

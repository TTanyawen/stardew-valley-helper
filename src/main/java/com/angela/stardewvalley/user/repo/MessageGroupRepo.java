package com.angela.stardewvalley.user.repo;

import com.angela.stardewvalley.user.entity.MessageGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageGroupRepo extends JpaRepository<MessageGroup,Long> {
    @Query(value = "select * from tb_message_group where user_id=?1 order by id asc",nativeQuery = true)
    List<MessageGroup> findAllMessageGroupByUserId(Long userId);
}

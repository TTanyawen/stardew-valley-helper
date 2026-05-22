package com.angela.stardewvalley.user.repo;

import com.angela.stardewvalley.user.entity.MessageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageDetailRepo extends JpaRepository<MessageDetail,Long> {
    @Query(value = "select * from tb_message_detail where message_group_id = ?1 order by id asc",nativeQuery = true)
    List<MessageDetail> findAllMessgaeDetailByMessageId(Long messageGroupId);
}

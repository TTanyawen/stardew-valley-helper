package com.angela.stardewvalley.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_message_detail")
public class MessageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message_group_id")
    private Long messageGroupId;
    @Column(name = "role")
    private String role;
    @Column(name = "content")
    private String content;
}

package com.example.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class TimeEntity {

    @CreationTimestamp
    @Column(updatable = false) // 업데이트 일때 DB에 저장 하지않음
    private Timestamp createdTime;
    @UpdateTimestamp
    @Column(insertable = false) // 저장될 때 DB에 저장하지 않음
    private Timestamp updatedTime;
}

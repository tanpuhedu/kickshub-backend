package com.tanpuh.kickshub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class InvalidatedToken {
    @Id
    private String id;

    // lưu trữ thời gian hết hạn để có thể xóa sau 1 tgian nhất định -> cải thiện chi phí lưu trữ
    private Date expTime;
}

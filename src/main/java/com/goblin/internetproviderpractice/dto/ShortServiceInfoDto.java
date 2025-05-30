package com.goblin.internetproviderpractice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortServiceInfoDto {
    @Id
    private int serviceId;

    private String serviceTitle;

    private String createdBy;
}

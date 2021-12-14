package com.rats.scope.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @ManyToMany(targetEntity = TaskEntity.class)
  @JoinTable(name = "users_task",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
  private List<TaskEntity> taskEntityList = new ArrayList<>();

  private String nickname;
  private String password;
  private String email;
  private OffsetDateTime creationDate;
  private OffsetDateTime updateDate;

}
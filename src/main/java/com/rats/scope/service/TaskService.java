package com.rats.scope.service;

import com.rats.scope.entity.TaskEntity;
import com.rats.scope.entity.TaskStatus;
import com.rats.scope.entity.UserEntity;
import com.rats.scope.exception.RequestException;
import com.rats.scope.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskEntity save(TaskEntity task, UserEntity user) {
    task.setStatus(TaskStatus.ACTIVE);
    task.setCreationDate(OffsetDateTime.now());
    task.setCustomer(user.getNickname());
    return taskRepository.save(task);
  }

  public void startTask(UserEntity user, Long id) {
    TaskEntity taskEntity = taskRepository.findById(id)
            .orElseThrow(() -> new RequestException("Не существует записи с ID = " + id));
    taskEntity.setExecutor(user.getNickname());
    taskEntity.setStatus(TaskStatus.IN_PROGRESS);
    taskRepository.saveAndFlush(taskEntity);
  }

  public List<TaskEntity> getMyTasksOfUser(UserEntity user) {
    return taskRepository.findByUserId(user.getId()).stream()
            .filter(TaskEntity -> TaskEntity.getStatus().equals(TaskStatus.ACTIVE))
            .collect(Collectors.toList());
  }

  public List<TaskEntity> getInProgressTasksOfUser(UserEntity user) {
    return new ArrayList<>(taskRepository.findByExecutor(user.getNickname()));
  }

  public List<TaskEntity> getAllTasks() {
    return new ArrayList<>(taskRepository.findAll());
  }

  public void changeStatus(Long id,TaskStatus status) {
    TaskEntity taskEntity = taskRepository.findById(id)
            .orElseThrow(() -> new RequestException("Не существует записи с ID = " + id));
    taskEntity.setStatus(status);
    taskEntity.setUpdateDate(OffsetDateTime.now());
    taskRepository.saveAndFlush(taskEntity);
  }

}

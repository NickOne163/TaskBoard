package com.rats.scope.controller;

import com.rats.scope.entity.TaskEntity;
import com.rats.scope.entity.UserEntity;
import com.rats.scope.entity.dto.TaskDto;
import com.rats.scope.service.TaskService;
import com.rats.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/task-board")
@RequiredArgsConstructor
public class TasksBoardController {

  private final UserService userService;

  private final TaskService taskService;

  private final MapperFacade mapperFacade;

  @RequestMapping("/start")
  public String start(Model model, @PathParam("id") Long id, @CookieValue(name = "authUser") String authUser) {
    model.addAttribute("currentUser", authUser);
    UserEntity user = userService.findByNickname(authUser);
    taskService.startTask(user,id);
    List<TaskEntity> tasksOfUser = taskService.getInProgressTasksOfUser(user);
    model.addAttribute("tasks", mapperFacade.mapAsList(tasksOfUser, TaskDto.class));
    return "in-progress-tasks";
  }

}


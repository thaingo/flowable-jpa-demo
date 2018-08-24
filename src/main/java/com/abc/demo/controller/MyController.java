package com.abc.demo.controller;

import com.abc.demo.service.MyService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MyController {

  @Autowired private MyService myService;

  @RequestMapping(
      value = "/process",
      method = RequestMethod.POST,
      produces = APPLICATION_JSON_VALUE)
  public void startProcessInstance(@RequestBody ProcessDTO processDTO) {
    myService.startProcess(processDTO.getAssignee());
  }

  @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
  public List<TaskDTO> getTasks(@RequestParam String assignee) {
    List<Task> tasks = myService.getTasks(assignee);
    List<TaskDTO> dtos = new ArrayList<>();
    for (Task task : tasks) {
      dtos.add(new TaskDTO(task.getId(), task.getName()));
    }

    return dtos;
  }

  @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.PUT)
  public void completeTask(@PathVariable String taskId) {
    myService.completeTask(taskId);
  }

  static class TaskDTO {
    private String id;
    private String name;

    public TaskDTO(String id, String name) {
      this.id = id;
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  static class ProcessDTO {
    private String assignee;

    public ProcessDTO() {
    }

    public String getAssignee() {
      return assignee;
    }

    public void setAssignee(String assignee) {
      this.assignee = assignee;
    }
  }
}


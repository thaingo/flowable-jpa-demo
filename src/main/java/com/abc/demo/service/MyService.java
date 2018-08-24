package com.abc.demo.service;

import com.abc.demo.jpa.model.Person;
import com.abc.demo.jpa.repository.PersonRepository;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyService {

  @Autowired private RuntimeService runtimeService;
  @Autowired private TaskService taskService;
  @Autowired private PersonRepository personRepository;

  public void startProcess(String assignee) {
    Person person = personRepository.findByUserName(assignee);
    Map<String, Object> processVariables = new HashMap<>();
    processVariables.put("person", person);

    runtimeService.startProcessInstanceByKey("oneTaskProcess", processVariables);
  }

  public List<Task> getTasks(String assignee) {
    return taskService.createTaskQuery().taskAssignee(assignee).list();
  }

  public void completeTask(String taskId) {
    taskService.complete(taskId);
  }

  public void createDemoUsers() {
    if (personRepository.findAll().size() == 0) {
      personRepository.save(new Person("John", "John", "Ngo", new Date()));
      personRepository.save(new Person("Lucas", "Lucas", "Nguyen", new Date()));
    }
  }
}


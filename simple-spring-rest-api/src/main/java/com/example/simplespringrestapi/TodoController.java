package com.example.simplespringrestapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

  // get todos
  @RequestMapping(method = RequestMethod.GET, path = "/todos")
  public String getTodoItems() {
    return "get todo items";
  }

  // get todo
  @RequestMapping(method = RequestMethod.GET, path = "/todos/{id}")
  public String getTodoItem() {
    return "get todo items";
  }

  // create todo
  @RequestMapping(method = RequestMethod.POST, path = "/todos")
  public String createTodoItems() {
    return "create todo item";
  }

  // update todo
  @RequestMapping(method = RequestMethod.PUT, path = "/todos/{id}")
  public String putTodoItems() {
    return "put todo item";
  }

  // delete todo
  @RequestMapping(method = RequestMethod.DELETE, path = "/todos/{id}")
  public String deleteTodoItems() {
    return "delete todo item";
  }


  
}

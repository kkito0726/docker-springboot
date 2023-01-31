package com.example.simplespringrestapi.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplespringrestapi.models.TodoItem;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
public class TodoController {

  // すべてのtodoをためておくリスト
  private final ArrayList<TodoItem> _todoItems = new ArrayList<>() {
    {
      add(new TodoItem(1, "todo 1"));
      add(new TodoItem(2, "todo 2"));
      add(new TodoItem(3, "todo 3"));
      add(new TodoItem(4, "todo 4"));
    }
  };

  @RequestMapping(method = RequestMethod.GET, path = "/")
  public String root () {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
      .build();
    client.sendAsync(request, BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println)
      .join();
    return client.toString();
  }

  // get todos
  @RequestMapping(method = RequestMethod.GET, path = "/todos")
  public ArrayList<TodoItem> getTodoItems() {

    return _todoItems;
  }

  // get todo
  @RequestMapping(method = RequestMethod.GET, path = "/todos/{id}")
  public TodoItem getTodoItem(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);
    return found;
  }

  // create todo
  @RequestMapping(method = RequestMethod.POST, path = "/todos")
  public TodoItem createTodoItems(@RequestBody TodoItem todoItem) {
    todoItem.setId(4);
    _todoItems.add(todoItem);
    return todoItem;
  }

  // update todo
  @RequestMapping(method = RequestMethod.PUT, path = "/todos/{id}")
  public TodoItem putTodoItems(@PathVariable int id, @RequestBody TodoItem todoItem) {
    TodoItem found = getTodoItemById(id);

    _todoItems.remove(found);
    _todoItems.add(todoItem);

    return todoItem;
  }

  // delete todo
  @RequestMapping(method = RequestMethod.DELETE, path = "/todos/{id}")
  public String deleteTodoItems(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);

    _todoItems.remove(found);

    return "delete todo item" + "id=" + id;
  }

  private TodoItem getTodoItemById(int id) {
    return _todoItems.stream().filter(item -> item.getId() == id).findAny().orElse(null);
  }
  
}

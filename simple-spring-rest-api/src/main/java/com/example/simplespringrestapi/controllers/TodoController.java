package com.example.simplespringrestapi.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simplespringrestapi.models.TodoItem;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping(path = TodoController.BASE_URL)
public class TodoController {
  public static final String BASE_URL = "/api/v1/todos";
  private final AtomicInteger _counter = new AtomicInteger();

  // すべてのtodoをためておくリスト
  private final ArrayList<TodoItem> _todoItems = new ArrayList<>() {
    {
      add(new TodoItem(_counter.incrementAndGet(), "todo 1"));
      add(new TodoItem(_counter.incrementAndGet(), "todo 2"));
      add(new TodoItem(_counter.incrementAndGet(), "todo 3"));
      add(new TodoItem(_counter.incrementAndGet(), "todo 4"));
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
  @RequestMapping(method = RequestMethod.GET, path = "")
  public ArrayList<TodoItem> getTodoItems() {

    return _todoItems;
  }

  // get todo
  @RequestMapping(method = RequestMethod.GET, path = "/{id}")
  public TodoItem getTodoItem(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);
    if (found == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }
    return found;
  }

  // create todo
  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> createTodoItems(@RequestBody TodoItem todoItem) {
    todoItem.setId(_counter.incrementAndGet());
    _todoItems.add(todoItem);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoItem.getId()).toUri();
    
    return ResponseEntity.created(location).body(todoItem);
  }

  // update todo
  @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
  public ResponseEntity<?> updateTodoItems(@PathVariable int id, @RequestBody TodoItem todoItem) {
    TodoItem found = getTodoItemById(id);

    if (found == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    _todoItems.remove(found);
    _todoItems.add(todoItem);

    return ResponseEntity.noContent().build();
  }

  // delete todo
  @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
  public ResponseEntity<?> deleteTodoItems(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);

    _todoItems.remove(found);

    if (found == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    return ResponseEntity.noContent().build();
  }

  private TodoItem getTodoItemById(int id) {
    return _todoItems.stream().filter(item -> item.getId() == id).findAny().orElse(null);
  }
  
}

package com.example.simplespringrestapi.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  @GetMapping(path = "")
  public ResponseEntity<ArrayList<TodoItem>> getTodoItems() {

    return ResponseEntity.ok(_todoItems);
  }

  // get todo
  @GetMapping(path = "/{id}")
  public ResponseEntity<TodoItem> getTodoItem(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);

    return ResponseEntity.ok(found);
  }

  // create todo
  @PostMapping(path = "")
  public ResponseEntity<TodoItem> createTodoItems(@RequestBody TodoItem newTodoItem) {
    if (Objects.isNull(newTodoItem)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todo item must not be null");
    }
    newTodoItem.setId(_counter.incrementAndGet());
    _todoItems.add(newTodoItem);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodoItem.getId()).toUri();
    
    return ResponseEntity.created(location).body(newTodoItem);
  }

  // update todo
  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateTodoItems(@PathVariable int id, @RequestBody TodoItem todoItem) {
    TodoItem found = getTodoItemById(id);

    _todoItems.remove(found);
    _todoItems.add(todoItem);

    return ResponseEntity.noContent().build();
  }

  // delete todo
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> deleteTodoItems(@PathVariable int id) {
    TodoItem found = getTodoItemById(id);

    _todoItems.remove(found);

    return ResponseEntity.noContent().build();
  }

  private TodoItem getTodoItemById(int id) {
    Optional<TodoItem> found =  _todoItems.stream().filter(item -> item.getId() == id).findAny();

    if (!found.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    return found.get();
  }
  
}

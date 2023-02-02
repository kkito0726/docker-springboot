package com.example.simplespringrestapi.controllers;

import java.net.URI;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simplespringrestapi.models.TodoItem;
import com.example.simplespringrestapi.services.TodoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = TodoController.BASE_URL)
public class TodoController {
  public static final String BASE_URL = "/api/v1/todos";
  @Autowired
  private TodoService _todoService;

  // get todos
  @GetMapping(path = "")
  public ResponseEntity<ArrayList<TodoItem>> getTodoItems() {
    ArrayList<TodoItem> todoItems = _todoService.getTodoItems();
    return ResponseEntity.ok(todoItems);
  }

  // get todo
  @GetMapping(path = "/{id}")
  public ResponseEntity<TodoItem> getTodoItem(@PathVariable int id) {
    TodoItem todoItem = _todoService.getTodoItemById(id);

    return ResponseEntity.ok(todoItem);
  }

  // create todo
  @PostMapping(path = "")
  public ResponseEntity<TodoItem> createTodoItem(@Valid @RequestBody TodoItem newTodoItem) {
      TodoItem savedTodoItem = _todoService.saveTodoItem(newTodoItem);
      
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTodoItem.getId()).toUri();
      return ResponseEntity.created(location).body(savedTodoItem);
  }

  // update todo
  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateTodoItems(@PathVariable int id, @RequestBody TodoItem todoItem) {
    _todoService.updateTodoItem(todoItem, id);

    return ResponseEntity.noContent().build();
  }

  // delete todo
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> deleteTodoItems(@PathVariable int id) {
    _todoService.deleteTodoItems(id);

    return ResponseEntity.noContent().build();
  }
}

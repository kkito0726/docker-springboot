package com.example.simplespringrestapi.services;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.simplespringrestapi.errors.BadRequestException;
import com.example.simplespringrestapi.errors.NotFoundException;
import com.example.simplespringrestapi.models.TodoItem;

@Service
public class TodoServiceImpl implements TodoService{
  private final AtomicInteger _counter = new AtomicInteger();

  // すべてのtodoをためておくリスト
  private final ArrayList<TodoItem> _todoItems = new ArrayList<>() {
    {
      add(new TodoItem(_counter.incrementAndGet(), "todo 1"));
      add(new TodoItem(_counter.incrementAndGet(), "todo 2"));
      add(new TodoItem(_counter.incrementAndGet(), "todo 3"));
    }
  };
  @Override
  public TodoItem saveTodoItem(TodoItem todoItem) {
    if (Objects.isNull(todoItem.getTitle())) {
      throw new BadRequestException("Title must not be null");
    }
    todoItem.setId(_counter.incrementAndGet());
    _todoItems.add(todoItem);
    return todoItem;
  }

  @Override
  public ArrayList<TodoItem> getTodoItems() {
    return this._todoItems;
  }

  @Override
  public TodoItem getTodoItemById(int id) {
    return _findTodoItemById(id);
  }

  @Override
  public TodoItem updateTodoItem(TodoItem todoItem, int id) {
    TodoItem found = _findTodoItemById(id);
    _todoItems.remove(found);
    _todoItems.add(todoItem);

    return todoItem;
  }

  @Override
  public void deleteTodoItems(int id) {
    TodoItem found = _findTodoItemById(id);
    _todoItems.remove(found);
    
  }

  private TodoItem _findTodoItemById(int id) {
    Optional<TodoItem> found =  _todoItems.stream().filter(item -> item.getId() == id).findAny();

    if (!found.isPresent()) {
      throw new NotFoundException("Todo item is not available");
    }

    return found.get();
  }
  
}

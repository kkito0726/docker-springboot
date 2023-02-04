package com.example.simplespringrestapi.services;

import com.example.simplespringrestapi.models.TodoItem;
import java.util.List;

public interface TodoService {
  public TodoItem saveTodoItem(TodoItem todoItem);

  public List<TodoItem> getTodoItems();

  public TodoItem getTodoItemById(int id);

  public TodoItem updateTodoItem(TodoItem todoItem, int id);

  public void deleteTodoItems(int id);
}

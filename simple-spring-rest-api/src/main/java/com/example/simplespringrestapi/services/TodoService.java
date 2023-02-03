package com.example.simplespringrestapi.services;

import com.example.simplespringrestapi.models.TodoItem;
import java.util.ArrayList;

public interface TodoService {
  public TodoItem saveTodoItem(TodoItem todoItem);

  public ArrayList<TodoItem> getTodoItems();

  public TodoItem getTodoItemById(int id);

  public TodoItem updateTodoItem(TodoItem todoItem, int id);

  public void deleteTodoItems(int id);
}

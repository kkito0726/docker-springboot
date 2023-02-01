package com.example.simplespringrestapi.services;

import java.util.ArrayList;

import com.example.simplespringrestapi.models.TodoItem;

public interface TodoService {
  public TodoItem saveTodoItem(TodoItem todoItem);

  public ArrayList<TodoItem> getTodoItems();

  public TodoItem getTodoItemById(int id);

  public TodoItem updateTodoItem(TodoItem todoItem, int id);

  public void deleteTodoItems(int id);
}

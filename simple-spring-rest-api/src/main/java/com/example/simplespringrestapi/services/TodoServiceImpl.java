package com.example.simplespringrestapi.services;

import com.example.simplespringrestapi.errors.NotFoundException;
import com.example.simplespringrestapi.models.TodoItem;
import com.example.simplespringrestapi.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TodoServiceImpl implements TodoService {

  @Autowired(required = true)
  public TodoRepository _todoRepository;

  @Override
  public void deleteTodoItems(int id) {
    _todoRepository.deleteById(id);
  }

  @Override
  public TodoItem getTodoItemById(int id) {
    return _findTodoItemById(id);
  }

  @Override
  public List<TodoItem> getTodoItems() {
    return _todoRepository.findAll();
  }

  @Override
  public TodoItem saveTodoItem(TodoItem todoItem) {
    return _todoRepository.save(todoItem);
  }

  @Override
  public TodoItem updateTodoItem(TodoItem todoItem, int id) {
    return _todoRepository.save(todoItem);
  }

  private TodoItem _findTodoItemById(int id) {
    Optional<TodoItem> found = _todoRepository.findById(id);

    if (!found.isPresent()) {
      throw new NotFoundException("Todo item is not available");
    }

    return found.get();
  }
}

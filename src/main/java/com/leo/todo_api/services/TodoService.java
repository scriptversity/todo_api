package com.leo.todo_api.services;

import com.leo.todo_api.models.Todo;
import com.leo.todo_api.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

  private final TodoRepository repository;

  public TodoService(TodoRepository repository) {
    this.repository = repository;
  }

  public List<Todo> getAllTodos() {
    return repository.findAll();
  }

  public Optional<Todo> getTodoById(Long id) {
    return repository.findById(id);
  }

  public Todo createTodo(Todo todo) {
    return repository.save(todo);
  }

  public Optional<Todo> updateTodo(Long id, Todo todo) {
    return repository.update(id, todo);
  }

  public boolean deleteTodo(Long id) {
    return repository.delete(id);
  }
}

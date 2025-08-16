package com.leo.todo_api.services;

import com.leo.todo_api.models.Todo;
import com.leo.todo_api.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public List<Todo> getAllTodos() {
    return todoRepository.findAll();
  }

  public Optional<Todo> getTodoById(Long id) {
    return todoRepository.findById(id);
  }

  public Todo createTodo(Todo todo) {
    return todoRepository.save(todo);
  }

  public Optional<Todo> updateTodo(Long id, Todo updatedTodo) {
    return todoRepository.findById(id).map(existingTodo -> {
      existingTodo.setTitle(updatedTodo.getTitle());
      existingTodo.setCompleted(updatedTodo.isCompleted());
      return todoRepository.save(existingTodo);
    });
  }

  public boolean deleteTodo(Long id) {
    return todoRepository.findById(id).map(todo -> {
      todoRepository.deleteById(id);
      return true;
    }).orElse(false);
  }
}

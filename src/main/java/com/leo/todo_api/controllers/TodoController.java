package com.leo.todo_api.controllers;

import com.leo.todo_api.models.Todo;
import com.leo.todo_api.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @GetMapping
  public ResponseEntity<List<Todo>> getAllTodos() {
    return ResponseEntity.ok(todoService.getAllTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    return todoService.getTodoById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Todo> createTodo(
          @RequestParam Long userId,
          @RequestBody Todo todo
  ) {
    Todo created = todoService.createTodo(userId, todo);
    return ResponseEntity.ok(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
    return todoService.updateTodo(id, todo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    boolean deleted = todoService.deleteTodo(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}

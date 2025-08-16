package com.leo.todo_api.repositories;

import com.leo.todo_api.models.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoRepository {
  private final Map<Long, Todo> store = new HashMap<>();
  private final AtomicLong idGenerator = new AtomicLong();

  public List<Todo> findAll() {
    return new ArrayList<>(store.values());
  }

  public Optional<Todo> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  public Todo save(Todo todo) {
    Long id = idGenerator.incrementAndGet();
    todo.setId(id);
    store.put(id, todo);
    return todo;
  }

  public Optional<Todo> update(Long id, Todo updatedTodo) {
    if (store.containsKey(id)) {
      updatedTodo.setId(id);
      store.put(id, updatedTodo);
      return Optional.of(updatedTodo);
    }
    return Optional.empty();
  }

  public boolean delete(Long id) {
    return store.remove(id) != null;
    //Equivalent Code (More Verbose)
    /*Todo removedTodo = store.remove(id);
    if (removedTodo != null) {
      return true; // Todo existed and was removed
    } else {
      return false; // Todo did not exist
    }*/
  }
}

package com.leo.todo_api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
  private Long id;
  private String title;
  private boolean completed;
}

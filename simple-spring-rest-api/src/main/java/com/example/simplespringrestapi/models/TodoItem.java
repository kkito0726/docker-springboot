package com.example.simplespringrestapi.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {

  private int id;

  @NotBlank(message = "Title must not be blank.")
  private String title;
}

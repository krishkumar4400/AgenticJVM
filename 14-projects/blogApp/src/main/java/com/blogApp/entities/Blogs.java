package com.blogApp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@Getter
@Setter
public class Blogs {
    @Id
    private String id;
}

package com.example.cicd.service;

import com.example.cicd.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final ConcurrentHashMap<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    public Todo create(String title) {
        long id = counter.incrementAndGet();
        Todo todo = new Todo(id, title, false);
        store.put(id, todo);
        return todo;
    }

    public Todo update(Long id, Todo updated) {
        Todo existing = store.get(id);
        if (existing == null) return null;
        if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
        existing.setCompleted(updated.isCompleted());
        store.put(id, existing);
        return existing;
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}

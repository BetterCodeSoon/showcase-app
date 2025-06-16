package com.showcase.backend.service;

import static com.showcase.backend.error.ExceptionMessages.DELETE_NO_ID;
import static com.showcase.backend.error.ExceptionMessages.ENTITY_NULL;
import static com.showcase.backend.error.ExceptionMessages.FIND_NO_ID;
import static com.showcase.backend.error.ExceptionMessages.UPDATE_NO_ID;

import com.showcase.backend.domain.IdAble;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.repository.CrudRepository;

@Transactional
public abstract class AbstractCrudService<T extends IdAble<ID>, ID> {

  protected abstract CrudRepository<T, ID> getRepository();

  protected void validateEntity(T entity) {
    if (entity == null) {
      throw new IllegalArgumentException(ENTITY_NULL);
    }
  }

  public boolean doesNotExist(ID id) {
    return !getRepository().existsById(id);
  }

  public long count() {
    return getRepository().count();
  }

  public T save(T entity) {
    validateEntity(entity);
    return getRepository().save(entity);
  }

  public T update(ID id, T entity) {
    validateEntity(entity);
    if (doesNotExist(id)) {
      throw new EntityNotFoundException(String.format(UPDATE_NO_ID, id));
    }
    entity.setId(id);
    return getRepository().save(entity);
  }

  public T find(ID id) {
    return getRepository().findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(String.format(FIND_NO_ID, id)));
  }

  public List<T> findAll() {
    return StreamSupport.stream(getRepository().findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public void delete(ID id) {
    if (doesNotExist(id)) {
      throw new EntityNotFoundException(String.format(DELETE_NO_ID, id));
    }
    getRepository().deleteById(id);
  }

  public void delete(T entity) {
    validateEntity(entity);
    getRepository().delete(entity);
  }

  public void deleteAll() {
    getRepository().deleteAll();
  }
}
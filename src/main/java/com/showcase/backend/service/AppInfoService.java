package com.showcase.backend.service;

import com.showcase.backend.domain.AppInfo;
import com.showcase.backend.repository.AppInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppInfoService extends AbstractCrudService<AppInfo, Long> {

  private final AppInfoRepository repository;

  public AppInfoService(AppInfoRepository repository) {
    this.repository = repository;
  }

  @Override
  protected CrudRepository<AppInfo, Long> getRepository() {
    return repository;
  }
}

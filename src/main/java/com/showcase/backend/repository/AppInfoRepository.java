package com.showcase.backend.repository;

import com.showcase.backend.domain.AppInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoRepository extends CrudRepository<AppInfo, Long> {

}

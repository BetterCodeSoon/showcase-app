package com.showcase.backend.controller;

import com.showcase.backend.domain.AppInfo;
import com.showcase.backend.service.AppInfoService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppInfoController.ENDPOINT_URL)
public class AppInfoController {

  public static final String ENDPOINT_URL = "/appinfo";

  private final AppInfoService service;

  public AppInfoController(AppInfoService service) {
    this.service = service;
  }

  @GetMapping
  public List<AppInfo> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public AppInfo find(@PathVariable Long id) {
    return service.find(id);
  }

  @PostMapping
  public AppInfo save(@RequestBody AppInfo appInfo) {
    return service.save(appInfo);
  }

  @PutMapping("/{id}")
  public AppInfo update(@PathVariable Long id, @RequestBody AppInfo appInfo) {
    return service.update(id, appInfo);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}

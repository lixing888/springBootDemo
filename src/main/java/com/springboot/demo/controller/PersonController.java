package com.springboot.demo.controller;

import io.swagger.annotations.Api;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * Copyright (C), 2018-2021
 * FileName: PersonController
 * Author:   李兴
 * Date:     2021/5/18 11:40
 * Description: Person控制层
 */
@RestController
@RequestMapping("/api/persons")
@Api(tags = "/Person控制层")
@Validated
public class PersonController {

    @GetMapping("/{id}")
    //@Scheduled(fixedDelay = 1000 * 60 * 10)
    //@SchedulerLock(name = "queryRechargeBill", lockAtMostFor = "10s", lockAtLeastFor = "10s") //启用SchedulerLock
    public ResponseEntity<Integer> getPersonByID(@Valid @PathVariable("id") @Max(value = 5, message = "超过 id 的范围了") Integer id) {
        return ResponseEntity.ok().body(id);
    }

    @PutMapping
    public ResponseEntity<String> getPersonByName(@Valid @RequestParam("name") @Size(max = 6, message = "超过 name 的范围了") String name) {
        return ResponseEntity.ok().body(name);
    }
}

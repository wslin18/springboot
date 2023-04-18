package com.example.springboot.web;

import com.example.springboot.core.Result;
import com.example.springboot.core.ResultGenerator;
import com.example.springboot.model.SalesMan;
import com.example.springboot.service.SalesManService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
* @author CodeGenerator
* Created by CodeGenerator on 2023/04/18.
*/
@RestController
@RequestMapping("/sales/man")
public class SalesManController {
    @Resource
    private SalesManService salesManService;

    @PostMapping
    public Result add(@RequestBody SalesMan salesMan) {
        salesManService.save(salesMan);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        salesManService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody SalesMan salesMan) {
        salesManService.update(salesMan);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SalesMan salesMan = salesManService.findById(id);
        return ResultGenerator.genSuccessResult(salesMan);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<SalesMan> list = salesManService.findAll();
        PageInfo<SalesMan> pageInfo = new PageInfo<SalesMan>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

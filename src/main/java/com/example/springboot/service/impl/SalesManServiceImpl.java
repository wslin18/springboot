package com.example.springboot.service.impl;

import com.example.springboot.dao.SalesManMapper;
import com.example.springboot.model.SalesMan;
import com.example.springboot.service.SalesManService;
import com.example.springboot.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;


/**
 * @author CodeGenerator
 * Created by CodeGenerator on 2023/04/18.
 */
@Service
@Transactional
public class SalesManServiceImpl extends AbstractService<SalesMan> implements SalesManService {
    @Resource
    private SalesManMapper baseSalesmanMapper;

}

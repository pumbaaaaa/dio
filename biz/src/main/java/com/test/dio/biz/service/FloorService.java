package com.test.dio.biz.service;

import com.test.dio.biz.entity.Floor;
import com.test.dio.biz.mapper.FloorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService {

    @Autowired
    private FloorMapper floorMapper;

    public void getPostAllFloor() {
        List<Floor> floors = floorMapper.queryFloorFromPost(15929069L);
        floors.forEach(f -> {
            String content = f.getContent();
            System.out.println(content);
        });
    }
}

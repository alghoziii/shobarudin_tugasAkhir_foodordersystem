package com.sinaukoding.foodordersystem.service.transaction;

import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.filter.OrderFilterRecord;
import com.sinaukoding.foodordersystem.model.request.OrderRequestRecord;
import com.sinaukoding.foodordersystem.model.request.OrderUpdateRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    SimpleMap add(OrderRequestRecord request);

    SimpleMap edit(OrderUpdateRequestRecord request);

    Page<SimpleMap> findAll(OrderFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}

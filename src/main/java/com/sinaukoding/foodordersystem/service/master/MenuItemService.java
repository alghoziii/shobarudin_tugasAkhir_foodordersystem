package com.sinaukoding.foodordersystem.service.master;

import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.filter.MenuItemFilterRecord;
import com.sinaukoding.foodordersystem.model.request.MenuItemRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuItemService {
    SimpleMap add(MenuItemRequestRecord request);

    SimpleMap edit(MenuItemRequestRecord request);

    Page<SimpleMap> findAll(MenuItemFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}

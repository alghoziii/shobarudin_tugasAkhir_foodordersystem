package com.sinaukoding.foodordersystem.mapper.master;

import com.sinaukoding.foodordersystem.entity.master.MenuItem;
import com.sinaukoding.foodordersystem.entity.master.MenuItemImage;
import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.request.MenuItemRequestRecord;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuItemMapper {

    public MenuItem requestToEntity(MenuItemRequestRecord request) {
        MenuItem menuItem = MenuItem.builder()
                .nama(request.nama().trim().toUpperCase())
                .deskripsi(request.deskripsi())
                .harga(request.harga())
                .stok(request.stok())
                .status(request.status())
                .build();

        menuItem.setListImage(request.listImage().stream()
                .map(path -> MenuItemImage.builder()
                        .path(path)
                        .menuItem(menuItem)
                        .build())
                .collect(Collectors.toSet()));

        return menuItem;
    }

    public SimpleMap entityToSimpleMap(MenuItem entity) {
        return SimpleMap.createMap()
                .add("id", entity.getId())
                .add("nama", entity.getNama())
                .add("deskripsi", entity.getDeskripsi())
                .add("harga", entity.getHarga())
                .add("stok", entity.getStok())
                .add("status", entity.getStatus())
                .add("listImage", entity.getListImage().stream()
                        .map(MenuItemImage::getPath)
                        .collect(Collectors.toList()));
    }
}


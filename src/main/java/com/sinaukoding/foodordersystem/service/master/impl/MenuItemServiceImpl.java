package com.sinaukoding.foodordersystem.service.master.impl;

import com.sinaukoding.foodordersystem.builder.CustomBuilder;
import com.sinaukoding.foodordersystem.builder.CustomSpecification;
import com.sinaukoding.foodordersystem.builder.MultipleCriteria;
import com.sinaukoding.foodordersystem.builder.SearchCriteria;
import com.sinaukoding.foodordersystem.entity.master.MenuItem;
import com.sinaukoding.foodordersystem.entity.master.MenuItemImage;
import com.sinaukoding.foodordersystem.mapper.master.MenuItemMapper;
import com.sinaukoding.foodordersystem.model.app.AppPage;
import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.filter.MenuItemFilterRecord;
import com.sinaukoding.foodordersystem.model.request.MenuItemRequestRecord;
import com.sinaukoding.foodordersystem.repository.master.MenuItemRepository;
import com.sinaukoding.foodordersystem.service.app.ValidatorService;
import com.sinaukoding.foodordersystem.service.master.MenuItemService;
import com.sinaukoding.foodordersystem.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final ValidatorService validatorService;
    private final MenuItemMapper menuItemMapper;

    @Override
    public SimpleMap add(MenuItemRequestRecord request) {
        try {
            log.trace("Masuk ke menu tambah data produk");
            log.debug("Request data produk: {}", request);

            // validator mandatory
            validatorService.validator(request);

            if (request.stok() < 0) {
                log.warn("Stok produk tidak boleh kurang dari 0");
            }

            var entity = menuItemMapper.requestToEntity(request);
            var saved = menuItemRepository.save(entity);

            log.info("MenuItem {} berhasil ditambahkan", request.nama());
            log.trace("Tambah data menu item selesai");

            return SimpleMap.createMap()
                    .add("id", saved.getId())
                    .add("nama", saved.getNama())
                    .add("deskripsi", saved.getDeskripsi())
                    .add("harga", saved.getHarga())
                    .add("stok", saved.getStok())
                    .add("status", saved.getStatus())
                    .add("listImage", saved.getListImage().stream()
                            .map(img -> img.getPath())
                            .toList());
        } catch (Exception e) {
            log.error("Tambah data menu item gagal: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SimpleMap edit(MenuItemRequestRecord request) {
        validatorService.validator(request);

        var menuExisting = menuItemRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Menu tidak ditemukan"));

        var menu = menuItemMapper.requestToEntity(request);
        menu.setId(menuExisting.getId());

        var saved = menuItemRepository.save(menu);

        return SimpleMap.createMap()
                .add("id", saved.getId())
                .add("nama", saved.getNama())
                .add("deskripsi", saved.getDeskripsi())
                .add("harga", saved.getHarga())
                .add("stok", saved.getStok())
                .add("status", saved.getStatus());
    }


    @Override
    public Page<SimpleMap> findAll(MenuItemFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<MenuItem> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("stok", filterRequest.stok(), builder);

        if (filterRequest.hargaMin() != null && filterRequest.hargaMax() != null) {
            builder.with(MultipleCriteria.builder().criterias(
                    SearchCriteria.OPERATOR_AND,
                    SearchCriteria.of("harga", CustomSpecification.OPERATION_GREATER_THAN_EQUAL, filterRequest.hargaMin()),
                    SearchCriteria.of("harga", CustomSpecification.OPERATION_LESS_THAN_EQUAL, filterRequest.hargaMax())
            ));
        } else if (filterRequest.hargaMax() != null) {
            builder.with("harga", CustomSpecification.OPERATION_LESS_THAN_EQUAL, filterRequest.hargaMax());
        } else if (filterRequest.hargaMin() != null) {
            builder.with("harga", CustomSpecification.OPERATION_GREATER_THAN_EQUAL, filterRequest.hargaMin());
        }

        Page<MenuItem> listMenu = menuItemRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listMenu.stream().map(menuItem -> {
            SimpleMap data = new SimpleMap();
            data.put("id", menuItem.getId());
            data.put("nama", menuItem.getNama());
            data.put("deskripsi", menuItem.getDeskripsi());
            data.put("harga", menuItem.getHarga());
            data.put("stok", menuItem.getStok());
            data.put("status", menuItem.getStatus());
            data.put("createdDate", menuItem.getCreatedDate());
            data.put("modifiedDate", menuItem.getModifiedDate());
            data.put("listImage", menuItem.getListImage().stream().map(MenuItemImage::getPath).collect(Collectors.toSet()));
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listMenu.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var menuItem = menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu tidak ditemukan"));

        SimpleMap data = new SimpleMap();
        data.put("id", menuItem.getId());
        data.put("nama", menuItem.getNama());
        data.put("deskripsi", menuItem.getDeskripsi());
        data.put("harga", menuItem.getHarga());
        data.put("stok", menuItem.getStok());
        data.put("status", menuItem.getStatus());
        data.put("createdDate", menuItem.getCreatedDate());
        data.put("modifiedDate", menuItem.getModifiedDate());
        data.put("listImage", menuItem.getListImage().stream().map(MenuItemImage::getPath).collect(Collectors.toSet()));

        return data;
    }

}

package com.sinaukoding.foodordersystem.controller.master;

import com.sinaukoding.foodordersystem.model.enums.Status;
import com.sinaukoding.foodordersystem.model.filter.MenuItemFilterRecord;
import com.sinaukoding.foodordersystem.model.request.MenuItemRequestRecord;
import com.sinaukoding.foodordersystem.model.response.BaseResponse;
import com.sinaukoding.foodordersystem.service.master.MenuItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu-items")
@RequiredArgsConstructor
@Tag(name = "Menu API")
public class MenuItemController {
    private final MenuItemService  menuItemService;
    @PostMapping
    @PreAuthorize("hasRole('PENJUAL')")
    public BaseResponse<?> create(@RequestBody MenuItemRequestRecord request) {
        var result = menuItemService.add(request);
        return BaseResponse.ok("Data berhasil disimpan", result);
    }

    @PostMapping("edit")
    @PreAuthorize("hasRole('PENJUAL')")
    public BaseResponse<?> update(@RequestBody MenuItemRequestRecord request) {
        var result = menuItemService.edit(request);
        return BaseResponse.ok("Data berhasil diubah", result);
    }

    @GetMapping("find-all")
    public BaseResponse<?> findAll(
            @org.springdoc.core.annotations.ParameterObject
            @PageableDefault(sort = "modifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Integer stok,
            @RequestParam(required = false) Double hargaMin,
            @RequestParam(required = false) Double hargaMax
    ) {
        var filter = new MenuItemFilterRecord(nama, status, stok, hargaMin, hargaMax);
        return BaseResponse.ok("Data berhasil diambil", menuItemService.findAll(filter, pageable));
    }


    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("Data berhasil diambil", menuItemService.findById(id));
    }

}

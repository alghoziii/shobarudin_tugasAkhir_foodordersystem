package com.sinaukoding.foodordersystem;

import com.sinaukoding.foodordersystem.entity.master.MenuItem;
import com.sinaukoding.foodordersystem.mapper.master.MenuItemMapper;
import com.sinaukoding.foodordersystem.model.enums.Status;
import com.sinaukoding.foodordersystem.model.request.MenuItemRequestRecord;
import com.sinaukoding.foodordersystem.repository.master.MenuItemRepository;
import com.sinaukoding.foodordersystem.service.app.ValidatorService;
import com.sinaukoding.foodordersystem.service.master.impl.MenuItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Test
    void testAddMenuItem_Success() {
        Set<String> listImage = new HashSet<>();
        listImage.add("path1");

        var request = new MenuItemRequestRecord(null, "Bakso Goreng", "Bakso di goreng dadakan",
                25000D, 10, Status.AKTIF, listImage);

        var mappedEntity = new MenuItem();
        when(menuItemMapper.requestToEntity(request)).thenReturn(mappedEntity);

        when(menuItemRepository.save(any(MenuItem.class))).thenAnswer(inv -> {
            MenuItem e = inv.getArgument(0);
            e.setId(java.util.UUID.randomUUID().toString());
            return e;
        });

        // when
        menuItemService.add(request);

        // then
        verify(validatorService, times(1)).validator(request);
        verify(menuItemRepository, times(1)).save(mappedEntity);
    }

}

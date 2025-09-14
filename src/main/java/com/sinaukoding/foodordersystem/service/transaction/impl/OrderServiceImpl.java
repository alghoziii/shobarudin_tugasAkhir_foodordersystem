package com.sinaukoding.foodordersystem.service.transaction.impl;

import com.sinaukoding.foodordersystem.config.UserLoggedInConfig;
import com.sinaukoding.foodordersystem.model.request.OrderUpdateRequestRecord;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import com.sinaukoding.foodordersystem.builder.CustomBuilder;
import com.sinaukoding.foodordersystem.builder.CustomSpecification;
import com.sinaukoding.foodordersystem.builder.MultipleCriteria;
import com.sinaukoding.foodordersystem.builder.SearchCriteria;
import com.sinaukoding.foodordersystem.entity.transaction.Order;
import com.sinaukoding.foodordersystem.entity.transaction.OrderItem;
import com.sinaukoding.foodordersystem.mapper.master.OrderMapper;
import com.sinaukoding.foodordersystem.model.app.AppPage;
import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import com.sinaukoding.foodordersystem.model.filter.OrderFilterRecord;
import com.sinaukoding.foodordersystem.model.request.OrderRequestRecord;
import com.sinaukoding.foodordersystem.repository.managementuser.UserRepository;
import com.sinaukoding.foodordersystem.repository.master.MenuItemRepository;
import com.sinaukoding.foodordersystem.repository.master.OrderRepository;
import com.sinaukoding.foodordersystem.service.app.ValidatorService;
import com.sinaukoding.foodordersystem.service.transaction.OrderService;
import com.sinaukoding.foodordersystem.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    private final ValidatorService validatorService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public SimpleMap add(OrderRequestRecord request) {
        log.trace("Menambahkan order baru");
        validatorService.validator(request);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserLoggedInConfig) auth.getPrincipal();
        var userEntity = principal.getUser();

        var orderStatus = request.status() == null ? OrderStatus.DRAFT : request.status();
        var orderEntity = orderMapper.newOrder(userEntity, orderStatus);

        double totalAmount = 0.0;

        for (var requestItem : request.items()) {
            var name = requestItem.menuName().replaceAll("\\s+", " ").trim();

            var menuItemEntity = menuItemRepository.findByNamaIgnoreCase(name)
                    .orElseThrow(() -> new RuntimeException("Menu '" + name + "' tidak ditemukan"));

            int quantity = requestItem.qty();
            double price = menuItemEntity.getHarga();

            var orderItem = OrderItem.builder()
                    .order(orderEntity)
                    .menuItem(menuItemEntity)
                    .qty(quantity)
                    .price(price)
                    .build();

            orderEntity.addItem(orderItem);
            totalAmount += quantity * price;
        }

        log.info("Order {} berhasil dibuat", orderEntity.getId());

        orderEntity.setTotalAmount(totalAmount);
        var saved = orderRepository.save(orderEntity);

        return SimpleMap.createMap()
                        .add("orderId", saved.getId())
                        .add("status", saved.getStatus())
                        .add("totalAmount", saved.getTotalAmount());
    }


    @Override
    @Transactional
    public SimpleMap edit(OrderUpdateRequestRecord request) {
        log.trace("Mengedit order {}", request.id());
        validatorService.validator(request);

        var existingOrder = orderRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));

        if (request.status() != null) {
            existingOrder.setStatus(request.status());
        }

        var saved = orderRepository.save(existingOrder);
        log.info("Order {} berhasil diperbarui", existingOrder.getId());

        return SimpleMap.createMap()
                .add("orderId", saved.getId())
                .add("status", saved.getStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleMap> findAll(OrderFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Order> builder = new CustomBuilder<>();
        FilterUtil.builderConditionNotBlankEqualJoin("user.id", filterRequest.userId(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);

        if (filterRequest.tanggalMulai() != null && filterRequest.tanggalSelesai() != null) {
            builder.with(MultipleCriteria.builder().criterias(
                    SearchCriteria.OPERATOR_AND,
                    SearchCriteria.of("createdDate", CustomSpecification.OPERATION_GREATER_THAN_EQUAL, filterRequest.tanggalMulai()),
                    SearchCriteria.of("createdDate", CustomSpecification.OPERATION_LESS_THAN_EQUAL, filterRequest.tanggalSelesai())
            ));
        }

        var orderPage = orderRepository.findAll(builder.build(), pageable);
        var mappedList = orderPage.stream().map(this::toSimpleMap).toList();

        return AppPage.create(mappedList, pageable, orderPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleMap findById(String id) {
        var orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));
        return toSimpleMap(orderEntity);
    }

    private SimpleMap toSimpleMap(Order orderEntity) {
        var map = new SimpleMap();
        map.put("id", orderEntity.getId());
        map.put("userId", orderEntity.getUser().getId());
        map.put("status", orderEntity.getStatus());
        map.put("totalAmount", orderEntity.getTotalAmount());
        map.put("createdDate", orderEntity.getCreatedDate());
        map.put("modifiedDate", orderEntity.getModifiedDate());
        map.put("items", orderEntity.getListItem().stream().map(item -> {
            var detail = new SimpleMap();
            detail.put("id", item.getId());
            detail.put("menuItemId", item.getMenuItem().getId());
            detail.put("menuName", item.getMenuItem().getNama());
            detail.put("deskripsi", item.getMenuItem().getDeskripsi());
            detail.put("qty", item.getQty());
            detail.put("price", item.getPrice());
            detail.put("subtotal", item.getQty() * item.getPrice());
            return detail;
        }).collect(Collectors.toList()));
        return map;
    }
}
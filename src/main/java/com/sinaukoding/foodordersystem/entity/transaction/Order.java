package com.sinaukoding.foodordersystem.entity.transaction;

import com.sinaukoding.foodordersystem.entity.app.BaseEntity;
import com.sinaukoding.foodordersystem.entity.managementuser.User;
import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order", indexes = {
        @Index(name = "idx_t_order_created_date", columnList = "createdDate"),
        @Index(name = "idx_t_order_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_t_order_status", columnList = "status"),
})
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<OrderItem> listItem = new HashSet<>();

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.listItem.add(item);
    }
    public void removeItem(OrderItem item) {
        item.setOrder(null);
        this.listItem.remove(item);
    }
}

package com.sinaukoding.foodordersystem.entity.master;

import com.sinaukoding.foodordersystem.entity.app.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_menuItem_image", indexes = {
        @Index(name = "idx_menuItem_image_created_date", columnList = "createdDate"),
        @Index(name = "idx_menuItem_image_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_menuItem_image_id_menu", columnList = "id_menu"),
        @Index(name = "idx_menuItem_image_path", columnList = "path")
})
public class MenuItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private String path;

}

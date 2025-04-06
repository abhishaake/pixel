package com.av.pixel.dao;

import com.av.pixel.dao.base.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "images")
@Accessors(chain = true)
public class Images extends BaseEntity {

    @Column(name = "user_code")
    String userCode;

    @Column(name = "url")
    String url;

    @Column(name = "tag")
    String tag;

    @Column(name = "category")
    String category;

    @Column(name = "style")
    String style;

    @Column(name = "privacy")
    String privacy;

    @Type(JsonBinaryType.class)
    @Column(name = "metaData", columnDefinition = "jsonb")
    ImageMetaData metaData;
}

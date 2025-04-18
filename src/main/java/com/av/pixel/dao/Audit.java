package com.av.pixel.dao;

import com.av.pixel.dao.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "audit")
@Data
@Accessors(chain = true)
public class Audit extends BaseEntity {
    String userCode;
    String type;
    String reason;
    String amount;
    String packageId;
    String credits;
    String refId;
}

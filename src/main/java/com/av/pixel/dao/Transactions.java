package com.av.pixel.dao;


import com.av.pixel.dao.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Document("transactions")
public class Transactions extends BaseEntity {
    String userCode;
    Double amount;
    String txnType;
    String source;
    String refId;
    String orderType;
}

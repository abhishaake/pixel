package com.av.pixel.dao;


import com.av.pixel.dao.base.BaseEntity;
import com.av.pixel.enums.OrderStatusEnum;
import com.av.pixel.enums.OrderTypeEnum;
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

    Double creditsBefore;
    Double creditsInvolved;
    Double creditsAfter;

    OrderTypeEnum orderType;
    String orderId;
    String packageId;
    OrderStatusEnum status;
    String amountInRs;

    String source;
    String remarks;
}

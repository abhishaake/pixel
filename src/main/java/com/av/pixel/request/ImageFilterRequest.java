package com.av.pixel.request;

import com.av.pixel.request.base.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageFilterRequest extends PageRequest {
    List<String> userCodes;
    List<String> categories;
    List<String> styles;
    String privacy;
}

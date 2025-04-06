package com.av.pixel.response;

import com.av.pixel.dto.ImagesDTO;
import com.av.pixel.response.base.PageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageFilterResponse extends PageResponse {

    List<ImagesDTO> images;

    public ImageFilterResponse(List<ImagesDTO> images, long totalCount, long page, long size) {
        super(page, size, totalCount);
        this.images = images;
    }

}

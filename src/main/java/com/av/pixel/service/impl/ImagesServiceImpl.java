package com.av.pixel.service.impl;

import com.av.pixel.dao.Images;
import com.av.pixel.dto.ImagesDTO;
import com.av.pixel.helper.Validator;
import com.av.pixel.mapper.ImagesMap;
import com.av.pixel.repository.ImagesRepository;
import com.av.pixel.request.ImageFilterRequest;
import com.av.pixel.response.ImageFilterResponse;
import com.av.pixel.service.ImagesService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    ImagesRepository imagesRepository;

    @Override
    public ImageFilterResponse filterImages (ImageFilterRequest imageFilterRequest) {

        Validator.validateFilterImageRequest(imageFilterRequest, "");

        Page<Images> imagesDTOList = imagesRepository.filterImages(imageFilterRequest.getUserCodes(),
                                    imageFilterRequest.getCategories(),
                                    imageFilterRequest.getStyles(),
                                    imageFilterRequest.getPrivacy(),
                                    PageRequest.of(imageFilterRequest.getPage(), imageFilterRequest.getSize()));

        long totalCount = imagesDTOList.getTotalElements();

        return new ImageFilterResponse(ImagesMap.toList(imagesDTOList.getContent()), totalCount, imageFilterRequest.getPage(), imageFilterRequest.getSize());
    }
}

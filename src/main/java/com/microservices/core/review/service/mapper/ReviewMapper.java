package com.microservices.core.review.service.mapper;

import com.microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.review.service.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", source = "address")
    })
    ReviewDTO entityToDTO(Review review, String address);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    Review DTOToEntity(ReviewDTO reviewDTO);
}

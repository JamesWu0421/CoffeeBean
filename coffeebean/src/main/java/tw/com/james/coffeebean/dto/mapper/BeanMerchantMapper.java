package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;


import tw.com.james.coffeebean.dto.BeanMerchantDto;
import tw.com.james.coffeebean.entity.BeanMerchant;
import tw.com.james.coffeebean.vo.BeanMerchantVo;

@Mapper(componentModel = "spring")
public interface BeanMerchantMapper {

    // DTO → Entity（Create / Update 前用）
    BeanMerchant toEntity(BeanMerchantDto dto);

    // Entity → VO（回傳前端）
    BeanMerchantVo toVo(BeanMerchant entity);


}

package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tw.com.james.coffeebean.dto.GreenBeanCreateDto;
import tw.com.james.coffeebean.dto.GreenBeanUpdateDto;
import tw.com.james.coffeebean.entity.CoffeeBean;
import tw.com.james.coffeebean.vo.GreenBeanVo;

@Mapper(componentModel = "spring")
public interface GreenBeanDtoMapper {

    // ===== CreateDto -> Entity  =====
    @Mapping(source = "countryId", target = "country.id")
    @Mapping(source = "processMethodId", target = "processMethod.id")
    @Mapping(source = "beanMerchantId", target = "beanMerchant.id")
    CoffeeBean toEntity(GreenBeanCreateDto dto);

    // ===== UpdateDto -> Entity  =====
    @Mapping(source = "countryId", target = "country.id")
    @Mapping(source = "processMethodId", target = "processMethod.id")
    @Mapping(source = "beanMerchantId", target = "beanMerchant.id")
    void updateEntity(GreenBeanUpdateDto dto, @MappingTarget CoffeeBean entity);

    // ===== Entity -> VO  =====
    @Mapping(source = "country.countryName", target = "country")
    @Mapping(source = "processMethod.processMethod", target = "processMethod") 
    @Mapping(source = "beanMerchant.merchantName", target = "beanMerchant") 
    GreenBeanVo toVO(CoffeeBean entity);
}   
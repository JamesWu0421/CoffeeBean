package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tw.com.james.coffeebean.dto.RoastCreateDto;
import tw.com.james.coffeebean.dto.RoastUpdateDto;
import tw.com.james.coffeebean.entity.Roast;
import tw.com.james.coffeebean.vo.RoastVo;

@Mapper(componentModel = "spring")
public interface RoastDtoMapper {

    // CreateDto -> Entity
    @Mapping(source = "coffeeBeanId", target = "coffeeBean.id")
    Roast toEntity(RoastCreateDto dto);

    // UpdateDto -> Entity
    @Mapping(source = "coffeeBeanId", target = "coffeeBean.id")
    void updateEntity(RoastUpdateDto dto, @MappingTarget Roast entity);

    // Entity -> VO
    @Mapping(source = "coffeeBean.id", target = "coffeeBeanId")
    // 假設 CoffeeBean 有 beanVariety 欄位，若名稱不同請自行修改 source
    @Mapping(source = "coffeeBean.beanVariety", target = "coffeeBeanName") 
    RoastVo toVo(Roast entity);
}
package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tw.com.james.coffeebean.dto.StockCreateDto;
import tw.com.james.coffeebean.dto.StockUpdateDto;
import tw.com.james.coffeebean.entity.Stock;
import tw.com.james.coffeebean.vo.StockVo;

@Mapper(componentModel = "spring")
public interface StockDtoMapper {

    // CreateDto -> Entity
    @Mapping(source = "coffeeBeanId", target = "coffeeBean.id")
    Stock toEntity(StockCreateDto dto);

    // UpdateDto -> Entity
    @Mapping(source = "coffeeBeanId", target = "coffeeBean.id")
    void updateEntity(StockUpdateDto dto, @MappingTarget Stock entity);

    // Entity -> VO
    @Mapping(source = "coffeeBean.id", target = "coffeeBeanId")
    // 假設 CoffeeBean 有 beanVariety 欄位
    @Mapping(source = "coffeeBean.beanVariety", target = "coffeeBeanName")
    StockVo toVo(Stock entity);
}
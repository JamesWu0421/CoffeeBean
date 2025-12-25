package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;
import tw.com.james.coffeebean.dto.CountryDto;
import tw.com.james.coffeebean.entity.Country;
import tw.com.james.coffeebean.vo.CountryVo;

@Mapper(componentModel = "spring")
public interface CountryDtoMapper {

    
    Country toEntity(CountryDto dto);

    
    CountryVo toVO(Country entity);
}

package tw.com.james.coffeebean.dto.mapper;

import org.mapstruct.Mapper;


import tw.com.james.coffeebean.dto.ProcessMethodDto;
import tw.com.james.coffeebean.entity.ProcessMethod;
import tw.com.james.coffeebean.vo.ProcessMethodVo;

@Mapper(componentModel = "spring")
public interface ProcessMethodMapper {

    // DTO → Entity
    ProcessMethod toEntity(ProcessMethodDto dto);

    // Entity → VO
    ProcessMethodVo toVo(ProcessMethod entity);


}

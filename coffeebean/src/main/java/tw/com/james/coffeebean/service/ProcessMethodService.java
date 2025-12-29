package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.james.coffeebean.dto.ProcessMethodDto;
import tw.com.james.coffeebean.entity.ProcessMethod;
import tw.com.james.coffeebean.dto.mapper.ProcessMethodMapper;
import tw.com.james.coffeebean.repository.ProcessMethodRepository;
import tw.com.james.coffeebean.vo.ProcessMethodVo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessMethodService {

    private final ProcessMethodRepository repo;
    private final ProcessMethodMapper mapper;

    public ProcessMethodService(
            ProcessMethodRepository repo,
            ProcessMethodMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
    }

    // ===== CREATE =====
    @Transactional
    public ProcessMethodVo create(ProcessMethodDto dto) {

        ProcessMethod entity = mapper.toEntity(dto);
        repo.save(entity);

        return mapper.toVo(entity);
    }

    // ===== DELETE =====
    @Transactional
    public boolean delete(Integer id) {

        ProcessMethod entity = repo.findById(id);
        if (entity == null) {
            return false;
        }

        repo.delete(id);
        return true;
    }

    // ===== UPDATE =====
    @Transactional
    public ProcessMethodVo update(ProcessMethodDto dto) {

        ProcessMethod entity = repo.findById(dto.getId());
        if (entity == null) {
            return null;
        }
        
        entity.setProcessMethod(dto.getProcessMethod());
        entity.setProcessMethodEng(dto.getProcessMethodEng());
        repo.update(entity);

        return mapper.toVo(entity);
    }

    // ===== READ : list + pageable =====
    @Transactional(readOnly = true)
    public Page<ProcessMethodVo> findAll(Pageable pageable) {

        List<ProcessMethod> all = repo.findAll();
        int total = all.size();
        int start = (int) pageable.getOffset();

        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        List<ProcessMethodVo> content = all.subList(start, end)
                .stream()
                .map(mapper::toVo)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    @Transactional(readOnly = true)
    public List<ProcessMethodVo> findAllList() {
        return repo.findAll().stream()
                .map(mapper::toVo)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProcessMethod> search(String name, String engName) {
        return repo.search(name, engName);
    }
}

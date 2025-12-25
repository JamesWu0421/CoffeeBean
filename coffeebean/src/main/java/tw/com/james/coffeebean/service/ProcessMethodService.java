package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.entity.ProcessMethod;
import tw.com.james.coffeebean.repository.ProcessMethodRepository;

import java.util.List;

@Service
public class ProcessMethodService {

    private final ProcessMethodRepository repo;

    public ProcessMethodService(ProcessMethodRepository repo) {
        this.repo = repo;
    }

    /* ===== Create ===== */
    @Transactional
    public ProcessMethod create(ProcessMethod method) {
        repo.save(method);
        return method;
    }

    /* ===== Update ===== */
    @Transactional
    public ProcessMethod update(ProcessMethod req) {
        ProcessMethod entity = repo.findById(req.getId());
        if (entity == null) return null;

        entity.setProcessMethod(req.getProcessMethod());
        entity.setProcessMethodEng(req.getProcessMethodEng());
        return repo.update(entity);
    }

    /* ===== Delete ===== */
    @Transactional
    public boolean delete(Integer id) {
        ProcessMethod entity = repo.findById(id);
        if (entity == null) return false;

        repo.delete(id);
        return true;
    }

    /* ===== Read : list + pageable ===== */
    @Transactional(readOnly = true)
    public Page<ProcessMethod> findAll(Pageable pageable) {

        List<ProcessMethod> all = repo.findAll();
        int total = all.size();
        int start = (int) pageable.getOffset();

        
        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int end = Math.min(start + pageable.getPageSize(), total);

        return new PageImpl<>(
                all.subList(start, end),
                pageable,
                total
        );
    }
}

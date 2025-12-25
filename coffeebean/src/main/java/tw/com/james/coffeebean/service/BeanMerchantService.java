package tw.com.james.coffeebean.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.james.coffeebean.entity.BeanMerchant;
import tw.com.james.coffeebean.repository.BeanMerchantRepository;

import java.util.List;

@Service
public class BeanMerchantService {

    private final BeanMerchantRepository repo;

    public BeanMerchantService(BeanMerchantRepository repo) {
        this.repo = repo;
    }

    /* ===== Create ===== */
    @Transactional
    public BeanMerchant create(BeanMerchant merchant) {
        repo.save(merchant);
        return merchant;
    }

    /* ===== Update ===== */
    @Transactional
    public BeanMerchant update(BeanMerchant req) {
        BeanMerchant entity = repo.findById(req.getId());
        if (entity == null) return null;

        entity.setMerchantName(req.getMerchantName());
        return repo.update(entity);
    }

    /* ===== Delete ===== */
    @Transactional
    public boolean delete(Integer id) {
        BeanMerchant entity = repo.findById(id);
        if (entity == null) return false;

        repo.delete(id);
        return true;
    }

    /* ===== Read : list + pageable ===== */
    @Transactional(readOnly = true)
    public Page<BeanMerchant> findAll(Pageable pageable) {

        List<BeanMerchant> all = repo.findAll();
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

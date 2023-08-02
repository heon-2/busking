package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class CompanyJpaEntityTest {

    @Autowired
    private BusRepository busJpaRepository;

    @Autowired
    private CompanyRepository companyJpaRepository;

    @Test
    public void testCreateAndRetrieveCompanyWithBus() {
        // 새로운 회사 생성
        CompanyJpaEntity company = new CompanyJpaEntity();
        company.setName("싸피");
        companyJpaRepository.save(company);

        // 버스 생성
        BusPK busPK = new BusPK(company.getId(), 5L);
        BusJpaEntity bus = new BusJpaEntity();
        bus.setId(busPK);
        bus.setCompany(company);

        busJpaRepository.save(bus);

        // 데이터베이스에서 버스를 조회하여 확인
        Optional<BusJpaEntity> savedBusOptional = busJpaRepository.findById(busPK);
        if (savedBusOptional.isPresent()) {
            BusJpaEntity savedBus = savedBusOptional.get();
            System.out.println("Saved Bus: " + savedBus.getId().getCompanyId() + ", " + savedBus.getId().getBusNum());
            System.out.println("Company Name: " + savedBus.getCompany().getName());
        }
    }
}

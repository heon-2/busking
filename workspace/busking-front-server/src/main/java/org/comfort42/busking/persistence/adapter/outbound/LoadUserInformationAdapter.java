package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadUserInformationPort;
import org.comfort42.busking.common.PersistenceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@PersistenceAdapter
class LoadUserInformationAdapter implements LoadUserInformationPort {

    private final static UserMapper userMapper = UserMapper.getInstance();
    private final ViewUserRepository viewUserRepository;

    @Override
    public List<User> loadViewUser(long companyId, long page) {
        List<User> list = new ArrayList<>();
        for (UserJpaEntity user : viewUserRepository.findByCompanyId(companyId,page)) {
            list.add(userMapper.mapToDomainEntity(user));

        }
        return list;
    }
    @Override
    public Long totalPage(long companyId) {
        return viewUserRepository.countByCompanyId(companyId);
    }

    @Override
    public User loadUserDetail(UUID userId) {
        Optional<UserJpaEntity> user = viewUserRepository.findById(userId);

        if(user.isPresent()){
            return userMapper.mapToDomainEntity(user.get());
        }else{
            return null;
        }
    }
}

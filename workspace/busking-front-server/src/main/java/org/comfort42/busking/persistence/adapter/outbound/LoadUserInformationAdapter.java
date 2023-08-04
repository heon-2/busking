package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadUserInformationPort;
import org.comfort42.busking.common.PersistenceAdapter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
class LoadUserInformationAdapter implements LoadUserInformationPort {

    private final static UserMapper userMapper = UserMapper.getInstance();
    private final UserRepository userRepository;

    @Override
    public List<User> LoadViewUser() {
//        List<User> list = new ArrayList<>();
//        int index = 0;
//        for (UserJpaEntity user : userRepository.findAll()) {
//            list.add(userMapper.mapToDomainEntity(user));
//        }
//
//        return list;
        return new ArrayList<>();
    }

    @Override
    public User LoadDetailUser() {
        return null;
    }
}

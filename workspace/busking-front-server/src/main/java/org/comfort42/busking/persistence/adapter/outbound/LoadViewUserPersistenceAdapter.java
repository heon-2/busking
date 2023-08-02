package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadViewUserPort;
import org.comfort42.busking.common.PersistenceAdapter;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@PersistenceAdapter
public class LoadViewUserPersistenceAdapter implements LoadViewUserPort {

    private final  UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Optional<List<User>> LoadViewUser() {


        return Optional.empty();
    }

    @Override
    public Optional<User> LoadDetailUser() {
        return null;
    }
}

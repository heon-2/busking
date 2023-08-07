// TODO(권준일): 네이밍 컨벤션 준수, 페이지네이션으로 구현

package org.comfort42.busking.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.ViewUserPayload;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;

import org.comfort42.busking.application.port.outbound.LoadUserInformationPort;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.common.UseCase;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@UseCase
@Transactional
public class ViewUserService implements ViewUserUseCase {

    private final LoadUserInformationPort loadUserInformationPort;

    @Override
    public ConcurrentHashMap<String, Object> userList(long companyId,long page) {

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        long pageNum = loadUserInformationPort.totalPage(companyId);
        map.put("totalPageNum",(pageNum/10)+1);

        List<ViewUserPayload> list = new ArrayList<>();
        for (final User user : loadUserInformationPort.loadViewUser(companyId,page)) {

            list.add(new ViewUserPayload(
                    user.username(),
                    user.realName(),
                    user.email(),
                    user.phoneNumber(),
                    user.companyId().toString(),
                    user.role().value()));
        }
        map.put("list",list);
        return map;
    }

    @Override
    public ViewUserPayload userDetail(UUID userId) {

        User user =  loadUserInformationPort.loadUserDetail(userId);
        return new ViewUserPayload( user.username(),
                                    user.realName(),
                                    user.email(),
                                    user.phoneNumber(),
                                    user.companyId().toString(),
                                    user.role().value());
    }

}

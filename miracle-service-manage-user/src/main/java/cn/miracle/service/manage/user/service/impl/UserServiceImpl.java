package cn.miracle.service.manage.user.service.impl;

import cn.miracle.framework.common.util.IdWorker;
import cn.miracle.framework.model.user.User;
import cn.miracle.service.manage.user.dao.UserRepository;
import cn.miracle.service.manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 16:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdWorker idWorker;

    /**
     * {@inheritDoc}
     *
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        user.setId(idWorker.nextId());
        userRepository.save(user);
        return user;
    }

    /**
     * {@inheritDoc}
     *
     * @param user
     * @return
     */
    @Override
    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    /**
     * {@inheritDoc}
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        userRepository.deleteById(id);
        return 1;
    }
}

package cn.miracle.service.manage.user.dao;

import cn.miracle.framework.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 21:55
 */
public interface UserRepository extends JpaRepository<User, Long> {
}

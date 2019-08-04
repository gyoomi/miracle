package cn.miracle.service.manage.user.dao;

import cn.miracle.framework.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 21:55
 */
public interface MenuRepository extends JpaRepository<Role, Long> {
}

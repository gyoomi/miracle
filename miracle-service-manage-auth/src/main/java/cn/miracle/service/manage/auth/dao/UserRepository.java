/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.manage.auth.dao;

import cn.miracle.framework.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 接口功能描述
 *
 * @author Leon
 * @version 2019/8/7 16:53
 */
public interface UserRepository extends JpaRepository<User, Long> {

}

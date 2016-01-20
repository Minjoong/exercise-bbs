package kim.minjoong.bbs.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service(value = "userDao")
public class UserDao {
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    public UserVo getSelectById(String id) {
        return this.userMapper.selectById(id);
    }

    public UserVo getSelectOne(int idx) {
        return this.userMapper.selectOne(idx);
    }

    public void insert(UserVo userVo) {
         this.userMapper.insert(userVo);
    }

    public void update(UserVo userVo) {
         this.userMapper.update(userVo);
    }

    public void delete(int idx) {
         this.userMapper.delete(idx);
    }
}

package kim.minjoong.bbs.dao;

import org.springframework.stereotype.Repository;

@Repository(value = "userMapper")
public interface UserMapper {
	UserVo selectById(String id);

	UserVo selectOne(int idx);

	void insert(UserVo userVo);

	void update(UserVo userVo);

	void delete(int idx);
}
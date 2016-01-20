package kim.minjoong.bbs.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository(value = "bbsMapper")
public interface BbsMapper {
	List<BbsVo> select();

	List<BbsVo> selectSearch(String search);
	
	List<String> selectSubjectList();

	BbsVo selectOne(int idx);

	void insert(BbsVo bbsVo);

	void update(BbsVo bbsVo);

	void delete(int idx);
}
package com.dj.repo;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.dj.model.LargeBlob;

@Accessor
public interface LargeBlobAccessor {
	@Query("select * from large_object_blob where context_id = ? and record_num = ?")
	LargeBlob getOne(String contextId, int recordNum);

	@Query("select * from large_object_blob where context_id = ?")
	Result<LargeBlob> getAllForContext(String contextId);
}

package org.nevskiy619.dao;

import org.nevskiy619.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData, Long> {

}

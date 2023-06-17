package org.nevskiy619.dao;

import org.nevskiy619.entity.AppPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}

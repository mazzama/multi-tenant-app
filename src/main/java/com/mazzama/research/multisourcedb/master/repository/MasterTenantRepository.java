package com.mazzama.research.multisourcedb.master.repository;

import com.mazzama.research.multisourcedb.master.domain.MasterTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTenantRepository extends JpaRepository<MasterTenant, Long> {
}

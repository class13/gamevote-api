package at.tailor.gamevoteapi.party.service.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BeerRepository: JpaRepository<BeerEntity, Long> {
    @Query("select b from BeerEntity b where b.party.id = :partyId")
    fun findByPartyId(partyId: Long): List<BeerEntity>
}
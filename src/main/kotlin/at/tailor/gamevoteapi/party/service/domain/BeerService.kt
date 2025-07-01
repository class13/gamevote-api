package at.tailor.gamevoteapi.party.service.domain

import at.tailor.gamevoteapi.party.service.domain.data.Beer
import at.tailor.gamevoteapi.party.service.persistence.BeerEntity
import at.tailor.gamevoteapi.party.service.persistence.BeerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BeerService (
    val beerRepository: BeerRepository
) {
    fun createHourlySummary(partyId: Long): Map<String, Map<LocalDateTime, Int>> {
        val beers = beerRepository.findByPartyId(partyId)
        return beers.groupBy { beer -> beer.attendee }.mapValues {
            it.value.groupBy { beer -> beer.createdAt.truncateToHour() }.mapValues {
                entry -> entry.value.count()
            }
        }
    }

    private fun LocalDateTime.truncateToHour(): LocalDateTime {
        return this.withMinute(0).withSecond(0).withNano(0)
    }
}
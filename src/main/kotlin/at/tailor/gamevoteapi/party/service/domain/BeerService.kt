package at.tailor.gamevoteapi.party.service.domain

import at.tailor.gamevoteapi.party.service.persistence.BeerRepository
import at.tailor.gamevoteapi.party.service.persistence.PartyRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class BeerService(
    val beerRepository: BeerRepository,
    val partyRepository: PartyRepository
) {

    @Transactional
    fun createHourlySummary(partyId: Long): Map<String, Map<LocalDateTime, Int>> {
        val timeline = buildTimeline(partyId)
        return timeline.attendees.associateWith { attendee ->
            timeline.hours.associateWith { hour ->
                timeline.hourlyCounts[attendee]?.get(hour) ?: 0
            }
        }
    }

    @Transactional
    fun createCumulativeHourlySummary(partyId: Long): Map<String, Map<LocalDateTime, Int>> {
        val timeline = buildTimeline(partyId)
        return timeline.attendees.associateWith { attendee ->
            var runningTotal = 0
            timeline.hours.associateWith { hour ->
                runningTotal += timeline.hourlyCounts[attendee]?.get(hour) ?: 0
                runningTotal
            }
        }
    }

    private fun buildTimeline(partyId: Long): BeerTimeline {
        val party = partyRepository.findById(partyId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val beers = beerRepository.findByPartyId(partyId)
        val attendees = (party.attendees + beers.map { it.attendee }).distinct()
        val observedHours = beers.map { it.createdAt.truncateToHour() }.distinct().sorted()
        val hours = if (observedHours.isEmpty()) {
            emptyList()
        } else {
            generateSequence(observedHours.first()) { currentHour ->
                currentHour.plusHours(1).takeUnless { it.isAfter(observedHours.last()) }
            }.toList()
        }
        val hourlyCounts = beers
            .groupBy { beer -> beer.attendee }
            .mapValues { (_, attendeeBeers) ->
                attendeeBeers
                    .groupBy { beer -> beer.createdAt.truncateToHour() }
                    .mapValues { (_, beersInHour) -> beersInHour.count() }
            }

        return BeerTimeline(
            attendees = attendees,
            hours = hours,
            hourlyCounts = hourlyCounts
        )
    }

    private fun LocalDateTime.truncateToHour(): LocalDateTime {
        return this.withMinute(0).withSecond(0).withNano(0)
    }

    private data class BeerTimeline(
        val attendees: List<String>,
        val hours: List<LocalDateTime>,
        val hourlyCounts: Map<String, Map<LocalDateTime, Int>>
    )
}

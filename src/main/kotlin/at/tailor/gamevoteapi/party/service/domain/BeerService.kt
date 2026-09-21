package at.tailor.gamevoteapi.party.service.domain

import at.tailor.gamevoteapi.party.service.persistence.BeerRepository
import at.tailor.gamevoteapi.party.service.persistence.PartyRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.round

@Service
class BeerService(
    val beerRepository: BeerRepository,
    val partyRepository: PartyRepository
) {

    companion object {
        const val DEFAULT_BODY_WEIGHT_KG = 80.0
        const val DEFAULT_BEER_VOLUME_ML = 500
        const val DEFAULT_BEER_ALCOHOL_BY_VOLUME = 0.05
        const val DEFAULT_BODY_WATER_DISTRIBUTION = 0.68
        const val DEFAULT_ALCOHOL_ELIMINATION_PER_HOUR = 0.15
        private const val ALCOHOL_DENSITY_GRAMS_PER_ML = 0.789
    }

    @Transactional
    fun createHourlySummary(
        partyId: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): Map<String, Map<LocalDateTime, Int>> {
        val timeline = buildTimeline(partyId, now)
        return timeline.attendees.associateWith { attendee ->
            timeline.hours.associateWith { hour ->
                timeline.hourlyCounts[attendee]?.get(hour) ?: 0
            }
        }
    }

    @Transactional
    fun createCumulativeHourlySummary(
        partyId: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): Map<String, Map<LocalDateTime, Int>> {
        val timeline = buildTimeline(partyId, now)
        return timeline.attendees.associateWith { attendee ->
            var runningTotal = 0
            timeline.hours.associateWith { hour ->
                runningTotal += timeline.hourlyCounts[attendee]?.get(hour) ?: 0
                runningTotal
            }
        }
    }

    @Transactional(readOnly = true)
    fun createPromilleSummary(
        partyId: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): Map<String, Map<LocalDateTime, Double>> {
        val timeline = buildTimeline(partyId, now)
        val beers = beerRepository.findByPartyId(partyId)
            .groupBy { it.attendee }

        return timeline.attendees.associateWith { attendee ->
            val attendeeBeers = beers[attendee].orEmpty().sortedBy { it.createdAt }
            timeline.hours.associateWith { hour ->
                estimatePromille(attendeeBeers, hour)
            }
        }
    }

    private fun estimatePromille(beers: List<at.tailor.gamevoteapi.party.service.persistence.BeerEntity>, at: LocalDateTime): Double {
        val alcoholGramsPerBeer = DEFAULT_BEER_VOLUME_ML * DEFAULT_BEER_ALCOHOL_BY_VOLUME * ALCOHOL_DENSITY_GRAMS_PER_ML
        val distributionVolume = DEFAULT_BODY_WEIGHT_KG * DEFAULT_BODY_WATER_DISTRIBUTION

        return beers
            .filter { !it.createdAt.isAfter(at.plusHours(1)) }
            .sumOf { beer ->
                val hoursSinceDrink = max(0.0, Duration.between(beer.createdAt, at).toMinutes() / 60.0)
                max(0.0, alcoholGramsPerBeer / distributionVolume - DEFAULT_ALCOHOL_ELIMINATION_PER_HOUR * hoursSinceDrink)
            }
            .roundToTwoDecimals()
    }

    private fun Double.roundToTwoDecimals(): Double = round(this * 100) / 100

    private fun buildTimeline(partyId: Long, now: LocalDateTime): BeerTimeline {
        val party = partyRepository.findById(partyId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val beers = beerRepository.findByPartyId(partyId)
        val attendees = (party.attendees + beers.map { it.attendee }).distinct()
        val observedHours = beers.map { it.createdAt.truncateToHour() }.distinct().sorted()
        val hours = if (observedHours.isEmpty()) {
            emptyList()
        } else {
            val lastHour = maxOf(observedHours.last(), now.truncateToHour())
            val hourlyPoints = generateSequence(observedHours.first()) { currentHour ->
                currentHour.plusHours(1).takeUnless { it.isAfter(lastHour) }
            }.toList()
            if (now.isAfter(lastHour)) hourlyPoints + now else hourlyPoints
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

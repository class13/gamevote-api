package at.tailor.gamevoteapi.party.service.domain

import at.tailor.gamevoteapi.party.service.persistence.BeerEntity
import at.tailor.gamevoteapi.party.service.persistence.BeerRepository
import at.tailor.gamevoteapi.party.service.persistence.PartyEntity
import at.tailor.gamevoteapi.party.service.persistence.PartyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
class BeerServiceIntegrationTest {

    @Autowired
    private lateinit var beerService: BeerService

    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Autowired
    private lateinit var partyRepository: PartyRepository

    @Test
    @Transactional
    fun `should create hourly summary with explicit zero-value gaps for every attendee`() {
        val party = partyRepository.save(
            PartyEntity(
                attendees = listOf("Alice", "Bob", "Charlie"),
                status = "NOMINATION",
                code = "GRAPH1A"
            )
        )
        beerRepository.saveAll(
            listOf(
                BeerEntity(party = party, attendee = "Alice", createdAt = hour(20, 15)),
                BeerEntity(party = party, attendee = "Bob", createdAt = hour(20, 40)),
                BeerEntity(party = party, attendee = "Alice", createdAt = hour(22, 5)),
                BeerEntity(party = party, attendee = "Bob", createdAt = hour(23, 0))
            )
        )

        val summary = beerService.createHourlySummary(party.id)

        assertThat(summary["Alice"]).isEqualTo(
            linkedMapOf(
                hour(20) to 1,
                hour(21) to 0,
                hour(22) to 1,
                hour(23) to 0
            )
        )
        assertThat(summary["Bob"]).isEqualTo(
            linkedMapOf(
                hour(20) to 1,
                hour(21) to 0,
                hour(22) to 0,
                hour(23) to 1
            )
        )
        assertThat(summary["Charlie"]).isEqualTo(
            linkedMapOf(
                hour(20) to 0,
                hour(21) to 0,
                hour(22) to 0,
                hour(23) to 0
            )
        )
    }

    @Test
    @Transactional
    fun `should create cumulative hourly summary with flat lines for hours without new beers`() {
        val party = partyRepository.save(
            PartyEntity(
                attendees = listOf("Alice", "Bob", "Charlie"),
                status = "NOMINATION",
                code = "GRAPH2A"
            )
        )
        beerRepository.saveAll(
            listOf(
                BeerEntity(party = party, attendee = "Alice", createdAt = hour(20, 15)),
                BeerEntity(party = party, attendee = "Bob", createdAt = hour(20, 40)),
                BeerEntity(party = party, attendee = "Alice", createdAt = hour(22, 5)),
                BeerEntity(party = party, attendee = "Bob", createdAt = hour(23, 0))
            )
        )

        val summary = beerService.createCumulativeHourlySummary(party.id)

        assertThat(summary["Alice"]).isEqualTo(
            linkedMapOf(
                hour(20) to 1,
                hour(21) to 1,
                hour(22) to 2,
                hour(23) to 2
            )
        )
        assertThat(summary["Bob"]).isEqualTo(
            linkedMapOf(
                hour(20) to 1,
                hour(21) to 1,
                hour(22) to 1,
                hour(23) to 2
            )
        )
        assertThat(summary["Charlie"]).isEqualTo(
            linkedMapOf(
                hour(20) to 0,
                hour(21) to 0,
                hour(22) to 0,
                hour(23) to 0
            )
        )
    }

    private fun hour(hour: Int, minute: Int = 0): LocalDateTime {
        return LocalDateTime.of(2026, 3, 29, hour, minute)
    }
}

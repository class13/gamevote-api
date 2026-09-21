package at.tailor.gamevoteapi.party.service.domain

import at.tailor.gamevoteapi.party.service.persistence.PartyEntity
import at.tailor.gamevoteapi.party.service.persistence.PartyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@SpringBootTest
class PartyServiceIntegrationTest {

    @Autowired
    private lateinit var partyService: PartyService

    @Autowired
    private lateinit var partyRepository: PartyRepository

    @Test
    @Transactional
    fun `should suggest matching options from current and previous parties with prefix matches first`() {
        val currentParty = partyRepository.save(
            PartyEntity(
                attendees = listOf("Alice"),
                options = listOf("Catan", "Terraforming Mars"),
                status = "NOMINATION",
                code = "AUTO11"
            )
        )
        partyRepository.save(
            PartyEntity(
                attendees = listOf("Bob"),
                options = listOf("Carcassonne", "Caverna", "Azul"),
                status = "NOMINATION",
                code = "AUTO12"
            )
        )
        partyRepository.save(
            PartyEntity(
                attendees = listOf("Charlie"),
                options = listOf("catan", "Scythe"),
                status = "NOMINATION",
                code = "AUTO13"
            )
        )

        val suggestions = partyService.suggestOptions(currentParty.id, "ca")

        assertThat(suggestions).containsExactly("Carcassonne", "Catan", "Caverna")
    }

    @Test
    @Transactional
    fun `should return empty suggestions for blank query`() {
        val party = partyRepository.save(
            PartyEntity(
                attendees = listOf("Alice"),
                options = listOf("Catan"),
                status = "NOMINATION",
                code = "AUTO21"
            )
        )

        val suggestions = partyService.suggestOptions(party.id, "   ")

        assertThat(suggestions).isEmpty()
    }

    @Test
    @Transactional
    fun `should respect suggestion limit`() {
        val party = partyRepository.save(
            PartyEntity(
                attendees = listOf("Alice"),
                options = listOf("Catan"),
                status = "NOMINATION",
                code = "AUTO31"
            )
        )
        partyRepository.save(
            PartyEntity(
                attendees = listOf("Bob"),
                options = listOf("Carcassonne", "Caverna", "Canvas"),
                status = "NOMINATION",
                code = "AUTO32"
            )
        )

        val suggestions = partyService.suggestOptions(party.id, "ca", limit = 2)

        assertThat(suggestions).hasSize(2)
    }
}

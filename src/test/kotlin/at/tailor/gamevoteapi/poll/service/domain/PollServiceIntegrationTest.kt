package at.tailor.gamevoteapi.poll.service.domain

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.assertj.core.api.Assertions.assertThat

@ActiveProfiles("test")
@SpringBootTest
class PollServiceIntegrationTest {

    @Autowired
    private lateinit var pollService: PollService

    @Test
    fun `should create poll, cast votes and get deterministic results`() {
        // Given: Create a poll with options and attendees
        val poll = Poll(
            options = setOf("Game A", "Game B", "Game C"),
            attendees = setOf("Alice", "Bob", "Charlie")
        )

        val createdPoll = pollService.create(poll)
        assertThat(createdPoll.id).isNotNull()
        assertThat(createdPoll.status).isEqualTo(Poll.Companion.Status.IN_PROGRESS)
        assertThat(createdPoll.options).containsExactlyInAnyOrder("Game A", "Game B", "Game C")
        assertThat(createdPoll.attendees).containsExactlyInAnyOrder("Alice", "Bob", "Charlie")

        // When: Cast votes from attendees
        // Alice votes: Game A (+1), Game B (0), Game C (-1)
        val aliceVote = pollService.addVote(
            createdPoll.id!!,
            "Alice",
            mapOf("Game A" to 1, "Game B" to 0, "Game C" to -1)
        )
        assertThat(aliceVote).containsEntry("Game A", 1)
        assertThat(aliceVote).containsEntry("Game B", 0)
        assertThat(aliceVote).containsEntry("Game C", -1)

        // Bob votes: Game A (+1), Game B (+1), Game C (0)
        val bobVote = pollService.addVote(
            createdPoll.id!!,
            "Bob",
            mapOf("Game A" to 1, "Game B" to 1, "Game C" to 0)
        )
        assertThat(bobVote).containsEntry("Game A", 1)
        assertThat(bobVote).containsEntry("Game B", 1)
        assertThat(bobVote).containsEntry("Game C", 0)

        // Charlie votes: Game A (-1), Game B (+1), Game C (+1)
        val charlieVote = pollService.addVote(
            createdPoll.id!!,
            "Charlie",
            mapOf("Game A" to -1, "Game B" to 1, "Game C" to 1)
        )
        assertThat(charlieVote).containsEntry("Game A", -1)
        assertThat(charlieVote).containsEntry("Game B", 1)
        assertThat(charlieVote).containsEntry("Game C", 1)

        // Then: Poll should be completed (all attendees voted)
        val completedPoll = pollService.getPoll(createdPoll.id!!)
        assertThat(completedPoll.status).isEqualTo(Poll.Companion.Status.COMPLETED)

        // And: Verify all votes are recorded
        val votes = pollService.getVotes(createdPoll.id!!)
        assertThat(votes).hasSize(3)
        assertThat(votes["Alice"]).isEqualTo(mapOf("Game A" to 1, "Game B" to 0, "Game C" to -1))
        assertThat(votes["Bob"]).isEqualTo(mapOf("Game A" to 1, "Game B" to 1, "Game C" to 0))
        assertThat(votes["Charlie"]).isEqualTo(mapOf("Game A" to -1, "Game B" to 1, "Game C" to 1))

        // And: Results should be correct
        // Game A: 1 + 1 - 1 = 1
        // Game B: 0 + 1 + 1 = 2
        // Game C: -1 + 0 + 1 = 0
        val results = pollService.getResults(createdPoll.id!!)
        assertThat(results).hasSize(3)
        assertThat(results["Game A"]).isEqualTo(1)
        assertThat(results["Game B"]).isEqualTo(2)
        assertThat(results["Game C"]).isEqualTo(0)

        // And: Results should be sorted by score (descending)
        val resultEntries = results.entries.toList()
        assertThat(resultEntries[0].key).isEqualTo("Game B")
        assertThat(resultEntries[0].value).isEqualTo(2)
        assertThat(resultEntries[1].key).isEqualTo("Game A")
        assertThat(resultEntries[1].value).isEqualTo(1)
        assertThat(resultEntries[2].key).isEqualTo("Game C")
        assertThat(resultEntries[2].value).isEqualTo(0)

        // And: Outstanding attendees list should be empty
        val outstanding = pollService.getOutstanding(createdPoll.id!!)
        assertThat(outstanding).isEmpty()
    }

    @Test
    fun `should return random results when options have same score`() {
        // Given: Create a poll with two options
        val poll = Poll(
            options = setOf("Option A", "Option B"),
            attendees = setOf("Voter1", "Voter2")
        )

        val createdPoll = pollService.create(poll)

        // When: Both options receive the same total score
        // Voter1 votes: Option A (+1), Option B (+1)
        pollService.addVote(
            createdPoll.id!!,
            "Voter1",
            mapOf("Option A" to 1, "Option B" to 1)
        )

        // Voter2 votes: Option A (+1), Option B (+1)
        pollService.addVote(
            createdPoll.id!!,
            "Voter2",
            mapOf("Option A" to 1, "Option B" to 1)
        )

        // Then: Results should be deterministic (same order every time)
        // Both options have score of 2, but order should be consistent

        fun getWinner(): String {
            return pollService.getResults(createdPoll.id!!).entries.first().key
        }
        val winners = (0..10).map {
            getWinner()
        }

        assertThat(winners.distinct()).size().isEqualTo(2)
    }

    @Test
    fun `should track outstanding attendees correctly`() {
        // Given: Create a poll with 3 attendees
        val poll = Poll(
            options = setOf("Option X", "Option Y"),
            attendees = setOf("Alice", "Bob", "Charlie")
        )

        val createdPoll = pollService.create(poll)

        // When: Initially, all attendees are outstanding
        val initialOutstanding = pollService.getOutstanding(createdPoll.id!!)
        assertThat(initialOutstanding).containsExactlyInAnyOrder("Alice", "Bob", "Charlie")

        // And: Alice votes
        pollService.addVote(
            createdPoll.id!!,
            "Alice",
            mapOf("Option X" to 1, "Option Y" to 0)
        )

        // Then: Alice should not be in outstanding list
        val afterAliceVote = pollService.getOutstanding(createdPoll.id!!)
        assertThat(afterAliceVote).containsExactlyInAnyOrder("Bob", "Charlie")
        assertThat(afterAliceVote).doesNotContain("Alice")

        // When: Bob votes
        pollService.addVote(
            createdPoll.id!!,
            "Bob",
            mapOf("Option X" to -1, "Option Y" to 1)
        )

        // Then: Only Charlie is outstanding
        val afterBobVote = pollService.getOutstanding(createdPoll.id!!)
        assertThat(afterBobVote).containsExactly("Charlie")

        // And: Poll should still be IN_PROGRESS (not all votes cast)
        val pollInProgress = pollService.getPoll(createdPoll.id!!)
        assertThat(pollInProgress.status).isEqualTo(Poll.Companion.Status.IN_PROGRESS)
    }

    @Test
    fun `should normalize votes with missing options to zero`() {
        // Given: Create a poll with 3 options
        val poll = Poll(
            options = setOf("Game 1", "Game 2", "Game 3"),
            attendees = setOf("Player")
        )

        val createdPoll = pollService.create(poll)

        // When: Player votes for only 2 options (omits Game 3)
        val vote = pollService.addVote(
            createdPoll.id!!,
            "Player",
            mapOf("Game 1" to 1, "Game 2" to -1)
        )

        // Then: The returned vote should include Game 3 with value 0
        assertThat(vote).hasSize(3)
        assertThat(vote).containsEntry("Game 1", 1)
        assertThat(vote).containsEntry("Game 2", -1)
        assertThat(vote).containsEntry("Game 3", 0)

        // And: Verify in stored votes
        val storedVotes = pollService.getVotes(createdPoll.id!!)
        assertThat(storedVotes["Player"]).containsEntry("Game 3", 0)
    }

    @Test
    fun `should calculate results correctly with mix of upvotes and downvotes`() {
        // Given: A poll with 4 attendees
        val poll = Poll(
            options = setOf("Controversial Game", "Popular Game", "Unpopular Game"),
            attendees = setOf("Player1", "Player2", "Player3", "Player4")
        )

        val createdPoll = pollService.create(poll)

        // When: Controversial Game gets mixed votes (2 upvotes, 2 downvotes = 0)
        //       Popular Game gets mostly upvotes (3 upvotes, 1 neutral = 3)
        //       Unpopular Game gets mostly downvotes (1 upvote, 3 downvotes = -2)
        pollService.addVote(createdPoll.id!!, "Player1",
            mapOf("Controversial Game" to 1, "Popular Game" to 1, "Unpopular Game" to -1))
        pollService.addVote(createdPoll.id!!, "Player2",
            mapOf("Controversial Game" to -1, "Popular Game" to 1, "Unpopular Game" to -1))
        pollService.addVote(createdPoll.id!!, "Player3",
            mapOf("Controversial Game" to 1, "Popular Game" to 1, "Unpopular Game" to -1))
        pollService.addVote(createdPoll.id!!, "Player4",
            mapOf("Controversial Game" to -1, "Popular Game" to 0, "Unpopular Game" to 1))

        // Then: Results should reflect the aggregate votes
        val results = pollService.getResults(createdPoll.id!!)

        assertThat(results["Popular Game"]).isEqualTo(3)      // 1+1+1+0 = 3
        assertThat(results["Controversial Game"]).isEqualTo(0) // 1-1+1-1 = 0
        assertThat(results["Unpopular Game"]).isEqualTo(-2)    // -1-1-1+1 = -2

        // And: Should be sorted by score descending
        val sortedKeys = results.keys.toList()
        assertThat(sortedKeys[0]).isEqualTo("Popular Game")
        assertThat(sortedKeys[1]).isEqualTo("Controversial Game")
        assertThat(sortedKeys[2]).isEqualTo("Unpopular Game")
    }
}
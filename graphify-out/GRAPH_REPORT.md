# Graph Report - .  (2026-04-23)

## Corpus Check
- Corpus is ~3,876 words - fits in a single context window. You may not need a graph.

## Summary
- 156 nodes · 201 edges · 14 communities detected
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 18 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Poll Domain Model|Poll Domain Model]]
- [[_COMMUNITY_Party Domain Model|Party Domain Model]]
- [[_COMMUNITY_Party API DTOs|Party API DTOs]]
- [[_COMMUNITY_Poll API Layer|Poll API Layer]]
- [[_COMMUNITY_Beer Timeline Analytics|Beer Timeline Analytics]]
- [[_COMMUNITY_Poll Service Tests|Poll Service Tests]]
- [[_COMMUNITY_Poll Person Models|Poll Person Models]]
- [[_COMMUNITY_Beer Service Tests|Beer Service Tests]]
- [[_COMMUNITY_Spring Boot Smoke Test|Spring Boot Smoke Test]]
- [[_COMMUNITY_Application Bootstrap|Application Bootstrap]]
- [[_COMMUNITY_Poll Conversion|Poll Conversion]]
- [[_COMMUNITY_App Startup Check|App Startup Check]]
- [[_COMMUNITY_Vote Persistence|Vote Persistence]]
- [[_COMMUNITY_Roadmap Tasks|Roadmap Tasks]]

## God Nodes (most connected - your core abstractions)
1. `PartyController` - 26 edges
2. `PartyService` - 24 edges
3. `PollService` - 19 edges
4. `PollController` - 18 edges
5. `BeerService` - 13 edges
6. `PollEntity` - 11 edges
7. `PollService.addVote` - 9 edges
8. `PollRepository` - 8 edges
9. `Poll` - 8 edges
10. `PollConverter.toDomain` - 8 edges

## Surprising Connections (you probably didn't know these)
- `PartyController` --references--> `Poll Results Endpoint`  [INFERRED]
  /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/party/controller/PartyController.kt → src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `Poll Completion And Result Ordering Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `Outstanding Attendee Tracking Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `Missing Vote Normalization Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `PollService.addVote` --calls--> `VoteRepository`  [EXTRACTED]
  src/main/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollService.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/service/persistence/VoteRepository.kt

## Hyperedges (group relationships)
- **Party Management Flow** — partycontroller_partycontroller, partyservice_partyservice, partyconverter_partyconverter, partyrepository_partyrepository, party_party, partyentity_partyentity [INFERRED 0.90]
- **Beer Timeline Summary Flow** — partycontroller_partycontroller, beerservice_beerservice, partyrepository_partyrepository, beerrepository_beerrepository, partyentity_partyentity, beerentity_beerentity [INFERRED 0.88]
- **Poll API Contract Behaviors** — pollcontroller_pollcontroller, pollcontroller_results_endpoint, pollserviceintegrationtest_poll_completion_behavior, pollserviceintegrationtest_outstanding_tracking_behavior, pollserviceintegrationtest_vote_normalization_behavior, pollserviceintegrationtest_result_aggregation_behavior, pollserviceintegrationtest_tie_ordering_expectation [INFERRED 0.77]
- **Poll Lifecycle Flow** — pollservice_create, pollservice_addattendee, pollservice_addvote, pollservice_updatepoll, pollservice_completepoll, pollservice_getresults, pollservice_getoutstanding [INFERRED 0.89]
- **Poll Model Representations** — polldto_polldto, poll_poll, pollentity_pollentity [INFERRED 0.91]

## Communities

### Community 0 - "Poll Domain Model"
Cohesion: 0.12
Nodes (17): Poll, Poll Status, Status, PollConverter.toDomain, PollDTO, PollEntity, PollRepository, PollService.addAttendee (+9 more)

### Community 1 - "Party Domain Model"
Cohesion: 0.08
Nodes (7): BeerEntity, Party, PartyConverter, PartyDTO, PartyEntity, PartyService, PartyStatus

### Community 2 - "Party API DTOs"
Cohesion: 0.09
Nodes (6): Beer, BeerDTO, PartyController, PatchPartyDTO, PatchPartyRequest, StringValue

### Community 3 - "Poll API Layer"
Cohesion: 0.1
Nodes (8): ContextLink, PollController, Poll Results Endpoint, Outstanding Attendee Tracking Behavior, Poll Completion And Result Ordering Behavior, Mixed Vote Aggregation Behavior, Tie Ordering Expectation, Missing Vote Normalization Behavior

### Community 4 - "Beer Timeline Analytics"
Cohesion: 0.13
Nodes (6): BeerRepository, BeerService, BeerTimeline, Cumulative Summary Flatline Behavior, Hourly Summary Zero Gap Behavior, PartyRepository

### Community 5 - "Poll Service Tests"
Cohesion: 0.29
Nodes (1): PollServiceIntegrationTest

### Community 6 - "Poll Person Models"
Cohesion: 0.33
Nodes (3): Person, PersonDTO, PersonInPollDTO

### Community 7 - "Beer Service Tests"
Cohesion: 0.4
Nodes (1): BeerServiceIntegrationTest

### Community 8 - "Spring Boot Smoke Test"
Cohesion: 0.67
Nodes (1): GamevoteApiApplicationTests

### Community 9 - "Application Bootstrap"
Cohesion: 0.67
Nodes (1): GamevoteApiApplication

### Community 10 - "Poll Conversion"
Cohesion: 0.67
Nodes (1): PollConverter

### Community 11 - "App Startup Check"
Cohesion: 1.0
Nodes (2): GamevoteApiApplication, Application Context Load Test

### Community 12 - "Vote Persistence"
Cohesion: 1.0
Nodes (1): VoteRepository

### Community 13 - "Roadmap Tasks"
Cohesion: 1.0
Nodes (2): Docker Build Simplification, PostgreSQL Dependency Migration Task

## Ambiguous Edges - Review These
- `Poll Results Endpoint` → `Tie Ordering Expectation`  [AMBIGUOUS]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt · relation: rationale_for

## Knowledge Gaps
- **10 isolated node(s):** `GamevoteApiApplication`, `BeerTimeline`, `Status`, `Tie Ordering Expectation`, `Outstanding Attendee Tracking Behavior` (+5 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **Thin community `Poll Service Tests`** (7 nodes): `PollServiceIntegrationTest`, `.`should calculate results correctly with mix of upvotes and downvotes`()`, `.`should create poll, cast votes and get deterministic results`()`, `.`should normalize votes with missing options to zero`()`, `.`should return random results when options have same score`()`, `.`should track outstanding attendees correctly`()`, `PollServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Beer Service Tests`** (5 nodes): `BeerServiceIntegrationTest`, `.hour()`, `.`should create cumulative hourly summary with flat lines for hours without new beers`()`, `.`should create hourly summary with explicit zero-value gaps for every attendee`()`, `BeerServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Spring Boot Smoke Test`** (3 nodes): `GamevoteApiApplicationTests`, `.contextLoads()`, `GamevoteApiApplicationTests.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Application Bootstrap`** (3 nodes): `GamevoteApiApplication`, `main()`, `GamevoteApiApplication.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Poll Conversion`** (3 nodes): `PollConverter.kt`, `PollConverter`, `.toDomain()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `App Startup Check`** (2 nodes): `GamevoteApiApplication`, `Application Context Load Test`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Vote Persistence`** (2 nodes): `VoteRepository.kt`, `VoteRepository`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Roadmap Tasks`** (2 nodes): `Docker Build Simplification`, `PostgreSQL Dependency Migration Task`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What is the exact relationship between `Poll Results Endpoint` and `Tie Ordering Expectation`?**
  _Edge tagged AMBIGUOUS (relation: rationale_for) - confidence is low._
- **Why does `PartyService` connect `Party Domain Model` to `Party API DTOs`, `Poll API Layer`, `Beer Timeline Analytics`?**
  _High betweenness centrality (0.171) - this node is a cross-community bridge._
- **Why does `PartyController` connect `Party API DTOs` to `Party Domain Model`, `Poll API Layer`, `Beer Timeline Analytics`?**
  _High betweenness centrality (0.157) - this node is a cross-community bridge._
- **Why does `PollController` connect `Poll API Layer` to `Party Domain Model`?**
  _High betweenness centrality (0.107) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `PartyService` (e.g. with `PartyConverter` and `PollController`) actually correct?**
  _`PartyService` has 2 INFERRED edges - model-reasoned connections that need verification._
- **Are the 4 inferred relationships involving `PollController` (e.g. with `PartyService` and `Poll Completion And Result Ordering Behavior`) actually correct?**
  _`PollController` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `GamevoteApiApplication`, `BeerTimeline`, `Status` to the rest of the system?**
  _10 weakly-connected nodes found - possible documentation gaps or missing edges._
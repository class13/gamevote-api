# Graph Report - gamevote-api  (2026-09-19)

## Corpus Check
- 35 files · ~4,560 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 105 nodes · 127 edges · 18 communities detected
- Extraction: 85% EXTRACTED · 14% INFERRED · 1% AMBIGUOUS · INFERRED: 18 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]
- [[_COMMUNITY_Community 14|Community 14]]
- [[_COMMUNITY_Community 15|Community 15]]
- [[_COMMUNITY_Community 16|Community 16]]
- [[_COMMUNITY_Community 17|Community 17]]

## God Nodes (most connected - your core abstractions)
1. `PartyController` - 17 edges
2. `PartyService` - 16 edges
3. `PollEntity` - 10 edges
4. `BeerService` - 9 edges
5. `PollService.addVote` - 9 edges
6. `PollConverter.toDomain` - 8 edges
7. `PollService` - 8 edges
8. `PollRepository` - 7 edges
9. `Poll` - 7 edges
10. `PollService.updatePoll` - 7 edges

## Surprising Connections (you probably didn't know these)
- `PollDTO` --semantically_similar_to--> `Poll`  [INFERRED] [semantically similar]
  src/main/kotlin/at/tailor/gamevoteapi/poll/controller/data/PollDTO.kt → src/main/kotlin/at/tailor/gamevoteapi/poll/service/domain/Poll.kt
- `PollController` --rationale_for--> `Poll Completion And Result Ordering Behavior`  [INFERRED]
  src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt → src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt
- `Poll Results Endpoint` --rationale_for--> `Tie Ordering Expectation`  [AMBIGUOUS]
  src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt → src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt
- `PollController` --rationale_for--> `Outstanding Attendee Tracking Behavior`  [INFERRED]
  src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt → src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt
- `PollController` --rationale_for--> `Missing Vote Normalization Behavior`  [INFERRED]
  src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt → src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt

## Hyperedges (group relationships)
- **Option Suggestion Contract** — partycontroller_option_suggestions_endpoint, partyservice_suggestoptions, partyserviceintegrationtest_option_suggestion_order_behavior, partyserviceintegrationtest_blank_query_behavior, partyserviceintegrationtest_suggestion_limit_behavior [INFERRED 0.94]
- **Party Status Transition API** — partycontroller_patch_party_endpoint, partyservice_patchparty, partyservice_allowedtransitions [INFERRED 0.89]
- **Graphify Project Workflow** — agents_graphify_instructions, agents_graphify_knowledge_graph, agents_graphify_update_workflow [EXTRACTED 1.00]

## Communities

### Community 0 - "Community 0"
Cohesion: 0.33
Nodes (18): Poll, Poll Status, PollConverter, PollConverter.toDomain, PollDTO, PollEntity, PollRepository, PollService.addAttendee (+10 more)

### Community 1 - "Community 1"
Cohesion: 0.12
Nodes (1): PartyController

### Community 2 - "Community 2"
Cohesion: 0.13
Nodes (1): PartyService

### Community 3 - "Community 3"
Cohesion: 0.2
Nodes (2): BeerService, BeerTimeline

### Community 4 - "Community 4"
Cohesion: 0.29
Nodes (8): ContextLink, PollController, Poll Results Endpoint, Outstanding Attendee Tracking Behavior, Poll Completion And Result Ordering Behavior, Mixed Vote Aggregation Behavior, Tie Ordering Expectation, Missing Vote Normalization Behavior

### Community 5 - "Community 5"
Cohesion: 0.33
Nodes (1): BeerServiceIntegrationTest

### Community 6 - "Community 6"
Cohesion: 0.4
Nodes (6): BeerEntity, Party, PartyConverter, PartyDTO, PartyEntity, PartyStatus

### Community 7 - "Community 7"
Cohesion: 0.4
Nodes (1): PartyServiceIntegrationTest

### Community 8 - "Community 8"
Cohesion: 0.67
Nodes (2): PromilleAssumptionsDTO, PromilleSummaryDTO

### Community 9 - "Community 9"
Cohesion: 0.67
Nodes (3): Person, PersonDTO, PersonInPollDTO

### Community 10 - "Community 10"
Cohesion: 1.0
Nodes (3): Graphify Instructions, Graphify Knowledge Graph, Graphify Update Workflow

### Community 11 - "Community 11"
Cohesion: 1.0
Nodes (2): GamevoteApiApplication, Application Context Load Test

### Community 12 - "Community 12"
Cohesion: 1.0
Nodes (2): PatchPartyDTO, PatchPartyRequest

### Community 13 - "Community 13"
Cohesion: 1.0
Nodes (2): Beer, BeerDTO

### Community 14 - "Community 14"
Cohesion: 1.0
Nodes (2): Docker Build Simplification, PostgreSQL Dependency Migration Task

### Community 15 - "Community 15"
Cohesion: 1.0
Nodes (1): StringValue

### Community 16 - "Community 16"
Cohesion: 1.0
Nodes (1): BeerRepository

### Community 17 - "Community 17"
Cohesion: 1.0
Nodes (1): PartyRepository

## Ambiguous Edges - Review These
- `Tie Ordering Expectation` → `Poll Results Endpoint`  [AMBIGUOUS]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt · relation: rationale_for

## Knowledge Gaps
- **25 isolated node(s):** `Application Context Load Test`, `Tie Ordering Expectation`, `Outstanding Attendee Tracking Behavior`, `Missing Vote Normalization Behavior`, `GamevoteApiApplication` (+20 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **Thin community `Community 1`** (17 nodes): `PartyController.kt`, `PartyController`, `.createParty()`, `.deleteAttendee()`, `.deleteOption()`, `.getAttendees()`, `.getBeerSummary()`, `.getCumulativeBeerSummary()`, `.getOptions()`, `.getOptionSuggestions()`, `.getParty()`, `.getPromilleSummary()`, `.patchParty()`, `.postAttendee()`, `.postBeer()`, `.postOption()`, `.toDTO()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 2`** (15 nodes): `PartyService.kt`, `PartyService`, `.addAttendee()`, `.addOption()`, `.allowedTransitions()`, `.createCodeForParty()`, `.createParty()`, `.createRandomCode()`, `.deleteAttendee()`, `.deleteOption()`, `.getIdForCode()`, `.getParty()`, `.patchParty()`, `.postBeer()`, `.suggestOptions()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 3`** (10 nodes): `BeerService`, `.buildTimeline()`, `.createCumulativeHourlySummary()`, `.createHourlySummary()`, `.createPromilleSummary()`, `.estimatePromille()`, `.roundToTwoDecimals()`, `.truncateToHour()`, `BeerTimeline`, `BeerService.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 5`** (6 nodes): `BeerServiceIntegrationTest`, `.hour()`, `.`should create cumulative hourly summary with flat lines for hours without new beers`()`, `.`should create hourly summary with explicit zero-value gaps for every attendee`()`, `.`should estimate promille for each attendee at each hour`()`, `BeerServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 7`** (5 nodes): `PartyServiceIntegrationTest`, `.`should respect suggestion limit`()`, `.`should return empty suggestions for blank query`()`, `.`should suggest matching options from current and previous parties with prefix matches first`()`, `PartyServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 8`** (3 nodes): `PromilleSummaryDTO.kt`, `PromilleAssumptionsDTO`, `PromilleSummaryDTO`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 11`** (2 nodes): `GamevoteApiApplication`, `Application Context Load Test`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 12`** (2 nodes): `PatchPartyDTO`, `PatchPartyRequest`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 13`** (2 nodes): `Beer`, `BeerDTO`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 14`** (2 nodes): `Docker Build Simplification`, `PostgreSQL Dependency Migration Task`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 15`** (1 nodes): `StringValue`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 16`** (1 nodes): `BeerRepository`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 17`** (1 nodes): `PartyRepository`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What is the exact relationship between `Tie Ordering Expectation` and `Poll Results Endpoint`?**
  _Edge tagged AMBIGUOUS (relation: rationale_for) - confidence is low._
- **Why does `PartyService` connect `Community 2` to `Community 4`, `Community 6`?**
  _High betweenness centrality (0.199) - this node is a cross-community bridge._
- **Why does `PollController` connect `Community 4` to `Community 2`?**
  _High betweenness centrality (0.153) - this node is a cross-community bridge._
- **Why does `PartyController` connect `Community 1` to `Community 4`?**
  _High betweenness centrality (0.139) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `PartyService` (e.g. with `PartyConverter` and `PollController`) actually correct?**
  _`PartyService` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `Application Context Load Test`, `Tie Ordering Expectation`, `Outstanding Attendee Tracking Behavior` to the rest of the system?**
  _25 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.12 - nodes in this community are weakly interconnected._
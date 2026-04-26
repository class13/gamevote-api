# Graph Report - .  (2026-04-26)

## Corpus Check
- Corpus is ~4,275 words - fits in a single context window. You may not need a graph.

## Summary
- 181 nodes · 234 edges · 15 communities detected
- Extraction: 89% EXTRACTED · 10% INFERRED · 0% AMBIGUOUS · INFERRED: 24 edges (avg confidence: 0.86)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Poll Domain Model|Poll Domain Model]]
- [[_COMMUNITY_Party Domain Flow|Party Domain Flow]]
- [[_COMMUNITY_Party API DTOs|Party API DTOs]]
- [[_COMMUNITY_Poll API Layer|Poll API Layer]]
- [[_COMMUNITY_Beer Timeline Analytics|Beer Timeline Analytics]]
- [[_COMMUNITY_Option Suggestion Flow|Option Suggestion Flow]]
- [[_COMMUNITY_Poll Service Tests|Poll Service Tests]]
- [[_COMMUNITY_Poll Person Models|Poll Person Models]]
- [[_COMMUNITY_Beer Timeline Tests|Beer Timeline Tests]]
- [[_COMMUNITY_Spring Boot Test|Spring Boot Test]]
- [[_COMMUNITY_Application Bootstrap|Application Bootstrap]]
- [[_COMMUNITY_Poll Mapping|Poll Mapping]]
- [[_COMMUNITY_Graphify Workflow|Graphify Workflow]]
- [[_COMMUNITY_Context Load Check|Context Load Check]]
- [[_COMMUNITY_Build Migration Tasks|Build Migration Tasks]]

## God Nodes (most connected - your core abstractions)
1. `PartyController` - 32 edges
2. `PartyService` - 31 edges
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
- `PartyController` --references--> `Create Party Endpoint`  [EXTRACTED]
  /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/party/controller/PartyController.kt → src/main/kotlin/at/tailor/gamevoteapi/party/controller/PartyController.kt
- `Poll Completion And Result Ordering Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `Outstanding Attendee Tracking Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt
- `Missing Vote Normalization Behavior` --rationale_for--> `PollController`  [INFERRED]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt → /Users/lucky/Desktop/Development/IdeaProjects/gamevote-api/src/main/kotlin/at/tailor/gamevoteapi/poll/controller/PollController.kt

## Hyperedges (group relationships)
- **Option Suggestion Contract** — partycontroller_option_suggestions_endpoint, partyservice_suggestoptions, partyserviceintegrationtest_option_suggestion_order_behavior, partyserviceintegrationtest_blank_query_behavior, partyserviceintegrationtest_suggestion_limit_behavior [INFERRED 0.94]
- **Party Status Transition API** — partycontroller_patch_party_endpoint, partyservice_patchparty, partyservice_allowedtransitions [INFERRED 0.89]
- **Graphify Project Workflow** — agents_graphify_instructions, agents_graphify_knowledge_graph, agents_graphify_update_workflow [EXTRACTED 1.00]

## Communities

### Community 0 - "Poll Domain Model"
Cohesion: 0.11
Nodes (18): Poll, Poll Status, Status, PollConverter.toDomain, PollDTO, PollEntity, PollRepository, PollService.addAttendee (+10 more)

### Community 1 - "Party Domain Flow"
Cohesion: 0.07
Nodes (11): BeerEntity, Party, Create Party Endpoint, PartyConverter, PartyDTO, PartyEntity, PartyService.addAttendee, PartyService.createParty (+3 more)

### Community 2 - "Party API DTOs"
Cohesion: 0.07
Nodes (12): Beer, BeerDTO, Beer Summary Endpoint, Cumulative Beer Summary Endpoint, PartyController, Patch Party Endpoint, PartyService.allowedTransitions, PartyService.getIdForCode (+4 more)

### Community 3 - "Poll API Layer"
Cohesion: 0.1
Nodes (8): ContextLink, PollController, Poll Results Endpoint, Outstanding Attendee Tracking Behavior, Poll Completion And Result Ordering Behavior, Mixed Vote Aggregation Behavior, Tie Ordering Expectation, Missing Vote Normalization Behavior

### Community 4 - "Beer Timeline Analytics"
Cohesion: 0.13
Nodes (6): BeerRepository, BeerService, BeerTimeline, Cumulative Summary Flatline Behavior, Hourly Summary Zero Gap Behavior, PartyRepository

### Community 5 - "Option Suggestion Flow"
Cohesion: 0.27
Nodes (6): Option Suggestions Endpoint, PartyService.suggestOptions, Blank Query Suggestion Behavior, Option Suggestion Ordering Behavior, PartyServiceIntegrationTest, Suggestion Limit Behavior

### Community 6 - "Poll Service Tests"
Cohesion: 0.29
Nodes (1): PollServiceIntegrationTest

### Community 7 - "Poll Person Models"
Cohesion: 0.33
Nodes (3): Person, PersonDTO, PersonInPollDTO

### Community 8 - "Beer Timeline Tests"
Cohesion: 0.4
Nodes (1): BeerServiceIntegrationTest

### Community 9 - "Spring Boot Test"
Cohesion: 0.67
Nodes (1): GamevoteApiApplicationTests

### Community 10 - "Application Bootstrap"
Cohesion: 0.67
Nodes (1): GamevoteApiApplication

### Community 11 - "Poll Mapping"
Cohesion: 0.67
Nodes (1): PollConverter

### Community 12 - "Graphify Workflow"
Cohesion: 1.0
Nodes (3): Graphify Instructions, Graphify Knowledge Graph, Graphify Update Workflow

### Community 13 - "Context Load Check"
Cohesion: 1.0
Nodes (2): GamevoteApiApplication, Application Context Load Test

### Community 14 - "Build Migration Tasks"
Cohesion: 1.0
Nodes (2): Docker Build Simplification, PostgreSQL Dependency Migration Task

## Ambiguous Edges - Review These
- `Poll Results Endpoint` → `Tie Ordering Expectation`  [AMBIGUOUS]
  src/test/kotlin/at/tailor/gamevoteapi/poll/service/domain/PollServiceIntegrationTest.kt · relation: rationale_for

## Knowledge Gaps
- **13 isolated node(s):** `GamevoteApiApplication`, `BeerTimeline`, `Status`, `Tie Ordering Expectation`, `Outstanding Attendee Tracking Behavior` (+8 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **Thin community `Poll Service Tests`** (7 nodes): `PollServiceIntegrationTest`, `.`should calculate results correctly with mix of upvotes and downvotes`()`, `.`should create poll, cast votes and get deterministic results`()`, `.`should normalize votes with missing options to zero`()`, `.`should return random results when options have same score`()`, `.`should track outstanding attendees correctly`()`, `PollServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Beer Timeline Tests`** (5 nodes): `BeerServiceIntegrationTest`, `.hour()`, `.`should create cumulative hourly summary with flat lines for hours without new beers`()`, `.`should create hourly summary with explicit zero-value gaps for every attendee`()`, `BeerServiceIntegrationTest.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Spring Boot Test`** (3 nodes): `GamevoteApiApplicationTests`, `.contextLoads()`, `GamevoteApiApplicationTests.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Application Bootstrap`** (3 nodes): `GamevoteApiApplication`, `main()`, `GamevoteApiApplication.kt`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Poll Mapping`** (3 nodes): `PollConverter.kt`, `PollConverter`, `.toDomain()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Context Load Check`** (2 nodes): `GamevoteApiApplication`, `Application Context Load Test`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Build Migration Tasks`** (2 nodes): `Docker Build Simplification`, `PostgreSQL Dependency Migration Task`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What is the exact relationship between `Poll Results Endpoint` and `Tie Ordering Expectation`?**
  _Edge tagged AMBIGUOUS (relation: rationale_for) - confidence is low._
- **Why does `PartyService` connect `Party Domain Flow` to `Party API DTOs`, `Poll API Layer`, `Beer Timeline Analytics`, `Option Suggestion Flow`?**
  _High betweenness centrality (0.213) - this node is a cross-community bridge._
- **Why does `PartyController` connect `Party API DTOs` to `Party Domain Flow`, `Poll API Layer`, `Beer Timeline Analytics`, `Option Suggestion Flow`?**
  _High betweenness centrality (0.179) - this node is a cross-community bridge._
- **Why does `PollController` connect `Poll API Layer` to `Party Domain Flow`?**
  _High betweenness centrality (0.101) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `PartyService` (e.g. with `PartyConverter` and `PollController`) actually correct?**
  _`PartyService` has 2 INFERRED edges - model-reasoned connections that need verification._
- **Are the 4 inferred relationships involving `PollController` (e.g. with `PartyService` and `Poll Completion And Result Ordering Behavior`) actually correct?**
  _`PollController` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `GamevoteApiApplication`, `BeerTimeline`, `Status` to the rest of the system?**
  _13 weakly-connected nodes found - possible documentation gaps or missing edges._
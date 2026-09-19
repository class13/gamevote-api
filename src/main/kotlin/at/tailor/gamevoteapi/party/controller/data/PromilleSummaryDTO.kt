package at.tailor.gamevoteapi.party.controller.data

data class PromilleAssumptionsDTO(
    val bodyWeightKg: Double,
    val beerVolumeMl: Int,
    val beerAlcoholByVolume: Double,
    val bodyWaterDistribution: Double,
    val alcoholEliminationPerHour: Double
)

data class PromilleSummaryDTO(
    val assumptions: PromilleAssumptionsDTO,
    val estimates: Map<String, Map<java.time.LocalDateTime, Double>>
)

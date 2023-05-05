package com.fabiel.casas.simulator.model.table

import java.util.UUID

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
/**
 * Information data relate with a Team.
 * all probabilistic information set in the team(Int values) like: attach, defense, possession, recovery
 * mush be set from values from 0 to 100. This due to the fact is a probabilistic value.
 *
 * @property id
 * @property name
 * @property attack values from 0 to 10
 * @property defense values from 0 to 50
 * @property possession values from 0 to 100
 * @property recovery values from 0 to 100
 * @constructor Create empty Team
 */
data class Team(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val attack: Int,
    val defense: Int,
    val possession: Int,
    val recovery: Int,
)

package br.com.calculaflex.domain.entity

import br.com.calculaflex.domain.entity.enums.FeatureToggleState

data class DashboardMenu(
    val title: String,
    val subTitle: String,
    val items: List<DashboardItem>
)

data class DashboardItem(
    val feature: String,
    val image: String,
    val label: String,
    val status: FeatureToggleState,
    val action: DashboardAction,
    var onDisabledListener: (() -> Unit)?
)

data class DashboardAction(
    val deeplink: String,
    val params: HashMap<String, Any>
)

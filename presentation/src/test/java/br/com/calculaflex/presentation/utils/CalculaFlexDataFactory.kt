package br.com.calculaflex.presentation.utils

import br.com.calculaflex.domain.entity.DashboardAction
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.NewUser
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.enums.FeatureToggleState

object CalculaFlexDataFactory {

    fun createUser() =
        User(
            id = "id",
            name = "Heider",
            email = "heider@fiap.com",
            phone = "11912345678"
        )

    fun createNewUser() =
        NewUser(
            name = "Heider",
            email = "heider@fiap.com",
            phone = "11912345678",
            password = "password"
        )

    fun getDashboardMenu() =
        DashboardMenu(
            title = "title",
            subTitle = "subtitle",
            items = getDashboardItems()
        )

    fun getDashboardItems() =
        listOf(
            DashboardItem(
                feature = "feature 1",
                image = "https://cdn.icon-icons.com/icons2/1286/PNG/512/49_85223.png",
                status = FeatureToggleState.ENABLED,
                action = DashboardAction("://feature1", hashMapOf()),
                label = "Feature1",
                onDisabledListener = null
            ),
            DashboardItem(
                feature = "feature 2",
                image = "https://cdn.icon-icons.com/icons2/1286/PNG/512/49_85223.png",
                status = FeatureToggleState.ENABLED,
                action = DashboardAction("://feature2", hashMapOf()),
                label = "Feature2",
                onDisabledListener = null
            ),
            DashboardItem(
                feature = "feature 3",
                image = "https://cdn.icon-icons.com/icons2/1286/PNG/512/49_85223.png",
                status = FeatureToggleState.ENABLED,
                action = DashboardAction("://feature3", hashMapOf()),
                label = "Feature3",
                onDisabledListener = null
            )
        )
}
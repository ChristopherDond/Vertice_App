package com.vertice.app.nav

/**
 * Navigation routes for the Vertice app.
 * Main tab screens use the Screen enum values as routes.
 * Modal screens use dedicated route strings.
 */
object NavRoutes {
    // Main tab destinations (match Screen enum)
    const val HOME = "home"
    const val MATCH = "match"
    const val VIOLETA = "violeta"
    const val PERFIL = "perfil"
    const val CONFIRMACAO = "confirmacao"

    // Modal/Dialog destinations
    const val CONTACT_MODAL = "contact_modal"
    const val PROFILE_MODAL = "profile_modal"
    const val TRILHA_MODAL = "trilha_modal"
    const val EDIT_PROFILE_MODAL = "edit_profile_modal"
    const val OFFER_MODAL = "offer_modal"
    const val PRO_MODAL = "pro_modal"

    // Contact/Profile modal with freelancer name argument
    const val CONTACT_MODAL_WITH_ARG = "contact_modal/{freelancerName}"
    const val PROFILE_MODAL_WITH_ARG = "profile_modal/{freelancerName}"
}
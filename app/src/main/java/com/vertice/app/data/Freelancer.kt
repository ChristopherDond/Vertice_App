package com.vertice.app.data

import androidx.compose.ui.graphics.Color

data class Freelancer(
    val initials: String,
    val name: String,
    val role: String,
    val area: String,
    val match: Int,
    val bg: Color,
    val avail: String,
    val rating: Double,
    val jobs: Int,
    val bio: String,
    val gender: String, // "m" | "f" — usado pelo filtro do Protocolo Violeta
)

val FREELANCERS = listOf(
    Freelancer("CM", "Carlos Mendes", "Pedreiro", "Construção Civil", 94, Color(0xFF1E4ED8), "Disponível agora", 4.9, 48, "Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.", "m"),
    Freelancer("LF", "Luísa Fonseca", "Lojista", "Comércio & Varejo", 87, Color(0xFF0E7490), "Disponível amanhã", 4.7, 31, "Gestão de loja de moda feminina, vitrinismo e controle de estoque.", "f"),
    Freelancer("BC", "Beatriz Costa", "Eletricista", "Instalações Elétr.", 91, Color(0xFF7C2D8E), "Disponível agora", 4.8, 62, "Instalações elétricas residenciais e comerciais. Certificada pelo CREA.", "f"),
    Freelancer("RO", "Regina Oliveira", "Pintora", "Pintura Residencial", 83, Color(0xFFB45309), "Disponível esta semana", 4.6, 27, "Pintura interna e externa, texturas e efeitos decorativos. Atuo em toda São Paulo.", "f"),
    Freelancer("JP", "João Pereira", "Encanador", "Serviços Hidráulicos", 79, Color(0xFF065F46), "Disponível amanhã", 4.5, 39, "Reparos e instalações hidráulicas, desentupimentos e manutenção preventiva.", "m"),
    Freelancer("MS", "Marina Souza", "Limpeza", "Serviços Domésticos", 88, Color(0xFF9D174D), "Disponível agora", 4.9, 84, "Limpeza residencial e comercial, organização de ambientes e pós-obra.", "f"),
)

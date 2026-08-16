package com.vertice.app.data

import androidx.compose.ui.graphics.Color
import com.vertice.app.R

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
    val gender: String,
    val photoRes: Int? = null,
    val photoUrl: String? = null,
)

val FREELANCERS = listOf(
    Freelancer(
        initials = "CM", name = "Carlos Mendes", role = "Pedreiro", area = "Construção Civil", match = 94,
        bg = Color(0xFF1E4ED8), avail = "Disponível agora", rating = 4.9, jobs = 48,
        bio = "Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.",
        gender = "m", photoRes = R.drawable.foto_carlos,
    ),
    Freelancer(
        initials = "LF", name = "Luísa Fonseca", role = "Lojista", area = "Comércio & Varejo", match = 87,
        bg = Color(0xFF0E7490), avail = "Disponível amanhã", rating = 4.7, jobs = 31,
        bio = "Gestão de loja de moda feminina, vitrinismo e controle de estoque.",
        gender = "f", photoRes = R.drawable.foto_luisa,
    ),
    Freelancer(
        initials = "BC", name = "Beatriz Costa", role = "Eletricista", area = "Instalações Elétr.", match = 91,
        bg = Color(0xFF7C2D8E), avail = "Disponível agora", rating = 4.8, jobs = 62,
        bio = "Instalações elétricas residenciais e comerciais. Certificada pelo CREA.",
        gender = "f", photoRes = R.drawable.foto_beatriz,
    ),
    Freelancer(
        initials = "RO", name = "Regina Oliveira", role = "Pintora", area = "Pintura Residencial", match = 83,
        bg = Color(0xFFB45309), avail = "Disponível esta semana", rating = 4.6, jobs = 27,
        bio = "Pintura interna e externa, texturas e efeitos decorativos. Atuo em toda São Paulo.",
        gender = "f", photoRes = R.drawable.foto_regina,
    ),
    Freelancer(
        initials = "JP", name = "João Pereira", role = "Encanador", area = "Serviços Hidráulicos", match = 79,
        bg = Color(0xFF065F46), avail = "Disponível amanhã", rating = 4.5, jobs = 39,
        bio = "Reparos e instalações hidráulicas, desentupimentos e manutenção preventiva.",
        gender = "m", photoRes = R.drawable.foto_joao,
    ),
    Freelancer(
        initials = "MS", name = "Marina Souza", role = "Limpeza", area = "Serviços Domésticos", match = 88,
        bg = Color(0xFF9D174D), avail = "Disponível agora", rating = 4.9, jobs = 84,
        bio = "Limpeza residencial e comercial, organização de ambientes e pós-obra.",
        gender = "f", photoRes = R.drawable.foto_marina,
    ),
    Freelancer(
        initials = "AS", name = "Ana Silva", role = "Cabeleireira", area = "Beleza", match = 92,
        bg = Color(0xFFEC4899), avail = "Disponível agora", rating = 4.8, jobs = 56,
        bio = "Especialista em cortes, coloração e tratamentos capilares. Atendimento domiciliar e em salão. 8 anos transformando looks.",
        gender = "f", photoRes = R.drawable.foto_ana,
    ),
    Freelancer(
        initials = "RO", name = "Roberto Oliveira", role = "Chef Particular", area = "Alimentação", match = 89,
        bg = Color(0xFFF97316), avail = "Disponível amanhã", rating = 4.9, jobs = 42,
        bio = "Chef formado em gastronomia, cardápios personalizados para eventos e jantares íntimos. Cozinha brasileira e mediterrânea.",
        gender = "m", photoRes = R.drawable.foto_roberto,
    ),
    Freelancer(
        initials = "CF", name = "Camila Ferreira", role = "Consultora Financeira", area = "Consultoria", match = 95,
        bg = Color(0xFF059669), avail = "Disponível esta semana", rating = 5.0, jobs = 38,
        bio = "Planejamento financeiro pessoal e para pequenos negócios. Certificada CFP. Ajuda a organizar finanças e investir com segurança.",
        gender = "f", photoRes = R.drawable.foto_camila,
    ),
    Freelancer(
        initials = "DS", name = "Diego Santos", role = "Faxineiro Profissional", area = "Limpeza", match = 86,
        bg = Color(0xFF0891B2), avail = "Disponível agora", rating = 4.7, jobs = 71,
        bio = "Limpeza pesada, pós-obra, escritórios e residências. Equipamentos profissionais e produtos ecológicos. Pontual e detalhista.",
        gender = "m", photoRes = R.drawable.foto_diego,
    ),
    Freelancer(
        initials = "AP", name = "André Pereira", role = "Mestre de Obras", area = "Construção", match = 93,
        bg = Color(0xFF78350F), avail = "Disponível amanhã", rating = 4.8, jobs = 54,
        bio = "Gerenciamento completo de obras residenciais e comerciais. Da fundação ao acabamento. 15 anos de canteiro de obras.",
        gender = "m", photoRes = R.drawable.foto_andre,
    ),
    Freelancer(
        initials = "JR", name = "Juliana Ribeiro", role = "Personal Organizer", area = "Serviços", match = 90,
        bg = Color(0xFF9333EA), avail = "Disponível esta semana", rating = 4.9, jobs = 29,
        bio = "Organização de residências, mudanças e home offices. Método próprio para otimizar espaços e rotinas. Certificada NAPO.",
        gender = "f", photoRes = R.drawable.foto_juliana,
    ),
)

val FREELANCERS_SORTED = FREELANCERS.sortedByDescending { it.match }
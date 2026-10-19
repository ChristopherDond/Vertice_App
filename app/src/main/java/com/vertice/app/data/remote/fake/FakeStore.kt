package com.vertice.app.data.remote.fake

import com.vertice.app.data.remote.dto.AddressDto
import com.vertice.app.data.remote.dto.BalanceDto
import com.vertice.app.data.remote.dto.CardDto
import com.vertice.app.data.remote.dto.FreelancerDto
import com.vertice.app.data.remote.dto.LessonCardDto
import com.vertice.app.data.remote.dto.LessonDto
import com.vertice.app.data.remote.dto.ModuleDto
import com.vertice.app.data.remote.dto.PlanDto
import com.vertice.app.data.remote.dto.SubscriptionDto
import com.vertice.app.data.remote.dto.TransactionDto
import com.vertice.app.data.remote.dto.UserDto
import com.vertice.app.data.remote.dto.VioletaStatusDto

object FakeStore {

    var currentUser: UserDto = UserDto(
        id = "usr_ana",
        name = "Ana Silva",
        email = "ana.silva@vertice.app",
        phone = "(11) 98159-5963",
        cpfMasked = "***.***.***-28",
        bio = "Costureira e confeccionista. Peças sob medida, ajustes e reformas. Atendo em domicílio na zona sul de São Paulo.",
        skills = listOf("Costura", "Confecção", "Manejo"),
        address = AddressDto(
            cep = "06760-000",
            street = "Rua José",
            number = "182",
            district = "Jardim Maria Rosa",
            city = "Taboão da Serra",
            state = "SP",
        ),
        isPro = false,
        violetaVerified = true,
        profileCompletion = 70,
        rating = 4.8,
        completedServices = 56,
    )

    const val DEMO_PASSWORD = "senha123"
    const val DEMO_EMAIL = "ana.silva@vertice.app"
    const val DEMO_CPF = "52998224725"

    val freelancers: List<FreelancerDto> = listOf(
        FreelancerDto(
            id = "flw_camila", name = "Camila Ferreira", role = "Consultora Financeira", area = "Consultoria",
            category = "CONSULTING", matchPercent = 93, rating = 5.0, servicesCount = 38,
            availability = "Disponível esta semana",
            bio = "Planejamento financeiro pessoal e para pequenos negócios. Certificada CFP. Ajuda a organizar finanças e investir com segurança.",
            gender = "FEMALE", hoursWorked = 1240, jobsDone = 38, isVioleta = true, isPro = true,
            skills = listOf("Planejamento", "Investimentos", "CFP"), hourlyRate = 180.0,
        ),
        FreelancerDto(
            id = "flw_carlos", name = "Carlos Mendes", role = "Pedreiro", area = "Construção Civil",
            category = "CONSTRUCTION", matchPercent = 94, rating = 4.9, servicesCount = 48,
            availability = "Disponível agora",
            bio = "Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.",
            gender = "MALE", hoursWorked = 2880, jobsDone = 48, isPro = true,
            skills = listOf("Reforma", "Hidráulica", "Acabamento"), hourlyRate = 95.0,
        ),
        FreelancerDto(
            id = "flw_andre", name = "André Pereira", role = "Mestre de Obras", area = "Construção Civil",
            category = "CONSTRUCTION", matchPercent = 93, rating = 4.8, servicesCount = 31,
            availability = "Disponível amanhã",
            bio = "Gerenciamento completo de obras residenciais e comerciais. Da fundação ao acabamento. 15 anos de canteiro de obras.",
            gender = "MALE", hoursWorked = 3600, jobsDone = 31,
            skills = listOf("Gestão de obra", "Cronograma", "Orçamento"), hourlyRate = 120.0,
        ),
        FreelancerDto(
            id = "flw_luisa", name = "Luísa Fonseca", role = "Lojista", area = "Comércio & Varejo",
            category = "COMMERCE", matchPercent = 87, rating = 4.7, servicesCount = 31,
            availability = "Disponível amanhã",
            bio = "Gestão de loja de moda feminina, vitrinismo e controle de estoque.",
            gender = "FEMALE", hoursWorked = 890, jobsDone = 31, isVioleta = true,
            skills = listOf("Vitrinismo", "Estoque", "Vendas"), hourlyRate = 80.0,
        ),
        FreelancerDto(
            id = "flw_beatriz", name = "Beatriz Costa", role = "Eletricista", area = "Instalações Elétricas",
            category = "CONSTRUCTION", matchPercent = 91, rating = 4.8, servicesCount = 62,
            availability = "Disponível agora",
            bio = "Instalações elétricas residenciais e comerciais. Certificada pelo CREA.",
            gender = "FEMALE", hoursWorked = 3100, jobsDone = 62, isVioleta = true, isPro = true,
            skills = listOf("Elétrica", "CREA", "Manutenção"), hourlyRate = 110.0,
        ),
        FreelancerDto(
            id = "flw_regina", name = "Regina Oliveira", role = "Pintora", area = "Pintura Residencial",
            category = "CONSTRUCTION", matchPercent = 83, rating = 4.6, servicesCount = 27,
            availability = "Disponível esta semana",
            bio = "Pintura interna e externa, texturas e efeitos decorativos. Atuo em toda São Paulo.",
            gender = "FEMALE", hoursWorked = 980, jobsDone = 27, isVioleta = true,
            skills = listOf("Pintura", "Textura", "Efeitos"), hourlyRate = 85.0,
        ),
        FreelancerDto(
            id = "flw_joao", name = "João Pereira", role = "Encanador", area = "Serviços Hidráulicos",
            category = "SERVICES", matchPercent = 79, rating = 4.5, servicesCount = 39,
            availability = "Disponível amanhã",
            bio = "Reparos e instalações hidráulicas, desentupimentos e manutenção preventiva.",
            gender = "MALE", hoursWorked = 1450, jobsDone = 39,
            skills = listOf("Hidráulica", "Desentupimento", "Reparos"), hourlyRate = 90.0,
        ),
        FreelancerDto(
            id = "flw_marina", name = "Marina Souza", role = "Diarista", area = "Serviços Domésticos",
            category = "SERVICES", matchPercent = 88, rating = 4.9, servicesCount = 84,
            availability = "Disponível agora",
            bio = "Limpeza residencial e comercial, organização de ambientes e pós-obra.",
            gender = "FEMALE", hoursWorked = 2100, jobsDone = 84, isVioleta = true,
            skills = listOf("Limpeza", "Organização", "Pós-obra"), hourlyRate = 70.0,
        ),
        FreelancerDto(
            id = "flw_ana", name = "Ana Silva", role = "Cabeleireira", area = "Beleza",
            category = "BEAUTY", matchPercent = 92, rating = 4.8, servicesCount = 56,
            availability = "Disponível agora",
            bio = "Especialista em cortes, coloração e tratamentos capilares. Atendimento domiciliar e em salão. 8 anos transformando looks.",
            gender = "FEMALE", hoursWorked = 1980, jobsDone = 56, isVioleta = true,
            skills = listOf("Corte", "Coloração", "Tratamento"), hourlyRate = 130.0,
        ),
        FreelancerDto(
            id = "flw_roberto", name = "Roberto Oliveira", role = "Chef Particular", area = "Alimentação",
            category = "FOOD", matchPercent = 89, rating = 4.9, servicesCount = 42,
            availability = "Disponível amanhã",
            bio = "Chef formado em gastronomia, cardápios personalizados para eventos e jantares íntimos. Cozinha brasileira e mediterrânea.",
            gender = "MALE", hoursWorked = 1620, jobsDone = 42,
            skills = listOf("Eventos", "Menu", "Gastronomia"), hourlyRate = 200.0,
        ),
        FreelancerDto(
            id = "flw_diego", name = "Diego Santos", role = "Faxineiro Profissional", area = "Limpeza",
            category = "SERVICES", matchPercent = 86, rating = 4.7, servicesCount = 71,
            availability = "Disponível agora",
            bio = "Limpeza pesada, pós-obra, escritórios e residências. Equipamentos profissionais e produtos ecológicos.",
            gender = "MALE", hoursWorked = 2290, jobsDone = 71,
            skills = listOf("Pesada", "Ecológico", "Escritórios"), hourlyRate = 75.0,
        ),
        FreelancerDto(
            id = "flw_juliana", name = "Juliana Ribeiro", role = "Personal Organizer", area = "Serviços",
            category = "SERVICES", matchPercent = 90, rating = 4.9, servicesCount = 29,
            availability = "Disponível esta semana",
            bio = "Organização de residências, mudanças e home offices. Método próprio para otimizar espaços e rotinas. Certificada NAPO.",
            gender = "FEMALE", hoursWorked = 720, jobsDone = 29, isVioleta = true,
            skills = listOf("Organização", "Mudanças", "NAPO"), hourlyRate = 100.0,
        ),
    )

    var balance: BalanceDto = BalanceDto(available = 570.00, pending = 180.00)

    val transactions: List<TransactionDto> = listOf(
        TransactionDto("txn_1", "Flavio Bol.", "Conserto de pia", "Rua José, 182 - SP/SP", 220.00, "08/12/2025", "WITHDRAWABLE", "hoje"),
        TransactionDto("txn_2", "Daniel Vor.", "Conserto de chuveiro", "Rua José, 182 - SP/SP", 150.00, "07/12/2025", "COMPLETED", null),
        TransactionDto("txn_3", "Donald T.", "Conserto de piscina", "Rua José, 182 - SP/SP", 200.00, "06/12/2025", "COMPLETED", null),
        TransactionDto("txn_4", "Carlos Mendes", "Ajuste de costura", "Taboão da Serra - SP", 90.00, "05/12/2025", "WITHDRAWN", null),
    )

    var cards: MutableList<CardDto> = mutableListOf(
        CardDto("crd_1", "MASTERCARD", "6769", "ANA SILVA", "08/29", "Cartão principal", true),
        CardDto("crd_2", "VISA", "8371", "ANA SILVA", "11/28", "Reserva", false),
    )

    var subscription: SubscriptionDto = SubscriptionDto(
        active = false,
        planName = "Vértice Pro",
        monthlyPrice = 19.99,
        nextChargeDate = "08/12/2025",
        autoRenew = true,
        autoRenewMethod = "PIX",
        status = "UP_TO_DATE",
    )

    val plans: List<PlanDto> = listOf(
        PlanDto(
            id = "plan_pro_monthly",
            name = "Vértice Pro",
            monthlyPrice = 19.99,
            benefits = listOf(
                "Matches ilimitados — sem limite de conexões por mês",
                "Prioridade nos resultados — seu perfil aparece antes dos demais",
                "Acesso antecipado — veja novos parceiros antes de todos",
                "Relatórios de desempenho — acompanhe visualizações e conversões",
                "Selo Pro no perfil — mais credibilidade e confiança",
            ),
        ),
    )

    var violeta: VioletaStatusDto = VioletaStatusDto(
        enabled = true,
        identityVerified = true,
        phoneVerified = true,
        identityVerifiedAt = "jan 2026",
        phoneVerifiedAt = "jan 2025",
        safeRouteEnabled = true,
        emergencyButtonEnabled = true,
    )

    var trilhaDone: MutableSet<String> = mutableSetOf(
        "s1", "s2", "s3", "d1", "d2", "d3",
    )

    val modules: List<ModuleDto> = listOf(
        ModuleDto("m1", "Segurança Pessoal", listOf(
            LessonDto("s1", "Proteção digital", listOf(
                LessonCardDto("Sua identidade online", "Use senhas únicas para cada app. Ative verificação em duas etapas no WhatsApp: Configurações → Conta → Verificação.", "Nunca compartilhe códigos de verificação, nem com \"suporte técnico\"."),
                LessonCardDto("Cuidado com golpes", "Desconfie de mensagens urgentes pedindo dinheiro ou dados. Golpistas criam urgência falsa para você agir sem pensar.", "Recebeu proposta suspeita? Pesquise o CPF no Serasa antes de responder."),
            )),
            LessonDto("s2", "Segurança em Reuniões", listOf(
                LessonCardDto("Primeiro encontro", "Marque em local público e movimentado: café, coworking ou shopping. Avise alguém de confiança onde você estará.", "Use a Rota Segura do Protocolo Violeta para compartilhar localização."),
                LessonCardDto("Sinais de alerta", "Desconfie de quem pede desconto antes de ver o trabalho, exige sigilo total ou pressiona para assinar na hora.", "Você tem o direito de encerrar qualquer negociação que cause desconforto."),
            )),
            LessonDto("s3", "Contratos simples", listOf(
                LessonCardDto("Por que usar contrato?", "Mesmo simples, um contrato protege ambas as partes. Inclua: o que será feito, prazo, valor total e forma de pagamento.", "Contrato via WhatsApp ou e-mail também tem validade legal no Brasil."),
                LessonCardDto("O que não pode faltar", "Nome e CPF de ambos, descrição clara do serviço, valor, forma de pagamento e o que acontece se alguém desistir.", "Guarde sempre uma cópia. Print de conversa ou PDF enviado por e-mail servem."),
            )),
        )),
        ModuleDto("m2", "Direitos do MEI", listOf(
            LessonDto("d1", "O que é MEI", listOf(
                LessonCardDto("Microempreendedor Individual", "MEI é um registro que formaliza seu negócio com CNPJ próprio. Faturamento de até R$81.000/ano com direitos de trabalhadora.", "Com CNPJ você acessa crédito mais fácil e pode emitir nota fiscal."),
                LessonCardDto("Quanto custa?", "Mensalidade varia por área: R$72 para comércio, R$76 para serviços. Paga via DAS (boleto mensal) até o dia 20.", "Acesse gov.br/mei para emitir o DAS gratuitamente todo mês."),
            )),
            LessonDto("d2", "Obrigações fiscais", listOf(
                LessonCardDto("O que você precisa fazer", "Pagar o DAS mensalmente e entregar a Declaração Anual do Simples (DASN-SIMEI) até 31 de maio de cada ano.", "A declaração anual é gratuita e feita pelo site do governo. Não precisa de contador."),
                LessonCardDto("Emissão de nota fiscal", "Algumas prefeituras exigem nota para serviços. Verifique na prefeitura da sua cidade como emitir a NFS-e.", "Guardar comprovantes facilita muito na hora da declaração anual."),
            )),
            LessonDto("d3", "Benefícios do INSS", listOf(
                LessonCardDto("Você tem direito a", "Aposentadoria por idade, auxílio-doença, salário-maternidade (4 meses), pensão por morte — tudo com o DAS em dia.", "Você precisa de ao menos 12 meses de contribuição para acessar auxílio-doença."),
                LessonCardDto("Salário-maternidade", "A MEI tem direito a 4 meses de salário mínimo. Solicite pelo site Meu INSS com antecedência.", "Continue pagando o DAS durante a licença para não perder a qualidade de segurada."),
            )),
        )),
        ModuleDto("m3", "Gestão de Clientes", listOf(
            LessonDto("g1", "Atendimento profissional", listOf(
                LessonCardDto("Primeira impressão importa", "Responda em até 2 horas úteis. Use linguagem clara e educada. Apresente-se com nome e o que você faz.", "Crie mensagem de boas-vindas automática no WhatsApp Business para fora do horário."),
                LessonCardDto("Como lidar com reclamações", "Ouça sem interromper. Reconheça o problema, peça desculpas se for o caso, e proponha solução concreta com prazo.", "Um cliente que teve um problema resolvido bem se torna fiel e indica você."),
            )),
            LessonDto("g2", "Como cobrar corretamente", listOf(
                LessonCardDto("Defina seu preço com clareza", "Seja transparente sobre o valor antes de começar. Envie um orçamento escrito com o que está incluído.", "Nunca comece um serviço sem pelo menos 30-50% de entrada."),
                LessonCardDto("Inadimplência", "Envie lembrete gentil 1 dia antes do vencimento. Se atrasar, contate diretamente — sem ameaças, mas com firmeza.", "Use o Serasa para consultar o CPF de clientes novos antes de fazer crédito."),
            )),
            LessonDto("g3", "Fidelização de clientes", listOf(
                LessonCardDto("Clientes que voltam", "Manter um cliente custa 5x menos que conquistar um novo. Mande mensagem no aniversário, avise promoções, pergunte como foi.", "Um cliente satisfeito indica em média 3 pessoas. Peça avaliação ao final."),
                LessonCardDto("Programa simples de fidelidade", "A cada 5 serviços, ofereça desconto ou benefício. Mesmo algo pequeno cria lealdade.", "Anote dados dos clientes frequentes: nome, serviço preferido, última data."),
            )),
        )),
        ModuleDto("m4", "Melhores Contratos", listOf(
            LessonDto("f1", "Controle de gastos", listOf(
                LessonCardDto("Separe pessoal do profissional", "Tenha uma conta bancária separada para o negócio, mesmo que gratuita (Nubank, Inter, C6 Bank).", "Misturar dinheiro pessoal e do negócio é a principal causa de dívidas entre MEIs."),
                LessonCardDto("Anote tudo", "Use planilha simples ou app gratuito (Organizze, Mobills) para registrar toda entrada e saída semanalmente.", "Com 3 meses de dados você já consegue ver os meses mais fracos e se planejar."),
            )),
            LessonDto("f2", "Como precificar", listOf(
                LessonCardDto("A fórmula básica", "Custo + Despesas fixas + Margem de lucro = Preço. Inclua tempo de deslocamento, materiais e retrabalho.", "Pesquise quanto cobram outros profissionais da sua área antes de definir seu preço."),
                LessonCardDto("Por hora vs. por projeto", "Cobrar por hora é mais fácil no início. Por projeto é melhor quando você já sabe quanto tempo cada trabalho leva.", "Nunca reduza o preço abaixo do custo — trabalhar no prejuízo não sustenta o negócio."),
            )),
            LessonDto("f3", "Primeiros investimentos", listOf(
                LessonCardDto("Reserva de emergência primeiro", "Antes de investir, tenha 3 meses de despesas em conta que rende acima da poupança (CDB ou Tesouro Selic).", "Nubank e PicPay oferecem rendimento automático sem custo."),
                LessonCardDto("Invista no negócio", "Depois da reserva, invista em ferramentas, cursos e divulgação. Um celular melhor para fotos pode dobrar seus pedidos.", "Instagram e WhatsApp Business são as ferramentas mais baratas e eficazes para MEI."),
            )),
        )),
    )
}

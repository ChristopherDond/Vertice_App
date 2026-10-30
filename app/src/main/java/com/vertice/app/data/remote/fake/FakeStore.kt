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
            skills = listOf("Planejamento", "Investimentos", "CFP"), hourlyRate = 180.0, distanceKm = 4,
        ),
        FreelancerDto(
            id = "flw_carlos", name = "Carlos Mendes", role = "Pedreiro", area = "Construção Civil",
            category = "CONSTRUCTION", matchPercent = 94, rating = 4.9, servicesCount = 48,
            availability = "Disponível agora",
            bio = "Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.",
            gender = "MALE", hoursWorked = 2880, jobsDone = 48, isPro = true,
            skills = listOf("Reforma", "Hidráulica", "Acabamento"), hourlyRate = 95.0, distanceKm = 12,
        ),
        FreelancerDto(
            id = "flw_andre", name = "André Pereira", role = "Mestre de Obras", area = "Construção Civil",
            category = "CONSTRUCTION", matchPercent = 93, rating = 4.8, servicesCount = 31,
            availability = "Disponível amanhã",
            bio = "Gerenciamento completo de obras residenciais e comerciais. Da fundação ao acabamento. 15 anos de canteiro de obras.",
            gender = "MALE", hoursWorked = 3600, jobsDone = 31,
            skills = listOf("Gestão de obra", "Cronograma", "Orçamento"), hourlyRate = 120.0, distanceKm = 23,
        ),
        FreelancerDto(
            id = "flw_luisa", name = "Luísa Fonseca", role = "Lojista", area = "Comércio & Varejo",
            category = "COMMERCE", matchPercent = 87, rating = 4.7, servicesCount = 31,
            availability = "Disponível amanhã",
            bio = "Gestão de loja de moda feminina, vitrinismo e controle de estoque.",
            gender = "FEMALE", hoursWorked = 890, jobsDone = 31, isVioleta = true,
            skills = listOf("Vitrinismo", "Estoque", "Vendas"), hourlyRate = 80.0, distanceKm = 38,
        ),
        FreelancerDto(
            id = "flw_beatriz", name = "Beatriz Costa", role = "Eletricista", area = "Instalações Elétricas",
            category = "CONSTRUCTION", matchPercent = 91, rating = 4.8, servicesCount = 62,
            availability = "Disponível agora",
            bio = "Instalações elétricas residenciais e comerciais. Certificada pelo CREA.",
            gender = "FEMALE", hoursWorked = 3100, jobsDone = 62, isVioleta = true, isPro = true,
            skills = listOf("Elétrica", "CREA", "Manutenção"), hourlyRate = 110.0, distanceKm = 7,
        ),
        FreelancerDto(
            id = "flw_regina", name = "Regina Oliveira", role = "Pintora", area = "Pintura Residencial",
            category = "CONSTRUCTION", matchPercent = 83, rating = 4.6, servicesCount = 27,
            availability = "Disponível esta semana",
            bio = "Pintura interna e externa, texturas e efeitos decorativos. Atuo em toda São Paulo.",
            gender = "FEMALE", hoursWorked = 980, jobsDone = 27, isVioleta = true,
            skills = listOf("Pintura", "Textura", "Efeitos"), hourlyRate = 85.0, distanceKm = 31,
        ),
        FreelancerDto(
            id = "flw_joao", name = "João Pereira", role = "Encanador", area = "Serviços Hidráulicos",
            category = "SERVICES", matchPercent = 79, rating = 4.5, servicesCount = 39,
            availability = "Disponível amanhã",
            bio = "Reparos e instalações hidráulicas, desentupimentos e manutenção preventiva.",
            gender = "MALE", hoursWorked = 1450, jobsDone = 39,
            skills = listOf("Hidráulica", "Desentupimento", "Reparos"), hourlyRate = 90.0, distanceKm = 16,
        ),
        FreelancerDto(
            id = "flw_marina", name = "Marina Souza", role = "Diarista", area = "Serviços Domésticos",
            category = "SERVICES", matchPercent = 88, rating = 4.9, servicesCount = 84,
            availability = "Disponível agora",
            bio = "Limpeza residencial e comercial, organização de ambientes e pós-obra.",
            gender = "FEMALE", hoursWorked = 2100, jobsDone = 84, isVioleta = true,
            skills = listOf("Limpeza", "Organização", "Pós-obra"), hourlyRate = 70.0, distanceKm = 2,
        ),
        FreelancerDto(
            id = "flw_ana", name = "Ana Silva", role = "Cabeleireira", area = "Beleza",
            category = "BEAUTY", matchPercent = 92, rating = 4.8, servicesCount = 56,
            availability = "Disponível agora",
            bio = "Especialista em cortes, coloração e tratamentos capilares. Atendimento domiciliar e em salão. 8 anos transformando looks.",
            gender = "FEMALE", hoursWorked = 1980, jobsDone = 56, isVioleta = true,
            skills = listOf("Corte", "Coloração", "Tratamento"), hourlyRate = 130.0, distanceKm = 9,
        ),
        FreelancerDto(
            id = "flw_roberto", name = "Roberto Oliveira", role = "Chef Particular", area = "Alimentação",
            category = "FOOD", matchPercent = 89, rating = 4.9, servicesCount = 42,
            availability = "Disponível amanhã",
            bio = "Chef formado em gastronomia, cardápios personalizados para eventos e jantares íntimos. Cozinha brasileira e mediterrânea.",
            gender = "MALE", hoursWorked = 1620, jobsDone = 42,
            skills = listOf("Eventos", "Menu", "Gastronomia"), hourlyRate = 200.0, distanceKm = 27,
        ),
        FreelancerDto(
            id = "flw_diego", name = "Diego Santos", role = "Faxineiro Profissional", area = "Limpeza",
            category = "SERVICES", matchPercent = 86, rating = 4.7, servicesCount = 71,
            availability = "Disponível agora",
            bio = "Limpeza pesada, pós-obra, escritórios e residências. Equipamentos profissionais e produtos ecológicos.",
            gender = "MALE", hoursWorked = 2290, jobsDone = 71,
            skills = listOf("Pesada", "Ecológico", "Escritórios"), hourlyRate = 75.0, distanceKm = 44,
        ),
        FreelancerDto(
            id = "flw_juliana", name = "Juliana Ribeiro", role = "Personal Organizer", area = "Serviços",
            category = "SERVICES", matchPercent = 90, rating = 4.9, servicesCount = 29,
            availability = "Disponível esta semana",
            bio = "Organização de residências, mudanças e home offices. Método próprio para otimizar espaços e rotinas. Certificada NAPO.",
            gender = "FEMALE", hoursWorked = 720, jobsDone = 29, isVioleta = true,
            skills = listOf("Organização", "Mudanças", "NAPO"), hourlyRate = 100.0, distanceKm = 19,
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
        enabled = false,
        identityVerified = true,
        phoneVerified = true,
        identityVerifiedAt = "jan 2026",
        phoneVerifiedAt = "jan 2025",
        safeRouteEnabled = true,
        emergencyButtonEnabled = true,
    )

    var trilhaDone: MutableSet<String> = mutableSetOf()

    // Conteúdo da Trilha de Blindagem. Sem valores em reais nem percentuais:
    // eles mudam todo ano — a lição aponta para o gov.br/mei.
    // O id da lição é letra(s) + número: LearningRepositoryImpl usa a parte de letras como moduleId.
    val modules: List<ModuleDto> = listOf(
        ModuleDto("m1", "Segurança Pessoal", listOf(
            LessonDto("s1", "Proteção digital", listOf(
                LessonCardDto("Senhas e verificação em duas etapas", "Use uma senha diferente para cada aplicativo importante e ative a verificação em duas etapas no WhatsApp e no e-mail. Assim, mesmo que alguém descubra sua senha, não consegue entrar sozinho.", "Nunca passe códigos recebidos por SMS ou aplicativo para ninguém, nem para quem diz ser do suporte."),
                LessonCardDto("Golpes que usam a pressa", "Golpistas criam urgência para você agir sem pensar: pedido de Pix \"para liberar o pagamento\", link para \"atualizar cadastro\", cliente que paga a mais e pede o troco de volta. Respire e confirme por outro canal.", "Desconfie de qualquer pedido de dinheiro antes de começar o serviço. Se algo parecer estranho, pare e converse com alguém de confiança."),
                LessonCardDto("O celular é a sua loja", "Use bloqueio de tela, mantenha os aplicativos atualizados e baixe apenas das lojas oficiais. Se perder o celular, bloqueie o chip e troque as senhas o quanto antes.", "Guarde uma cópia dos seus contatos de clientes fora do aparelho."),
            )),
            LessonDto("s2", "Segurança em Reuniões", listOf(
                LessonCardDto("Primeiro encontro", "Marque em local público e movimentado: café, coworking ou shopping. Avise alguém de confiança onde você estará e a que horas pretende voltar.", "Use a Rota Segura do Protocolo Violeta para compartilhar sua localização."),
                LessonCardDto("Sinais de alerta", "Desconfie de quem pede desconto antes de ver o trabalho, exige sigilo total ou pressiona para assinar na hora.", "Você tem o direito de encerrar qualquer negociação que cause desconforto."),
                LessonCardDto("Reunião online", "Prefira aplicativos de chamada conhecidos, confirme quem está do outro lado antes de compartilhar a tela ou documentos e não envie documentos pessoais sem necessidade.", "Se a pessoa não quer mostrar o rosto nem se identificar, trate como sinal de alerta."),
            )),
            LessonDto("s3", "Contratos seguros", listOf(
                LessonCardDto("Por que usar contrato?", "Mesmo simples, um contrato protege os dois lados. Ele registra o que será feito, o prazo, o valor total e a forma de pagamento.", "Contrato por e-mail ou mensagem também serve como prova do combinado."),
                LessonCardDto("O que não pode faltar", "Nome e CPF (ou CNPJ) de ambos, descrição clara do serviço, valor, forma de pagamento e o que acontece se alguém desistir.", "Guarde sempre uma cópia: PDF por e-mail ou conversa salva."),
                LessonCardDto("Cuidado antes de assinar", "Leia tudo antes de assinar, inclusive multas e prazos. Não assine nada em branco nem sob pressão. Se não entender uma parte, peça para explicarem.", "Em caso de dúvida, procure o Sebrae ou o Procon da sua cidade."),
            )),
        )),
        ModuleDto("m2", "Direitos do MEI", listOf(
            LessonDto("d1", "O que é MEI", listOf(
                LessonCardDto("Microempreendedor Individual", "MEI é a forma de formalizar um pequeno negócio com CNPJ próprio. Existe um limite de faturamento por ano e uma lista de atividades que podem ser MEI.", "Consulte o limite e a lista de atividades atualizados no Portal do Empreendedor (gov.br/mei)."),
                LessonCardDto("Por que formalizar?", "Com CNPJ você pode emitir nota fiscal, abrir conta e pedir crédito como empresa, e passa a contribuir para a Previdência (INSS), com direito a benefícios.", "Abrir o MEI é gratuito. Desconfie de sites que cobram para fazer o cadastro."),
            )),
            LessonDto("d2", "Obrigações fiscais", listOf(
                LessonCardDto("Pagar o DAS todo mês", "O DAS é o boleto único mensal do MEI. O valor depende da atividade e é atualizado pelo governo. O vencimento é no dia 20 de cada mês.", "Emita o DAS só pelo gov.br/mei ou pelo aplicativo MEI. Cuidado com boletos falsos enviados por mensagem."),
                LessonCardDto("Declaração anual", "Todo ano o MEI entrega a Declaração Anual de Faturamento (DASN-SIMEI) até 31 de maio, pelo Portal do Empreendedor. É gratuita e dá para fazer sem contador.", "Guarde notas e comprovantes: eles ajudam a informar o faturamento do ano."),
                LessonCardDto("Nota fiscal", "Quando você vende ou presta serviço para outra empresa, precisa emitir nota fiscal. Para pessoa física, a regra depende da atividade e do município.", "Confirme na prefeitura da sua cidade como emitir a nota de serviço."),
            )),
            LessonDto("d3", "Benefícios do INSS", listOf(
                LessonCardDto("Você tem direito a", "Com o DAS em dia, o MEI conta com aposentadoria por idade, auxílio por incapacidade temporária (antigo auxílio-doença), salário-maternidade e pensão por morte para a família. Cada benefício tem um tempo mínimo de contribuição.", "Se parar de pagar o DAS, você pode perder a proteção da Previdência. Regularize assim que puder."),
                LessonCardDto("Salário-maternidade", "A MEI que cumpre o tempo mínimo de contribuição pode receber salário-maternidade por 120 dias. O pedido é feito pelo Meu INSS (aplicativo ou site).", "Guarde os comprovantes de pagamento do DAS: eles podem ser pedidos na análise."),
            )),
        )),
        ModuleDto("m3", "Melhores Contratos", listOf(
            LessonDto("c1", "Contrato simples e claro", listOf(
                LessonCardDto("O básico de um bom contrato", "Escreva quem contrata e quem presta o serviço, o que será entregue, o prazo, o valor e a forma de pagamento. Frases curtas e linguagem do dia a dia evitam mal-entendidos.", "Contrato claro não precisa de palavras difíceis: precisa ser entendido pelos dois lados."),
                LessonCardDto("Combinado por escrito vale", "Um acordo por e-mail ou mensagem também serve de prova. Peça que o cliente responda \"de acordo\" e guarde a conversa.", "Salve as conversas e os arquivos importantes em um lugar seguro."),
            )),
            LessonDto("c2", "Orçamento e entrada", listOf(
                LessonCardDto("Orçamento por escrito", "Antes de começar, envie um orçamento com o que está incluído, o que não está e quanto custa cada parte. Assim o cliente sabe o que esperar e você evita pedidos extras de graça.", "Defina quantos ajustes ou revisões já estão incluídos no preço."),
                LessonCardDto("Entrada e forma de pagamento", "Combinar uma entrada ajuda a cobrir seus materiais e mostra compromisso dos dois lados. Combine também como e quando o restante será pago e registre tudo por escrito.", "Prefira pagamentos que deixam comprovante, como Pix ou transferência, e guarde o comprovante."),
            )),
            LessonDto("c3", "Prazos, entregas e cancelamento", listOf(
                LessonCardDto("Prazo e entrega", "Combine as datas de início e de entrega e o que acontece se um dos lados atrasar. Se o cliente precisa enviar algo (medidas, fotos, materiais), coloque isso no combinado.", "Avise o cliente logo que perceber que um prazo pode mudar."),
                LessonCardDto("E se mudar de ideia?", "Escreva como funciona o cancelamento: o que é devolvido, o que fica para cobrir o trabalho já feito e com quanta antecedência avisar. Isso evita conflito e protege os dois.", "Se não houver acordo, tente primeiro conversar; depois, o Procon da sua cidade pode orientar."),
            )),
        )),
        ModuleDto("m4", "Gestão de Clientes", listOf(
            LessonDto("g1", "Atendimento profissional", listOf(
                LessonCardDto("Primeira impressão importa", "Responda com educação e clareza, apresente seu nome e o que você faz, e diga em quanto tempo vai retornar. Cumprir o prazo que você mesmo anunciou passa confiança.", "No WhatsApp Business você pode criar mensagem de saudação e de ausência."),
                LessonCardDto("Como lidar com reclamações", "Ouça sem interromper. Reconheça o problema, peça desculpas se for o caso e proponha uma solução concreta com prazo.", "Um problema bem resolvido pode fortalecer a confiança do cliente."),
            )),
            LessonDto("g2", "Como cobrar corretamente", listOf(
                LessonCardDto("Defina seu preço com clareza", "Seja transparente sobre o valor antes de começar. Envie um orçamento escrito com o que está incluído.", "Combine entrada e forma de pagamento antes de iniciar (veja o módulo Melhores Contratos)."),
                LessonCardDto("Atraso no pagamento", "Envie um lembrete gentil um dia antes do vencimento. Se atrasar, entre em contato direto, sem ameaças, mas com firmeza.", "Com clientes novos, prefira receber parte antes de começar em vez de dar prazo longo."),
            )),
            LessonDto("g3", "Fidelização de clientes", listOf(
                LessonCardDto("Clientes que voltam", "Manter contato depois do serviço, perguntando se ficou satisfeito e avisando novidades, ajuda o cliente a lembrar de você e a indicar para outras pessoas.", "Peça uma avaliação ou indicação ao final do serviço, com educação."),
                LessonCardDto("Programa simples de fidelidade", "A cada certo número de serviços, ofereça um desconto ou um pequeno benefício. Mesmo algo simples cria vínculo.", "Anote o nome, o serviço preferido e a data do último atendimento dos clientes frequentes."),
            )),
        )),
        ModuleDto("m5", "Finanças do Negócio", listOf(
            LessonDto("f1", "Controle de gastos", listOf(
                LessonCardDto("Separe o pessoal do profissional", "Se puder, use uma conta separada para o negócio. Misturar o dinheiro da casa com o do trabalho dificulta saber se você está tendo lucro.", "Compare as tarifas antes de escolher um banco ou conta para o negócio."),
                LessonCardDto("Anote tudo", "Registre toda entrada e toda saída, toda semana, em caderno, planilha ou aplicativo gratuito. Depois de alguns meses você enxerga os meses mais fracos e se planeja melhor.", "Escolha um dia fixo da semana para atualizar suas anotações."),
            )),
            LessonDto("f2", "Como precificar", listOf(
                LessonCardDto("A fórmula básica", "Custo + despesas fixas + margem de lucro = preço. Inclua tempo de deslocamento, materiais e retrabalho.", "Pesquise o que outros profissionais da sua área cobram antes de definir seu preço."),
                LessonCardDto("Por hora ou por projeto", "Cobrar por hora é mais simples no começo. Por projeto funciona melhor quando você já sabe quanto tempo cada trabalho leva.", "Evite cobrar abaixo do seu custo: trabalhar no prejuízo não sustenta o negócio."),
            )),
            LessonDto("f3", "Primeiros investimentos", listOf(
                LessonCardDto("Reserva para imprevistos primeiro", "Antes de investir, monte uma reserva para cobrir alguns meses de despesas, em uma aplicação segura da qual você consiga resgatar o dinheiro rápido.", "Desconfie de promessa de ganho garantido e rápido."),
                LessonCardDto("Invista no negócio", "Depois da reserva, invista no que faz o negócio render: ferramentas, cursos e divulgação. Um bom celular para fotografar seus produtos, por exemplo, ajuda a mostrar seu trabalho.", "Redes sociais e WhatsApp Business são formas de divulgar com baixo custo."),
            )),
        )),
    )
}

package com.vertice.app.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class LCard(val heading: String, val body: String, val tip: String? = null)
data class Lesson(val id: String, val title: String, val emoji: String, val cards: List<LCard>, val initDone: Boolean, val locked: Boolean, val current: Boolean = false)
data class TModule(val id: String, val title: String, val icon: ImageVector, val color: Color, val lessons: List<Lesson>)

val TRILHA = listOf(
    TModule(
        "m1", "Segurança Pessoal", Icons.Filled.Shield, Color(0xFF10B981),
        listOf(
            Lesson("s1", "Proteção digital", "\uD83D\uDD10", listOf(
                LCard("Sua identidade online", "Use senhas únicas para cada app. Ative verificação em duas etapas no WhatsApp: Configurações → Conta → Verificação.", "Nunca compartilhe códigos de verificação, nem com \"suporte técnico\"."),
                LCard("Cuidado com golpes", "Desconfie de mensagens urgentes pedindo dinheiro ou dados. Golpistas criam urgência falsa para você agir sem pensar.", "Recebeu proposta suspeita? Pesquise o CPF no Serasa antes de responder."),
            ), initDone = true, locked = false),
            Lesson("s2", "Segurança em reuniões", "\uD83E\uDD1D", listOf(
                LCard("Primeiro encontro", "Marque em local público e movimentado: café, coworking ou shopping. Avise alguém de confiança onde você estará.", "Use a Rota Segura do Protocolo Violeta para compartilhar localização."),
                LCard("Sinais de alerta", "Desconfie de quem pede desconto antes de ver o trabalho, exige sigilo total ou pressiona para assinar na hora.", "Você tem o direito de encerrar qualquer negociação que cause desconforto."),
            ), initDone = true, locked = false),
            Lesson("s3", "Contratos simples", "\uD83D\uDCDD", listOf(
                LCard("Por que usar contrato?", "Mesmo simples, um contrato protege ambas as partes. Inclua: o que será feito, prazo, valor total e forma de pagamento.", "Contrato via WhatsApp ou e-mail também tem validade legal no Brasil."),
                LCard("O que não pode faltar", "Nome e CPF de ambos, descrição clara do serviço, valor, forma de pagamento e o que acontece se alguém desistir.", "Guarde sempre uma cópia. Print de conversa ou PDF enviado por e-mail servem."),
            ), initDone = true, locked = false),
        ),
    ),
    TModule(
        "m2", "Direitos da MEI", Icons.Filled.MenuBook, Color(0xFF9B5FF7),
        listOf(
            Lesson("d1", "O que é MEI", "\uD83C\uDFDB\uFE0F", listOf(
                LCard("Microempreendedor Individual", "MEI é um registro que formaliza seu negócio com CNPJ próprio. Faturamento de até R$81.000/ano com direitos de trabalhadora.", "Com CNPJ você acessa crédito mais fácil e pode emitir nota fiscal."),
                LCard("Quanto custa?", "Mensalidade varia por área: R$72 para comércio, R$76 para serviços. Paga via DAS (boleto mensal) até o dia 20.", "Acesse gov.br/mei para emitir o DAS gratuitamente todo mês."),
            ), initDone = true, locked = false),
            Lesson("d2", "Obrigações fiscais", "\uD83D\uDCCA", listOf(
                LCard("O que você precisa fazer", "Pagar o DAS mensalmente e entregar a Declaração Anual do Simples (DASN-SIMEI) até 31 de maio de cada ano.", "A declaração anual é gratuita e feita pelo site do governo. Não precisa de contador."),
                LCard("Emissão de nota fiscal", "Algumas prefeituras exigem nota para serviços. Verifique na prefeitura da sua cidade como emitir a NFS-e.", "Guardar comprovantes facilita muito na hora da declaração anual."),
            ), initDone = true, locked = false),
            Lesson("d3", "Benefícios do INSS", "\uD83D\uDEE1\uFE0F", listOf(
                LCard("Você tem direito a", "Aposentadoria por idade, auxílio-doença, salário-maternidade (4 meses), pensão por morte — tudo com o DAS em dia.", "Você precisa de ao menos 12 meses de contribuição para acessar auxílio-doença."),
                LCard("Salário-maternidade", "A MEI tem direito a 4 meses de salário mínimo. Solicite pelo site Meu INSS com antecedência.", "Continue pagando o DAS durante a licença para não perder a qualidade de segurada."),
            ), initDone = true, locked = false),
        ),
    ),
    TModule(
        "m3", "Gestão de Clientes", Icons.Filled.TrackChanges, Color(0xFF7C3AED),
        listOf(
            Lesson("g1", "Atendimento profissional", "\uD83D\uDCAC", listOf(
                LCard("Primeira impressão importa", "Responda em até 2 horas úteis. Use linguagem clara e educada. Apresente-se com nome e o que você faz.", "Crie mensagem de boas-vindas automática no WhatsApp Business para fora do horário."),
                LCard("Como lidar com reclamações", "Ouça sem interromper. Reconheça o problema, peça desculpas se for o caso, e proponha solução concreta com prazo.", "Um cliente que teve um problema resolvido bem se torna fiel e indica você."),
            ), initDone = false, locked = false, current = true),
            Lesson("g2", "Como cobrar corretamente", "\uD83D\uDCB0", listOf(
                LCard("Defina seu preço com clareza", "Seja transparente sobre o valor antes de começar. Envie um orçamento escrito com o que está incluído.", "Nunca comece um serviço sem pelo menos 30-50% de entrada."),
                LCard("Inadimplência", "Envie lembrete gentil 1 dia antes do vencimento. Se atrasar, contate diretamente — sem ameaças, mas com firmeza.", "Use o Serasa para consultar o CPF de clientes novos antes de fazer crédito."),
            ), initDone = false, locked = true),
            Lesson("g3", "Fidelização de clientes", "\u2B50", listOf(
                LCard("Clientes que voltam", "Manter um cliente custa 5x menos que conquistar um novo. Mande mensagem no aniversário, avise promoções, pergunte como foi.", "Um cliente satisfeito indica em média 3 pessoas. Peça avaliação ao final."),
                LCard("Programa simples de fidelidade", "A cada 5 serviços, ofereça desconto ou benefício. Mesmo algo pequeno cria lealdade.", "Anote dados dos clientes frequentes: nome, serviço preferido, última data."),
            ), initDone = false, locked = true),
        ),
    ),
    TModule(
        "m4", "Finanças para Negócios", Icons.Filled.TrendingUp, Color(0xFFF59E0B),
        listOf(
            Lesson("f1", "Controle de gastos", "\uD83D\uDCF1", listOf(
                LCard("Separe pessoal do profissional", "Tenha uma conta bancária separada para o negócio, mesmo que gratuita (Nubank, Inter, C6 Bank).", "Misturar dinheiro pessoal e do negócio é a principal causa de dívidas entre MEIs."),
                LCard("Anote tudo", "Use planilha simples ou app gratuito (Organizze, Mobills) para registrar toda entrada e saída semanalmente.", "Com 3 meses de dados você já consegue ver os meses mais fracos e se planejar."),
            ), initDone = false, locked = true),
            Lesson("f2", "Como precificar", "\uD83C\uDFF7\uFE0F", listOf(
                LCard("A fórmula básica", "Custo + Despesas fixas + Margem de lucro = Preço. Inclua tempo de deslocamento, materiais e retrabalho.", "Pesquise quanto cobram outros profissionais da sua área antes de definir seu preço."),
                LCard("Por hora vs. por projeto", "Cobrar por hora é mais fácil no início. Por projeto é melhor quando você já sabe quanto tempo cada trabalho leva.", "Nunca reduza o preço abaixo do custo — trabalhar no prejuízo não sustenta o negócio."),
            ), initDone = false, locked = true),
            Lesson("f3", "Primeiros investimentos", "\uD83D\uDCC8", listOf(
                LCard("Reserva de emergência primeiro", "Antes de investir, tenha 3 meses de despesas em conta que rende acima da poupança (CDB ou Tesouro Selic).", "Nubank e PicPay oferecem rendimento automático sem custo."),
                LCard("Invista no negócio", "Depois da reserva, invista em ferramentas, cursos e divulgação. Um celular melhor para fotos pode dobrar seus pedidos.", "Instagram e WhatsApp Business são as ferramentas mais baratas e eficazes para MEI."),
            ), initDone = false, locked = true),
        ),
    ),
)

data class ModDisplay(val icon: ImageVector, val label: String, val done: Boolean)
val MOD_DISPLAY = listOf(
    ModDisplay(Icons.Filled.Shield, "Segurança Pessoal", true),
    ModDisplay(Icons.Filled.MenuBook, "Direitos da MEI", true),
    ModDisplay(Icons.Filled.TrackChanges, "Gestão de Clientes", false),
    ModDisplay(Icons.Filled.TrendingUp, "Finanças para Negócios", false),
)

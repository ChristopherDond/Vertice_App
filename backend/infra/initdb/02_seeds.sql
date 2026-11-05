-- =============================================================================
-- VÉRTICE APP — SEEDS INICIAIS (POSTGRESQL 16)
-- Arquivo: 02_seeds.sql
-- Dados extraídos fielmente de FakeStore.kt e contratos do aplicativo
-- =============================================================================

-- 1. USUÁRIA DEMO DO APLICATIVO (Ana Silva)
INSERT INTO usuarios (
    id, name, email, phone, cpf, password_hash, bio, skills, is_pro, violeta_verified, profile_completion, rating, completed_services
) VALUES (
    'usr_ana',
    'Ana Silva',
    'ana.silva@vertice.app',
    '+5511999990000',
    '52998224725',
    '$2a$10$ZbwZ0B3RzcK26.PzVfNmpOGMM1fJYwJiWpVn7WBlT1ww9hBH4haRy', -- Hash bcrypt para 'senha123'
    'Especialista em cortes, coloração e tratamentos capilares. Atendimento domiciliar e em salão. 8 anos transformando looks.',
    ARRAY['Corte', 'Coloração', 'Tratamento'],
    TRUE,
    TRUE,
    100,
    4.8,
    56
) ON CONFLICT (id) DO NOTHING;

-- Endereço da usuária demo
INSERT INTO enderecos (
    usuario_id, cep, rua, numero, complemento, district, cidade, estado, latitude, longitude
) VALUES (
    'usr_ana', '01310-100', 'Avenida Paulista', '1000', 'Apto 42', 'Bela Vista', 'São Paulo', 'SP', -23.561414, -46.6558819
) ON CONFLICT DO NOTHING;

-- Carteira e saldo zerado da usuária demo
INSERT INTO carteira_saldo (
    usuario_id, disponivel, pendente, moeda
) VALUES (
    'usr_ana', 0.00, 0.00, 'BRL'
) ON CONFLICT (usuario_id) DO NOTHING;

-- Protocolo Violeta habilitado e verificado para Ana Silva
INSERT INTO protocolo_violeta (
    usuario_id, enabled, identity_verified, phone_verified, identity_verified_at, phone_verified_at, safe_route_enabled, emergency_button_enabled
) VALUES (
    'usr_ana', TRUE, TRUE, TRUE, NOW() - INTERVAL '30 days', NOW() - INTERVAL '30 days', TRUE, TRUE
) ON CONFLICT (usuario_id) DO NOTHING;

-- 2. CATÁLOGO DE PLANOS
INSERT INTO planos (
    id, name, monthly_price, benefits, active
) VALUES (
    'plan_pro_monthly',
    'Vértice Pro',
    19.99,
    ARRAY[
        'Matches ilimitados — sem limite de conexões por mês',
        'Prioridade nos resultados — seu perfil aparece antes dos demais',
        'Acesso antecipado — veja novos parceiros antes de todos',
        'Relatórios de desempenho — acompanhe visualizações e conversões',
        'Selo Pro no perfil — mais credibilidade e confiança'
    ],
    TRUE
) ON CONFLICT (id) DO NOTHING;

-- Assinatura ativa da usuária demo
INSERT INTO assinaturas (
    usuario_id, plano_id, active, next_charge_date, auto_renew, auto_renew_method, status
) VALUES (
    'usr_ana', 'plan_pro_monthly', TRUE, '2026-12-08', TRUE, 'PIX', 'UP_TO_DATE'
) ON CONFLICT DO NOTHING;

-- 3. CATÁLOGO DOS 12 FREELANCERS DO MATCH
INSERT INTO freelancers (
    id, usuario_id, name, role, area, category, match_percent, rating, services_count, availability, bio, gender, hours_worked, jobs_done, is_violeta, is_pro, skills, hourly_rate, distance_km
) VALUES
(
    'flw_camila', NULL, 'Camila Ferreira', 'Consultora Financeira', 'Consultoria', 'CONSULTING',
    93, 5.0, 38, 'Disponível esta semana',
    'Planejamento financeiro pessoal e para pequenos negócios. Certificada CFP. Ajuda a organizar finanças e investir com segurança.',
    'FEMALE', 1240, 38, TRUE, TRUE, ARRAY['Planejamento', 'Investimentos', 'CFP'], 180.0, 4
),
(
    'flw_carlos', NULL, 'Carlos Mendes', 'Pedreiro', 'Construção Civil', 'CONSTRUCTION',
    94, 4.9, 48, 'Disponível agora',
    'Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.',
    'MALE', 2880, 48, FALSE, TRUE, ARRAY['Reforma', 'Hidráulica', 'Acabamento'], 95.0, 12
),
(
    'flw_andre', NULL, 'André Pereira', 'Mestre de Obras', 'Construção Civil', 'CONSTRUCTION',
    93, 4.8, 31, 'Disponível amanhã',
    'Gerenciamento completo de obras residenciais e comerciais. Da fundação ao acabamento. 15 anos de canteiro de obras.',
    'MALE', 3600, 31, FALSE, FALSE, ARRAY['Gestão de obra', 'Cronograma', 'Orçamento'], 120.0, 23
),
(
    'flw_luisa', NULL, 'Luísa Fonseca', 'Lojista', 'Comércio & Varejo', 'COMMERCE',
    87, 4.7, 31, 'Disponível amanhã',
    'Gestão de loja de moda feminina, vitrinismo e controle de estoque.',
    'FEMALE', 890, 31, TRUE, FALSE, ARRAY['Vitrinismo', 'Estoque', 'Vendas'], 80.0, 38
),
(
    'flw_beatriz', NULL, 'Beatriz Costa', 'Eletricista', 'Instalações Elétricas', 'CONSTRUCTION',
    91, 4.8, 62, 'Disponível agora',
    'Instalações elétricas residenciais e comerciais. Certificada pelo CREA.',
    'FEMALE', 3100, 62, TRUE, TRUE, ARRAY['Elétrica', 'CREA', 'Manutenção'], 110.0, 7
),
(
    'flw_regina', NULL, 'Regina Oliveira', 'Pintora', 'Pintura Residencial', 'CONSTRUCTION',
    83, 4.6, 27, 'Disponível esta semana',
    'Pintura interna e externa, texturas e efeitos decorativos. Atuo em toda São Paulo.',
    'FEMALE', 980, 27, TRUE, FALSE, ARRAY['Pintura', 'Textura', 'Efeitos'], 85.0, 31
),
(
    'flw_joao', NULL, 'João Pereira', 'Encanador', 'Serviços Hidráulicos', 'SERVICES',
    79, 4.5, 39, 'Disponível amanhã',
    'Reparos e instalações hidráulicas, desentupimentos e manutenção preventiva.',
    'MALE', 1450, 39, FALSE, FALSE, ARRAY['Hidráulica', 'Desentupimento', 'Reparos'], 90.0, 16
),
(
    'flw_marina', NULL, 'Marina Souza', 'Diarista', 'Serviços Domésticos', 'SERVICES',
    88, 4.9, 84, 'Disponível agora',
    'Limpeza residencial e comercial, organização de ambientes e pós-obra.',
    'FEMALE', 2100, 84, TRUE, FALSE, ARRAY['Limpeza', 'Organização', 'Pós-obra'], 70.0, 2
),
(
    'flw_ana', 'usr_ana', 'Ana Silva', 'Cabeleireira', 'Beleza', 'BEAUTY',
    92, 4.8, 56, 'Disponível agora',
    'Especialista em cortes, coloração e tratamentos capilares. Atendimento domiciliar e em salão. 8 anos transformando looks.',
    'FEMALE', 1980, 56, TRUE, TRUE, ARRAY['Corte', 'Coloração', 'Tratamento'], 130.0, 9
),
(
    'flw_roberto', NULL, 'Roberto Oliveira', 'Chef Particular', 'Alimentação', 'FOOD',
    89, 4.9, 42, 'Disponível amanhã',
    'Chef formado em gastronomia, cardápios personalizados para eventos e jantares íntimos. Cozinha brasileira e mediterrânea.',
    'MALE', 1620, 42, FALSE, FALSE, ARRAY['Eventos', 'Menu', 'Gastronomia'], 200.0, 27
),
(
    'flw_diego', NULL, 'Diego Santos', 'Faxineiro Profissional', 'Limpeza', 'SERVICES',
    86, 4.7, 71, 'Disponível agora',
    'Limpeza pesada, pós-obra, escritórios e residências. Equipamentos profissionais e produtos ecológicos.',
    'MALE', 2290, 71, FALSE, FALSE, ARRAY['Pesada', 'Ecológico', 'Escritórios'], 75.0, 44
),
(
    'flw_juliana', NULL, 'Juliana Ribeiro', 'Personal Organizer', 'Serviços', 'SERVICES',
    90, 4.9, 29, 'Disponível esta semana',
    'Organização de residências, mudanças e home offices. Método próprio para otimizar espaços e rotinas. Certificada NAPO.',
    'FEMALE', 720, 29, TRUE, FALSE, ARRAY['Organização', 'Mudanças', 'NAPO'], 100.0, 19
)
ON CONFLICT (id) DO NOTHING;

-- 4. MÓDULOS DA TRILHA DE BLINDAGEM
INSERT INTO trilha_modulos (id, title, ordem) VALUES
('m1', 'Segurança Pessoal', 1),
('m2', 'Direitos do MEI', 2),
('m3', 'Melhores Contratos', 3),
('m4', 'Gestão de Clientes', 4),
('m5', 'Finanças do Negócio', 5)
ON CONFLICT (id) DO NOTHING;

-- 5. LIÇÕES DA TRILHA DE BLINDAGEM
INSERT INTO trilha_licoes (id, modulo_id, title, ordem) VALUES
-- Módulo 1: Segurança Pessoal
('s1', 'm1', 'Proteção digital', 1),
('s2', 'm1', 'Segurança em Reuniões', 2),
('s3', 'm1', 'Contratos seguros', 3),
-- Módulo 2: Direitos do MEI
('d1', 'm2', 'O que é MEI', 1),
('d2', 'm2', 'Obrigações fiscais', 2),
('d3', 'm2', 'Benefícios do INSS', 3),
-- Módulo 3: Melhores Contratos
('c1', 'm3', 'Contrato simples e claro', 1),
('c2', 'm3', 'Orçamento e entrada', 2),
('c3', 'm3', 'Prazos, entregas e cancelamento', 3),
-- Módulo 4: Gestão de Clientes
('g1', 'm4', 'Atendimento profissional', 1),
('g2', 'm4', 'Como cobrar corretamente', 2),
('g3', 'm4', 'Fidelização de clientes', 3),
-- Módulo 5: Finanças do Negócio
('f1', 'm5', 'Controle de gastos', 1),
('f2', 'm5', 'Como precificar', 2),
('f3', 'm5', 'Primeiros investimentos', 3)
ON CONFLICT (id) DO NOTHING;

-- 6. CARDS DE CONTEÚDO DAS LIÇÕES
-- Lição s1: Proteção digital
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('s1', 'Senhas e verificação em duas etapas', 'Use uma senha diferente para cada aplicativo importante e ative a verificação em duas etapas no WhatsApp e no e-mail. Assim, mesmo que alguém descubra sua senha, não consegue entrar sozinho.', 'Nunca passe códigos recebidos por SMS ou aplicativo para ninguém, nem para quem diz ser do suporte.', 1),
('s1', 'Golpes que usam a pressa', 'Golpistas criam urgência para você agir sem pensar: pedido de Pix "para liberar o pagamento", link para "atualizar cadastro", cliente que paga a mais e pede o troco de volta. Respire e confirme por outro canal.', 'Desconfie de qualquer pedido de dinheiro antes de começar o serviço. Se algo parecer estranho, pare e converse com alguém de confiança.', 2),
('s1', 'O celular é a sua loja', 'Use bloqueio de tela, mantenha os aplicativos atualizados e baixe apenas das lojas oficiais. Se perder o celular, bloqueie o chip e troque as senhas o quanto antes.', 'Guarde uma cópia dos seus contatos de clientes fora do aparelho.', 3);

-- Lição s2: Segurança em Reuniões
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('s2', 'Primeiro encontro', 'Marque em local público e movimentado: café, coworking ou shopping. Avise alguém de confiança onde você estará e a que horas pretende voltar.', 'Use a Rota Segura do Protocolo Violeta para compartilhar sua localização.', 1),
('s2', 'Sinais de alerta', 'Desconfie de quem pede desconto antes de ver o trabalho, exige sigilo total ou pressiona para assinar na hora.', 'Você tem o direito de encerrar qualquer negociação que cause desconforto.', 2),
('s2', 'Reunião online', 'Prefira aplicativos de chamada conhecidos, confirme quem está do outro lado antes de compartilhar a tela ou documentos e não envie documentos pessoais sem necessidade.', 'Se a pessoa não quer mostrar o rosto nem se identificar, trate como sinal de alerta.', 3);

-- Lição s3: Contratos seguros
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('s3', 'Por que usar contrato?', 'Mesmo simples, um contrato protege os dois lados. Ele registra o que será feito, o prazo, o valor total e a forma de pagamento.', 'Contrato por e-mail ou mensagem também serve como prova do combinado.', 1),
('s3', 'O que não pode faltar', 'Nome e CPF (ou CNPJ) de ambos, descrição clara do serviço, valor, forma de pagamento e o que acontece se alguém desistir.', 'Guarde sempre uma cópia: PDF por e-mail ou conversa salva.', 2),
('s3', 'Cuidado antes de assinar', 'Leia tudo antes de assinar, inclusive multas e prazos. Não assine nada em branco nem sob pressão. Se não entender uma parte, peça para explicarem.', 'Em caso de dúvida, procure o Sebrae ou o Procon da sua cidade.', 3);

-- Lição d1: O que é MEI
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('d1', 'Microempreendedor Individual', 'MEI é a forma de formalizar um pequeno negócio com CNPJ próprio. Existe um limite de faturamento por ano e uma lista de atividades que podem ser MEI.', 'Consulte o limite e a lista de atividades atualizados no Portal do Empreendedor (gov.br/mei).', 1),
('d1', 'Por que formalizar?', 'Com CNPJ você pode emitir nota fiscal, abrir conta e pedir crédito como empresa, e passa a contribuir para a Previdência (INSS), com direito a benefícios.', 'Abrir o MEI é gratuito. Desconfie de sites que cobram para fazer o cadastro.', 2);

-- Lição d2: Obrigações fiscais
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('d2', 'Pagar o DAS todo mês', 'O DAS é o boleto único mensal do MEI. O valor depende da atividade e é atualizado pelo governo. O vencimento é no dia 20 de cada mês.', 'Emita o DAS só pelo gov.br/mei ou pelo aplicativo MEI. Cuidado com boletos falsos enviados por mensagem.', 1),
('d2', 'Declaração anual', 'Todo ano o MEI entrega a Declaração Anual de Faturamento (DASN-SIMEI) até 31 de maio, pelo Portal do Empreendedor. É gratuita e dá para fazer sem contador.', 'Guarde notas e comprovantes: eles ajudam a informar o faturamento do ano.', 2),
('d2', 'Nota fiscal', 'Quando você vende ou presta serviço para outra empresa, precisa emitir nota fiscal. Para pessoa física, a regra depende da atividade e do município.', 'Confirme na prefeitura da sua cidade como emitir a nota de serviço.', 3);

-- Lição d3: Benefícios do INSS
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('d3', 'Você tem direito a', 'Com o DAS em dia, o MEI conta com aposentadoria por idade, auxílio por incapacidade temporária (antigo auxílio-doença), salário-maternidade e pensão por morte para a família. Cada benefício tem um tempo mínimo de contribuição.', 'Se parar de pagar o DAS, você pode perder a proteção da Previdência. Regularize assim que puder.', 1),
('d3', 'Salário-maternidade', 'A MEI que cumpre o tempo mínimo de contribuição pode receber salário-maternidade por 120 dias. O pedido é feito pelo Meu INSS (aplicativo ou site).', 'Guarde os comprovantes de pagamento do DAS: eles podem ser pedidos na análise.', 2);

-- Lição c1: Contrato simples e claro
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('c1', 'O básico de um bom contrato', 'Escreva quem contrata e quem presta o serviço, o que será entregue, o prazo, o valor e a forma de pagamento. Frases curtas e linguagem do dia a dia evitam mal-entendidos.', 'Contrato claro não precisa de palavras difíceis: precisa ser entendido pelos dois lados.', 1),
('c1', 'Combinado por escrito vale', 'Um acordo por e-mail ou mensagem também serve de prova. Peça que o cliente responda "de acordo" e guarde a conversa.', 'Salve as conversas e os arquivos importantes em um lugar seguro.', 2);

-- Lição c2: Orçamento e entrada
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('c2', 'Orçamento por escrito', 'Antes de começar, envie um orçamento com o que está incluído, o que não está e quanto custa cada parte. Assim o cliente sabe o que esperar e você evita pedidos extras de graça.', 'Defina quantos ajustes ou revisões já estão incluídos no preço.', 1),
('c2', 'Entrada e forma de pagamento', 'Combinar uma entrada ajuda a cobrir seus materiais e mostra compromisso dos dois lados. Combine também como e quando o restante será pago e registre tudo por escrito.', 'Prefira pagamentos que deixam comprovante, como Pix ou transferência, e guarde o comprovante.', 2);

-- Lição c3: Prazos, entregas e cancelamento
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('c3', 'Prazo e entrega', 'Combine as datas de início e de entrega e o que acontece se um dos lados atrasar. Se o cliente precisa enviar algo (medidas, fotos, materiais), coloque isso no combinado.', 'Avise o cliente logo que perceber que um prazo pode mudar.', 1),
('c3', 'E se mudar de ideia?', 'Escreva como funciona o cancelamento: o que é devolvido, o que fica para cobrir o trabalho já feito e com quanta antecedência avisar. Isso evita conflito e protege os dois.', 'Se não houver acordo, tente primeiro conversar; depois, o Procon da sua cidade pode orientar.', 2);

-- Lição g1: Atendimento profissional
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('g1', 'Primeira impressão importa', 'Responda com educação e clareza, apresente seu nome e o que você faz, e diga em quanto tempo vai retornar. Cumprir o prazo que você mesmo anunciou passa confiança.', 'No WhatsApp Business você pode criar mensagem de saudação e de ausência.', 1),
('g1', 'Como lidar com reclamações', 'Ouça sem interromper. Reconheça o problema, peça desculpas se for o caso e proponha uma solução concreta com prazo.', 'Um problema bem resolvido pode fortalecer a confiança do cliente.', 2);

-- Lição g2: Como cobrar corretamente
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('g2', 'Defina seu preço com clareza', 'Seja transparente sobre o valor antes de começar. Envie um orçamento escrito com o que está incluído.', 'Combine entrada e forma de pagamento antes de iniciar (veja o módulo Melhores Contratos).', 1),
('g2', 'Atraso no pagamento', 'Envie um lembrete gentil um dia antes do vencimento. Se atrasar, entre em contato direto, sem ameaças, mas com firmeza.', 'Com clientes novos, prefira receber parte antes de começar em vez de dar prazo longo.', 2);

-- Lição g3: Fidelização de clientes
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('g3', 'Clientes que voltam', 'Manter contato depois do serviço, perguntando se ficou satisfeito e avisando novidades, ajuda o cliente a lembrar de você e a indicar para outras pessoas.', 'Peça uma avaliação ou indicação ao final do serviço, com educação.', 1),
('g3', 'Programa simples de fidelidade', 'A cada certo número de serviços, ofereça um desconto ou um pequeno benefício. Mesmo algo simples cria vínculo.', 'Anote o nome, o serviço preferido e a data do último atendimento dos clientes frequentes.', 2);

-- Lição f1: Controle de gastos
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('f1', 'Separe o pessoal do profissional', 'Se puder, use uma conta separada para o negócio. Misturar o dinheiro da casa com o do trabalho dificulta saber se você está tendo lucro.', 'Compare as tarifas antes de escolher um banco ou conta para o negócio.', 1),
('f1', 'Anote tudo', 'Registre toda entrada e toda saída, toda semana, em caderno, planilha ou aplicativo gratuito. Depois de alguns meses você enxerga os meses mais fracos e se planeja melhor.', 'Escolha um dia fixo da semana para atualizar suas anotações.', 2);

-- Lição f2: Como precificar
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('f2', 'A fórmula básica', 'Custo + despesas fixas + margem de lucro = preço. Inclua tempo de deslocamento, materiais e retrabalho.', 'Pesquise o que outros profissionais da sua área cobram antes de definir seu preço.', 1),
('f2', 'Por hora ou por projeto', 'Cobrar por hora é mais simples no começo. Por projeto funciona melhor quando você já sabe quanto tempo cada trabalho leva.', 'Evite cobrar abaixo do seu custo: trabalhar no prejuízo não sustenta o negócio.', 2);

-- Lição f3: Primeiros investimentos
INSERT INTO trilha_cards (licao_id, heading, body, tip, ordem) VALUES
('f3', 'Reserva para imprevistos primeiro', 'Antes de investir, monte uma reserva para cobrir alguns meses de despesas, em uma aplicação segura da qual você consiga resgatar o dinheiro rápido.', 'Desconfie de promessa de ganho garantido e rápido.', 1),
('f3', 'Invista no negócio', 'Depois da reserva, invista no que faz o negócio render: ferramentas, cursos e divulgação. Um bom celular para fotografar seus produtos, por exemplo, ajuda a mostrar seu trabalho.', 'Redes sociais e WhatsApp Business são formas de divulgar com baixo custo.', 2);

import { Generated } from 'kysely';

export interface UsuariosTable {
  id: string;
  firebase_uid: string | null;
  name: string;
  email: string;
  phone: string;
  cpf: string;
  birth_date: string | null;
  password_hash: string | null;
  bio: Generated<string>;
  skills: Generated<string[]>;
  avatar_url: string | null;
  is_pro: Generated<boolean>;
  violeta_verified: Generated<boolean>;
  profile_completion: Generated<number>;
  rating: Generated<number>;
  completed_services: Generated<number>;
  notifications_enabled: Generated<boolean>;
  created_at: Generated<Date>;
  updated_at: Generated<Date>;
}

export interface EnderecosTable {
  id: Generated<string>;
  usuario_id: string;
  cep: string;
  rua: string;
  numero: string;
  complemento: Generated<string>;
  district: string;
  cidade: string;
  estado: string;
  latitude: number | null;
  longitude: number | null;
  created_at: Generated<Date>;
  updated_at: Generated<Date>;
}

export interface FreelancersTable {
  id: string;
  usuario_id: string | null;
  name: string;
  role: string;
  area: string;
  category: string;
  match_percent: Generated<number>;
  rating: Generated<number>;
  services_count: Generated<number>;
  availability: Generated<string>;
  bio: Generated<string>;
  gender: Generated<string>;
  avatar_url: string | null;
  hours_worked: Generated<number>;
  jobs_done: Generated<number>;
  is_violeta: Generated<boolean>;
  is_pro: Generated<boolean>;
  skills: Generated<string[]>;
  hourly_rate: Generated<number>;
  distance_km: Generated<number>;
  latitude: number | null;
  longitude: number | null;
  created_at: Generated<Date>;
  updated_at: Generated<Date>;
}

export interface AvaliacoesTable {
  id: Generated<string>;
  freelancer_id: string;
  autor_id: string | null;
  autor_nome: string;
  nota: number;
  comentario: string;
  created_at: Generated<Date>;
}

export interface PropostasServicosTable {
  id: Generated<string>;
  cliente_id: string;
  freelancer_id: string;
  service_type: string;
  budget: number;
  data_agendamento: string;
  hora_agendamento: string;
  localizacao: string;
  descricao: string;
  photo_urls: Generated<string[]>;
  urgencia: Generated<string>;
  status: Generated<string>;
  created_at: Generated<Date>;
  updated_at: Generated<Date>;
}

export interface OfertasServicosTable {
  id: Generated<string>;
  freelancer_id: string;
  category: string;
  title: string;
  description: string;
  price: number;
  city: string;
  availability: string;
  created_at: Generated<Date>;
}

export interface CarteiraSaldoTable {
  id: Generated<string>;
  usuario_id: string;
  disponivel: Generated<number>;
  pendente: Generated<number>;
  moeda: Generated<string>;
  updated_at: Generated<Date>;
}

export interface TransacoesFinanceirasTable {
  id: Generated<string>;
  usuario_id: string;
  client_name: string;
  service: string;
  location: string;
  amount: number;
  data_transacao: string;
  status: Generated<string>;
  withdrawable_at: Date | null;
  created_at: Generated<Date>;
}

export interface CartoesPagamentoTable {
  id: Generated<string>;
  usuario_id: string;
  brand: string;
  last4: string;
  holder_name: string;
  expiry: string;
  label: string;
  is_primary: Generated<boolean>;
  created_at: Generated<Date>;
}

export interface PlanosTable {
  id: string;
  name: string;
  monthly_price: number;
  benefits: Generated<string[]>;
  active: Generated<boolean>;
  created_at: Generated<Date>;
}

export interface AssinaturasTable {
  id: Generated<string>;
  usuario_id: string;
  plano_id: string;
  active: Generated<boolean>;
  next_charge_date: string | null;
  auto_renew: Generated<boolean>;
  auto_renew_method: Generated<string>;
  status: Generated<string>;
  created_at: Generated<Date>;
  updated_at: Generated<Date>;
}

export interface ProtocoloVioletaTable {
  id: Generated<string>;
  usuario_id: string;
  enabled: Generated<boolean>;
  identity_verified: Generated<boolean>;
  phone_verified: Generated<boolean>;
  identity_verified_at: Date | null;
  phone_verified_at: Date | null;
  safe_route_enabled: Generated<boolean>;
  emergency_button_enabled: Generated<boolean>;
  documento_frente_url: string | null;
  documento_verso_url: string | null;
  selfie_url: string | null;
  updated_at: Generated<Date>;
}

export interface TrilhaModulosTable {
  id: string;
  title: string;
  ordem: Generated<number>;
  created_at: Generated<Date>;
}

export interface TrilhaLicoesTable {
  id: string;
  modulo_id: string;
  title: string;
  ordem: Generated<number>;
  created_at: Generated<Date>;
}

export interface TrilhaCardsTable {
  id: Generated<string>;
  licao_id: string;
  heading: string;
  body: string;
  tip: string | null;
  ordem: Generated<number>;
}

export interface TrilhaProgressoUsuarioTable {
  usuario_id: string;
  licao_id: string;
  done: Generated<boolean>;
  completed_at: Date | null;
}

export interface CodigosRecuperacaoTable {
  id: Generated<string>;
  contato: string;
  codigo: string;
  canal: Generated<string>;
  expira_em: Date;
  usado: Generated<boolean>;
  created_at: Generated<Date>;
}

export interface Database {
  usuarios: UsuariosTable;
  enderecos: EnderecosTable;
  freelancers: FreelancersTable;
  avaliacoes: AvaliacoesTable;
  propostas_servicos: PropostasServicosTable;
  ofertas_servicos: OfertasServicosTable;
  carteira_saldo: CarteiraSaldoTable;
  transacoes_financeiras: TransacoesFinanceirasTable;
  cartoes_pagamento: CartoesPagamentoTable;
  planos: PlanosTable;
  assinaturas: AssinaturasTable;
  protocolo_violeta: ProtocoloVioletaTable;
  trilha_modulos: TrilhaModulosTable;
  trilha_licoes: TrilhaLicoesTable;
  trilha_cards: TrilhaCardsTable;
  trilha_progresso_usuario: TrilhaProgressoUsuarioTable;
  codigos_recuperacao: CodigosRecuperacaoTable;
}

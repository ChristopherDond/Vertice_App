# Vértice — Arquitetura da Camada de Dados

Contrato de referência para toda a refatoração. Nenhum subagente cria tipos, DTOs ou interfaces de repositório por conta própria: tudo abaixo é a fundação compartilhada.

## Princípios

1. **UI nunca conhece HTTP nem SQL.** Tela → ViewModel → Repository → (RemoteDataSource | LocalDataSource).
2. **Domain model ≠ DTO.** DTO espelha o JSON do backend; domain model espelha a tela. Mapper explícito entre os dois.
3. **Repository único por agregado** expõe `Flow` para leitura e `suspend` para escrita.
4. **Backend ainda não existe.** Toda interface remota tem implementação `Fake` funcional (dados semeados do Figma) e a implementação `Retrofit` já escrita, pronta para plugar. Troca por flag de build, não por reescrita.
5. **Auth é o único fluxo com estado global.** Token em `DataStore`, exposto via `SessionManager`; `AuthInterceptor` injeta o header.
6. **Persistência local real** (Room) para trilha, perfil e rascunhos — o que o app já guarda em memória hoje.

## Camadas

```
com.vertice.app
├── core/
│   ├── di/            ServiceLocator manual (sem Hilt — projeto pequeno, zero codegen)
│   ├── result/        VerticeResult<T> = Ok | Err(code, message)
│   ├── session/       SessionManager (token, userId, isLoggedIn: Flow<Boolean>)
│   └── validation/    Validadores CPF, e-mail, telefone, senha, CEP, cartão (Luhn)
├── data/
│   ├── remote/
│   │   ├── api/       AuthApi, ProfileApi, MatchApi, FinanceApi, LearningApi, SubscriptionApi
│   │   ├── dto/       *Dto.kt (request + response) com @Serializable
│   │   ├── interceptor/ AuthInterceptor, ErrorInterceptor
│   │   └── fake/      FakeAuthApi, FakeMatchApi... (implementam as MESMAS interfaces)
│   ├── local/
│   │   ├── VerticeDatabase.kt      Room
│   │   ├── dao/       ProfileDao, TrilhaDao, DraftDao
│   │   └── entity/    ProfileEntity, LessonProgressEntity, DraftEntity
│   ├── mapper/        Dto ↔ Domain ↔ Entity
│   └── repository/    AuthRepository, ProfileRepository, MatchRepository,
│                      FinanceRepository, LearningRepository, SubscriptionRepository
└── domain/
    ├── model/         User, Address, Freelancer, ServiceRequest, Transaction,
    │                  Card, Subscription, TModule, Lesson, LessonCard, VioletaStatus
    └── repository/    interfaces (implementadas em data/repository)
```

## Contratos de domínio (assinaturas — não alterar sem justificar)

```kotlin
interface AuthRepository {
    val session: Flow<Session?>
    suspend fun login(credential: String, password: String): VerticeResult<Session>
    suspend fun loginWithProvider(provider: AuthProvider): VerticeResult<Session>
    suspend fun register(form: RegistrationForm): VerticeResult<Session>
    suspend fun requestRecoveryCode(channel: RecoveryChannel, contact: String): VerticeResult<Unit>
    suspend fun resetPassword(contact: String, code: String, newPassword: String): VerticeResult<Unit>
    suspend fun changePassword(current: String, new: String): VerticeResult<Unit>
    suspend fun changeEmail(newEmail: String): VerticeResult<Unit>
    suspend fun changePhone(newPhone: String): VerticeResult<Unit>
    suspend fun logout()
}

interface ProfileRepository {
    suspend fun me(): VerticeResult<User>
    suspend fun update(form: ProfileForm): VerticeResult<User>
    fun trilhaProgress(): Flow<Set<String>>
    suspend fun markLessonDone(lessonId: String, done: Boolean)
}

interface MatchRepository {
    fun search(query: String, filters: MatchFilters): Flow<List<Freelancer>>
    suspend fun byId(id: String): VerticeResult<Freelancer>
}

interface FinanceRepository {
    suspend fun balance(): VerticeResult<Balance>
    suspend fun transactions(): VerticeResult<List<Transaction>>
    suspend fun cards(): VerticeResult<List<Card>>
    suspend fun addCard(card: CardForm): VerticeResult<Card>
    suspend fun subscription(): VerticeResult<Subscription>
    suspend fun setAutoRenew(method: AutoRenewMethod, enabled: Boolean): VerticeResult<Subscription>
    suspend fun cancelSubscription(): VerticeResult<Unit>
}

interface LearningRepository { suspend fun modules(): VerticeResult<List<TModule>> }

interface SubscriptionRepository {
    suspend fun plans(): VerticeResult<List<Plan>>
    suspend fun subscribe(planId: String, method: PaymentMethod): VerticeResult<Subscription>
}
```

## Taxonomia vinda do Figma (fonte de verdade)

- **Categorias de match** (Filtro 85:293): Construção, Comércio, Serviços, Beleza, Alimentação, Consultoria.
- **Protocolo Violeta** (21:42, 64:225): `Identidade verificada`, `Telefone confirmado`, `Rota segura`, `Botão de emergência`; verificação em 2 etapas (documentos → selfie).
- **Plano Pro** (33:819): R$ 19,99/mês, 40% economia vs. avulso, benefícios = matches ilimitados, prioridade nos resultados, acesso antecipado, relatórios de desempenho, selo Pro.
- **Autorenovação** (145:682): `PIX automático` (autorização bancária) e `Cartão automático` (recorrência no cartão).
- **Trilha** (32:435): 12 módulos, 4 temas — Segurança Pessoal, Proteção Digital/Direitos do MEI, Contratos Seguros, Melhores Contratos.
- **Extrato** (116:782): descrição + local + valor; saque liberado 1h após o serviço.

## Regras de UI

- Alvo Android: `393x852` é iPhone; em Android o layout é `fillMaxWidth` + scroll vertical, sem dimensões fixas de tela.
- Bottom nav do Figma tem 4 itens (Início, Match, Violeta, Perfil). Telas novas (Financeiro, Configurações, Trilha, Pro) são destinos de navegação empilhada com seta de voltar — **não** entram no bottom nav.
- Fluxo de auth (Splash → Login → Cadastro → Esqueci a Senha) é um grafo separado do grafo do app; a troca acontece por `isLoggedIn`.
- Estado por tela via `ViewModel` + `StateFlow`; nada de `rememberSaveable` para dado de negócio.

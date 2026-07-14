# AGENTS.md

## Project

Spring Boot 4.0.3 / Java 17 / Maven single-module REST API for gym membership management ("Usuarios GYM").

## Commands

- Build: `./mvnw clean install` (Linux/macOS) or `mvnw.cmd clean install` (Windows)
- Run: `./mvnw spring-boot:run` or run `SegundosFueraApplication.main()`
- Tests: `./mvnw test` (only a single `contextLoads` test exists — no real test coverage)

There is no separate lint, typecheck, or formatter step. Compilation via `mvnw` is the main verification.

## Database

- **MariaDB** required at `localhost:3306`, database `SegundosFuera`
- Credentials are hardcoded in `src/main/resources/application.properties` (`root`/`javier`) — do not commit real credentials
- Schema is managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update`) — no migration files exist
- The `PeriodPaid` table must be seeded manually before use (no `data.sql` or import script); it drives membership period logic (id 1 = standard month, id 3 = under-18 period)
- On startup, `StartupListener` runs `checkAndUpdateExpiredUsers()` which marks expired subscriptions — the app requires DB connectivity at boot

## Architecture

Package: `com.usuarios.segundosFuera`

| Layer | Package | Key file |
|---|---|---|
| REST | `Controller` | `SFController.java` — all endpoints under `/api/gym` |
| Business | `Services` | `SFService.java` — single service class |
| Data | `Repositorys` | `IUsersRepo.java`, `IPeriodPaidRepo.java` (Spring Data JPA) |
| Domain | `Models` | `UsersEntity.java`, `PeriodPayEntity.java` + `Requests/` and `Response/` DTOs |
| Mapping | `Mapper` | `UserMapper.java` — static manual mappers (no MapStruct) |
| Config | `Config` | `StartupListener.java` (startup expiration check) |

## Conventions & Gotchas

- **Lombok** is used on all entities and DTOs (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` on `UsersEntity`) — keep annotations, do not mix manual getters/setters with Lombok in the same class
- **Spring Security is commented out** in both `pom.xml` and `SecurityConfig.java` — all API endpoints are currently unauthenticated
- Package names use **singular capitalized** names (`Controller`, `Services`, `Repositorys`) which differs from standard Java conventions — follow existing naming when adding files
- Business logic lives in `SFService`, not in controllers or repositories
- Expiration logic: 30-day periods expire at end of calendar month (`TemporalAdjusters.lastDayOfMonth()`); shorter periods expire same-day
- Under-18 users are auto-assigned period id 3 when period 1 is requested (`SFService.SaveUser`)
- `UpdateUserRequest.age` uses `!= 0` as the "was it set?" check — `age=0` cannot be sent via update
- `UpdateUserPaidRequest` DTO exists but is unused — the paid toggle goes through `POST /api/gym/activate-by-dni/{id}` instead

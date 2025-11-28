# Architecture Overview

Kotatsu is an Android application for reading manga. The project follows a clean architecture approach, separating concerns into different layers.

## High-Level Structure

The application code is located in `app/src/main/kotlin/org/koitharu/kotatsu`. The main packages are:

-   `core`: Contains the core logic of the application, including database definitions, network configuration, parsing logic, and utility classes.
-   `reader`: Contains the specific logic and UI for the manga reader.
-   `backups`: Logic for backing up and restoring application data.
-   `features`: (Implied) various feature-specific packages like `explore`, `favourites`, `history`, `download`, etc., which contain the UI and logic for those specific features.

## Layers

### 1. Data Layer (`core/db`, `core/network`, `core/parser`)
This layer is responsible for handling data operations.

-   **Database**: Uses Room for local data persistence.
    -   `core/db/entity`: Database entities (e.g., `MangaEntity`, `ChapterEntity`).
    -   `core/db/dao`: Data Access Objects for database operations.
    -   `core/db/MangaDatabase`: The main Room database class.
-   **Network**: Handles network requests.
    -   `core/network`: OkHttp clients, interceptors (e.g., `CloudFlareInterceptor`, `RateLimitInterceptor`), and other network utilities.
-   **Parsers**: Handles manga source parsing.
    -   `core/parser`: Logic for parsing manga from various online sources.

### 2. Domain Layer
While not strictly separated into a single `domain` package, the domain logic is distributed across feature packages and `core`.

-   **Entities**: Domain objects often map 1:1 with database entities.
-   **Use Cases**: Some features implement Use Cases to encapsulate business logic (e.g., `reader/domain/DetectReaderModeUseCase`).

### 3. UI Layer (`core/ui`, feature packages)
This layer is responsible for presenting data to the user and handling user interactions.

-   **Activities & Fragments**: Android components for UI rendering.
    -   `core/ui`: Base classes for Activities (`BaseActivity`) and Fragments (`BaseFragment`).
    -   Feature packages (e.g., `reader/ui`) contain specific UI implementations.
-   **ViewModels**: Uses Jetpack ViewModel to manage UI-related data.

## Key Components

-   **MangaRepository**: Interfaces for accessing manga data, with implementations for different data sources (database, network).
-   **Reader**: The core feature for reading manga. It handles page loading, image rendering, and user interactions (zooming, tapping).

## Technologies Used

-   **Language**: Kotlin
-   **UI Toolkit**: XML Layouts (primarily)
-   **Dependency Injection**: Hilt (inferred from `AppModule` and `@AndroidEntryPoint` usage patterns typical in such apps).
-   **Asynchronous Processing**: Kotlin Coroutines & Flow.
-   **Database**: Room.
-   **Network**: OkHttp, Retrofit (likely, though direct OkHttp usage is seen).
-   **Image Loading**: Coil (inferred from `core/util/ext/Coil.kt`).

## Contributing

When contributing, please respect the existing package structure and naming conventions. New features should ideally be isolated in their own packages.

### Architecture:

The app follows a Model-View-ViewModel (MVVM) architecture with a Repository pattern to separate concerns and improve testability.

[x] View (UI): Built using Jetpack Compose for a modern and declarative UI. Composables are organized into screens and reusable components.
[x] ViewModel: Manages UI state and business logic. Uses StateFlow to expose UI state and interacts with the Repository to fetch and manage data.
[x] Repository: Acts as a single source of truth for data. It orchestrates data retrieval from both remote and local data sources, implementing an "internet-first, cache-fallback" strategy.
[x] Remote Data Source: Handles network API calls using Retrofit and OkHttp to fetch data from the remote API. Includes timeout configuration and error handling for network requests.
[x] Local Data Source: Manages local data persistence using Room Persistence Library. Caches product lists in a SQLite database for offline access.
[x] Data Models: Defines data classes for ProductListItem and ProductDetail to represent product data.
[x] Dependency Injection: Uses Hilt for dependency injection to manage component dependencies and improve testability.
[x] Minimal testing done for purposes of demoing only

### Dependencies
This project utilizes the following dependencies:

#### UI Framework:
`Jetpack Compose UI`

#### Asynchronous Operations
`Kotlin Coroutines`
`Kotlin Flow`

#### Networking:
`Retrofit`
`Retrofit Converter Gson`
`OkHttp`

#### Dependency Injection:
`Hilt Android`
`Hilt Compiler (kapt)`

#### Local Persistence:
`Room Runtime`
`Room Compiler (kapt)`

#### Image Loading & Caching:
`Coil Compose`

#### Unit Testing:
`JUnit 4`
`Mockito-kotlin`
`Truth`

### How to Run the App
[x] Clone the repository:
[x] Launch Android Studio.
[x] Select "Open an existing project" or "Open".
[x] Navigate to the directory where you cloned the repository and select the project's root directory.
[x] Build and Run
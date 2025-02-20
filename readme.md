### Architecture:

The app follows a Model-View-ViewModel (MVVM) architecture with a Repository pattern to separate concerns and improve testability.

- View (UI): Built using Jetpack Compose for a modern and declarative UI. Composables are organized into screens and reusable components.
- ViewModel: Manages UI state and business logic. Uses StateFlow to expose UI state and interacts with the Repository to fetch and manage data.
- Repository: Acts as a single source of truth for data. It orchestrates data retrieval from both remote and local data sources, implementing an "internet-first, cache-fallback" strategy.
- Remote Data Source: Handles network API calls using Retrofit and OkHttp to fetch data from the remote API. Includes timeout configuration and error handling for network requests.
- Local Data Source: Manages local data persistence using Room Persistence Library. Caches product lists in a SQLite database for offline access.
- Data Models: Defines data classes for ProductListItem and ProductDetail to represent product data.
- Dependency Injection: Uses Hilt for dependency injection to manage component dependencies and improve testability.
- Minimal testing done for purposes of demoing only

### Dependencies
This project utilizes the following dependencies:

```
Jetpack Compose UI
Kotlin Coroutine 
Kotlin Flow
Retrofit
Retrofit Converter Gson
OkHttp
Hilt Android
Hilt Compiler (kapt)
Room Runtime
Coil Compose
JUnit 4
Mockito-kotlin
Truth
```

### How to Run the App
- Clone the repository:
- Launch Android Studio.
- Select "Open an existing project" or "Open".
- Navigate to the directory where you cloned the repository and select the project's root directory.
- Build and Run
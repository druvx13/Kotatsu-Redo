## Kotatsu contribution guidelines

Thank you for your interest in contributing to Kotatsu! We welcome contributions from everyone.

### Getting Started

1.  **Read the Architecture Overview**: Before diving into the code, please read the [Architecture Overview](docs/ARCHITECTURE.md) to understand the project structure.
2.  **Check Issues**:
    *   If you want to **fix bugs** or **implement new features** that **already have an [issue card](https://github.com/Kotatsu-Redo/Kotatsu-Redo/issues)**: please assign this issue to you and/or comment about it.
    *   If you want to **implement a new feature**: open an issue or discussion regarding it to ensure it will be accepted.
3.  **Parsers**: In case you want to **add a new manga source,** refer to the [parsers repository](https://github.com/Kotatsu-Redo/kotatsu-parsers-redo).

### Development Guidelines

**Refactoring** or some **dev-faces improvements** might also be accepted. However, please stick to the following principles:

+   **Performance matters.** In the case of choosing between source code beauty and performance, performance should be a priority.
+   **Documentation**: Please document your code using KDoc, especially for public APIs and complex logic.
+   **Avoid adding new dependencies** unless required. APK size is important.
+   **Do not modify readme and other information files** (except for typos).

### Code Style

-   Follow standard Kotlin coding conventions.
-   Use meaningful variable and function names.

### Submitting Changes

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes.
4.  Verify your changes.
5.  Submit a Pull Request.

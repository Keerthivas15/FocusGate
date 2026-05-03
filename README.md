# Grit (FocusGate)

Grit is an Android application designed to help you regain control of your attention. It uses a "Math Gate" to add a small friction point before you can access distracting apps, encouraging mindful usage.

## Features

### 🧠 Math Gate
Before accessing a blocked app, you must solve a math problem. This interrupts the habit loop of mindless scrolling.
- **Selectable Categories**: Choose between **Basic Math** (Addition/Subtraction), **Algebra** (Linear Equations), and **Advanced** (Matrices/Vectors).
- **Interactive UI**: Clean, high-contrast interface designed for quick solving.

### 🎁 Idle Credit System
Rewarding you for staying focused!
- **Break Reward**: If you don't open a blocked app for **10 hours**, you get **1 hour** of free access.
- **Scaling Bonus**: For every additional hour of focus beyond 10, you earn an extra **15 minutes** of credit.
- **Automatic Unlock**: Grit detects your long break and automatically unlocks your apps when you return.

### 🛡️ Smart Protection
- **Persistent Setup**: Once you configure your permissions during onboarding, Grit remembers them. No repeated prompts!
- **Customizable Blocks**: Choose exactly which apps you want to protect.
- **Credit Consumption**: Earn credits by staying focused for long periods, which can be used to skip a math problem when you're in a hurry.

## Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Hilt / Dagger
- **Local Storage**: Room Database & DataStore Preferences
- **Background Service**: Android Foreground Service for app monitoring

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Keerthivas15/FocusGate.git
   ```
2. **Open in Android Studio**: Import the project as a Gradle project.
3. **Build & Run**: Deploy to your Android device.
4. **Onboarding**: Follow the steps to grant Usage Access, Overlay, and Battery Optimization permissions.
5. **Configure**: Go to Settings to choose your preferred Math categories and blocked apps.

## License
MIT License

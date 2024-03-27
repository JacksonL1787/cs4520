# Assignment 5 - API Adventures

This assignment evolves the Amazing Products (Assignment 2) app by integrating dynamic data fetching from an API, replacing the static dataset previously used. The application now includes a login screen, a product list screen that dynamically fetches data, and implements pagination for handling large datasets efficiently.

## Getting Started

1. Clone the repository or download the project to your local machine.
2. Open the project in Android Studio.
3. Once the sync is complete, run the application on your device or emulator.

## Usage

1. Launch the application on your device.
2. On the login screen, enter the following credentials:

   - Username: `admin`
   - Password: `admin`

3. Press the "Log in" button to access the product list.

## Using the Product List

- A progress spinner is shown while data is being loaded.
- On success, products are displayed to the user.
  - If the API returns no products, the message "No products available" is shown.
  - If there is an error fetching the products, an error message is shown.
  - In offline mode, the application will fetch and display data from a local database if available.

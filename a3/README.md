# Assignment 3 - Favorite Calculator

A simple Android mobile application designed to perform basic mathematic operations such as addition, subtractions, multiplication, and division. The application features two implementations of this calculator, one using an Model-View-Presenter (MVP) architecture and another using Model-View-ViewModel (MVVM) architecture.

## Getting Started

1. Clone the repository or download the project to your local machine.
2. Open the project in Android Studio.
3. Once the sync is complete, run the application on your device or emulator.

## Usage

1. Launch the application on your device.
2. After launching the app, you can choose to use the **MVP** or **MVVM** calculator (they're nearly identical) by clicking on one of the respective buttons.
3. Once in one of the calculators, enter the two numbers you want to use in your calculation into the inputs at the top of the screen.
   - NOTE: You are limited to two numbers for each calculation.
4. After entering your numbers in, choose one of the following operations:

   - Add
   - Subtract
   - Multiply
   - Divide

5. The result for the calculation will appear below the inputs.

## Additional Information

- The same Model is used for both the MVP and MVVM implementations. Given the assignment requirements, there wasn't a need to create separate models for each.
  - The only quirk is that the Presenter (in MVP) has to set the numbers in the calculator before running a calculation. However, this actually works out well with the abstracted `runCalculation(operation: () -> Double)` method in the presenter.

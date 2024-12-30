# Asclepius

Asclepius is an Android application designed to assist in detecting cancer through image analysis. The app utilizes a machine learning model (TensorFlow Lite) to classify images as cancerous or non-cancerous. It also features a news page with the latest articles related to cancer research and treatments. All analysis history is stored in a dedicated history page for easy access and reference.

## Screenshots
![asclepius-screenshot](https://github.com/user-attachments/assets/442f7829-26da-403c-8ea9-3eabd96d91e4)

## Table of Contents
- [Features](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#features)
- [Technologies Used](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#technologies-used)
- [Architecture](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#architecture)
- [Machine Learning Model](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#machine-learning-model)
- [Getting Started](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#getting-started)
  - [Prerequisites](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#prerequisites)
  - [Installation](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#installation)
- [App Structure](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#app-structure)
- [Usage](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#usage)
- [Contributing](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#contributing)
- [License](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#license)
- [Contact](https://github.com/zyrridian/dicoding-book-app/edit/master/README.md#contact)

## Features
- **Cancer Detection**: Upload a picture, and the app will use TensorFlow Lite to analyze the image and detect if cancer is present.
- **News Feed**: Get the latest news and articles related to cancer, research, treatments, and health updates.
- **History**: Stores past analyses, allowing users to review their detection history.
- **User-friendly UI**: Simple navigation and interaction, focusing on accessibility and ease of use.

## Technologies Used
- **Language**: Kotlin
- **Machine Learning**: TensorFlow Lite
- **Data Binding**: LiveData, ViewModel
- **Networking**: Retrofit for fetching news data
- **Local Storage**: Room Database for storing analysis history
- **UI**: RecyclerView, Material Components, View Binding
- **Others**: AndroidX, Navigation Component

## Architecture
This app follows the **MVVM (Model-View-ViewModel)** architecture pattern, which helps in maintaining a clean separation of concerns, easy testing, and better organization of code:
- **Model**: Handles the image classification using TensorFlow Lite and stores history data using Room Database.
- **View**: Displays the results, news articles, and history in a clean and intuitive user interface.
- **ViewModel**: Manages the data and logic, coordinating between the View and Model.

## Machine Learning Model
- The cancer detection model is implemented using **TensorFlow Lite**.
- The model is pre-trained on medical image data to classify cancerous and non-cancerous images.
- **Image Preprocessing**: The images are processed to fit the input size of the model for accurate predictions.

## Getting Started
To get a local copy of the project up and running on your machine, follow these steps:

### Prerequisites
Ensure you have the following software installed:
- Android Studio (latest stable version recommended)
- Git (for cloning the repository)
- TensorFlow Lite Model (integrated into the project)
- News API's API Key

### Installation
1. Clone the repository:
   Open a terminal and run the following command to clone the repository:
    ```git clone https://github.com/zyrridian/asclepius.git```

2. Open the project in Android Studio:
    - Open Android Studio.
    - Select Open an `existing Android Studio project`.
    - Navigate to the cloned repository and select it.

3. Build the project:
    - Create a new `ApiKey.kt` object class and use your own API Key. Or you can put it somewhere else.
    - Android Studio will automatically build the project.
    - If the build does not start automatically, select `Build > Make Project` from the menu.

5. Run the app:
    - Connect your Android device or use an emulator.
    - Click the green play button to run the app on the device.
  
## App Structure
- **Home Page**: The main interface where users can select or take an image for cancer detection.
- **Detection Page**: Once an image is selected, the TensorFlow Lite model processes the image and displays the detection result (Cancerous / Non-Cancerous).
- **News Page**: Displays the latest cancer-related news fetched via Retrofit. Articles include titles, summaries, and links to full articles.
- **History Page**: Stores all past image analysis results with timestamps, allowing users to review their history.

## Usage
Once the app is running, you can:
- **Detect Cancer**: Upload or take a picture of a suspect area, and the TensorFlow Lite model will analyze it to detect cancer.
- **Read News**: Stay updated with the latest news on cancer research, treatments, and health.
- **Review History**: View previous analysis results on the history page, where results are stored locally using Room Database.

### Image Detection Flow:
- **Image Capture/Upload**: The user can take a photo or upload one from their gallery.
- **Image Processing**: The image is resized and preprocessed for the TensorFlow Lite model.
- **Result Display**: The app provides a clear result (Cancerous / Non-Cancerous) along with an option to save it in history.

## Contributing
Contributions are welcome! If you would like to contribute to the project, please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature/your-feature`).
6. Create a pull request.
Please ensure your code follows the existing code style and includes appropriate documentation.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Contact
- Author: Rezky Aditia Fauzan
- Email: rezky246@gmail.com
- GitHub: https://github.com/zyrridian

# Linked-List-Visualizer

A Java-based application that demonstrates and visualizes the behavior of a singly linked list. The project provides implementations of core operations such as insertion, deletion, traversal, and searching.

## Project Structure

```
src/
├── Main.java
├── components/
│   └── Visualizer.java
├── models/
│   └── Node.java
└── utils/
    └── Operations.java
```

## Prerequisites
- Java Development Kit (JDK) 8 or later installed
Verify your installation with:
```
java -version
javac -version
```
### Clone the Repository
```
git clone https://github.com/AlBazazi/Linked-List-Visualizer.git
cd Linked-List-Visualizer
```
### Compile the Project
Linux / macOS
```
javac -d out src/models/*.java src/utils/*.java src/components/*.java src/Main.java
```
Windows (Command Prompt)
```
javac -d out src\models\*.java src\utils\*.java src\components\*.java src\Main.java
```
### Run the Application
```
java -cp out Main
```

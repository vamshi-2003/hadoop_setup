# 🚀 **Hadoop + Maven Java Project Setup** 🌟

Welcome to the **Hadoop Java Project**! This guide will help you set up and run Hadoop-based Java projects using Maven in Visual Studio Code (VS Code). Let's get started!

---

## 🎯 **Setup Instructions**

1. **Install Maven for Java extension in VS Code**  
   👉 Make sure Maven is installed and configured in your development environment. This is essential for building and managing your Java projects.

2. **Install Hadoop and Set It Up**  
   👉 Follow the official Hadoop setup guide for a single-node cluster from [here](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html). Ensure Hadoop is properly installed and configured.

---

## 📋 **Instructions to Use**

1. **Write Your Code in `App.java`**  
   🖋️ Place your Hadoop-related code in the `App.java` file. This is where the core logic of your Hadoop program resides.

2. **Change the Name of the Classes**  
   📝 Modify the class names in `App.java` as per your use case. Remember to reflect the changes in other parts of your code if necessary.

3. **Comment Out `package com.example`**  
   🛑 Ensure the package declaration (`package com.example;`) is commented out or removed from the code. This prevents unnecessary errors during execution.

4. **Input Files Directory**  
   📂 Place your input files in the `hadoop_IO/input` folder. This is where the Hadoop program will read input data from.

5. **Edit the Path in `commands.sh`**  
   ✏️ Edit the file path in the second line of `commands.sh`. Ensure the paths point to your Hadoop installation and data directories.

6. **Run `commands.sh`**  
   ▶️ Execute the `commands.sh` script to compile and run your Hadoop program with the input data. This will process the input using Hadoop and generate output as specified in your code.

---

## 🛠️ **Helpful Links**
- **Maven Documentation**: [https://maven.apache.org/](https://maven.apache.org/)
- **Hadoop Documentation**: [https://hadoop.apache.org/](https://hadoop.apache.org/)

---

## 🖥️ **Example Commands**
Below are some example commands you might need to run during setup or execution:

```bash


# To execute the Hadoop job
./commands.sh

# kibo_lynx_test
For testing kibo_lynx's functions and algorithms (ex. image processing, reading qr code, ar tag)

## Set up
- IDE: Intellij IDEA
- JDK 1.8.0_251 (Java SE 8) -- Don't forget to add env. variable (JAVA_HOME, PATH)

## FYI
- Gradle version is the same as kibo_lynx's
- Libraries used are of the same version except openCV which uses windows 64bit build but still the same version
- You can't use com.android.xxx and org.opencv.android
- This is not an Android project.

## Compile
USE `./gradlew jar` in root project directory

The directory of build is `./build/libs`

## Set up for Running
*Only for the first run **./ is the root project directory
1. Copy `./libs/opencv-344.jar` to `./build/libs`
2. Put `opencv_java344.dll` downloaded from https://drive.google.com/file/d/1_FgaMLF3WrvfXUlGBpgUBHJ9X7kl7d6e/view?usp=sharing (64-bit only)
in Windows/System32
3. Copy the jar archives of zxing (core and javase) under External Libraries and put them in `./build/libs`
![](https://media.discordapp.net/attachments/837771986469519401/838120259495657472/Screenshot_2021-05-02_012919.png?width=540&height=669)
- Right click on the jar and select show in explorer to open its location.

## RUN
USE `java -jar ./kibo_lynx_test-1.0-SNAPSHOT.jar` in build directory.

## Testing
Please read the testing code before testing. You can edit code, compile and run freely.

## Common Issues
- https://stackoverflow.com/questions/15796855/java-is-not-recognized-as-an-internal-or-external-command
- Project JDK version is not the same as Running JDK version (`java -version`) -- make sure your own java path is above Windows/System32 and other java paths
- Cannot resolve symbol for ..... -- refer to a message in Discord
- Can't run error : NoClassDefFoundError, unsatisfiedlinkerror, no opencv_java344 in java.library.path  --- Please check the Set up for Running section again and
make sure your own running JDK version (`java -version`) is the same as project JDK version.

# kibo_lynx_test
For testing kibo_lynx's functions and algorithms (ex. image processing, reading qr code, ar tag)

## Set up
- IDE: Intellij IDEA
- JDK 1.8.0_291 (Java SE 8) -- Don't forget to add env. variable (JAVA_HOME, PATH)

## FYI
- Gradle version is the same as kibo_lynx's
- Libraries used are of the same version except openCV which uses windows 64bit build but still the same version
- You can't use com.android.xxx and org.opencv.android
- This is not an Android project.

## Compile
USE `./gradlew jar` in root project directory

The directory of build is `./build/libs`

## Run
*Only for the first run **./ is the root project directory
1. Copy `./libs/opencv-344.jar` to `./build/libs`
2. Put `opencv_java344.dll` downloaded from https://drive.google.com/file/d/1_FgaMLF3WrvfXUlGBpgUBHJ9X7kl7d6e/view?usp=sharing (64-bit only)
in Windows/System32

THEN USE `java -jar ./kibo_lynx_test-1.0-SNAPSHOT.jar` in build directory.

## Testing
Please read the testing code before testing. You can edit code, compile and run freely.

## Common Issues
- https://stackoverflow.com/questions/15796855/java-is-not-recognized-as-an-internal-or-external-command
- Cannot resolve symbol for ..... -- refer to a message in Discord
- Can't run error : NoClassDefFoundError, unsatisfiedlinkerror, no opencv_java344 in java.library.path  --- Please check the Run section again. 

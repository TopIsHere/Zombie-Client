@echo off
setlocal
where java >nul 2>nul || (echo Java not found. Install JDK 25. & exit /b 1)
if not exist gradlew.bat (echo Gradle wrapper missing. Run: gradle wrapper --gradle-version 9.4.0 & exit /b 1)
call gradlew.bat build
if errorlevel 1 exit /b %errorlevel%
echo Built JAR should be in build\libs

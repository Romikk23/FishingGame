@echo off

cd %~dp0

if exist %~dp0\loading\target\ (
  cd "loading"
  start /B "" javaw -jar target\loading-0.1-Beta.jar
  cd ..
  cd game
  mvn compile
  taskkill -f -im java*
  start /B "" javaw -jar target\game-0.1-Beta.jar
) else (
  cd loading
  mvn clean
  mvn compile
  mvn package
  start /B "" javaw -jar target\loading-0.1-Beta.jar
  cd ..
  cd game
  call mvn clean
  call mvn compile
  call mvn package
  taskkill -f -im java*
  start /B "" javaw -jar target\game-0.1-Beta.jar
)

wmic Path win32_process Where "Caption Like 'cmd%.exe'" Call Terminate
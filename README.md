# :feet: Проект с автотестами для сайта pet-yes.com 

![alt "PetYes"](./images/PetYes.png "PetYes")

## :gear: Стек технологий:
| IDEA | Java | Selenide | Selenoid | Allure Report | Gradle | Junit5 | GitHub | Jenkins | Allure TO | Jira |
|:--------:|:-------------:|:---------:|:-------:|:----:|:------:|:----:|:----:|:------:|:------:|:--------:|
| <img src="images/Intelij_IDEA.svg" width="40" height="40"> | <img src="images/JAVA.svg" width="40" height="40"> | <img src="images/Selenide.svg" width="40" height="40"> | <img src="images/Selenoid.svg" width="40" height="40"> | <img src="images/Allure_Report.svg" width="40" height="40"> | <img src="images/Gradle.svg" width="40" height="40"> | <img src="images/Junit5.svg" width="40" height="40"> | <img src="images/GitHub.svg" width="40" height="40"> | <img src="images/Jenkins.svg" width="40" height="40"> | <img src="images/Allure_TO.svg" width="40" height="40"> | <img src="images/Jira.svg" width="40" height="40"> |
___

## :pushpin: В качестве CI системы использован Jenkins
### [Проект PetYes](https://jenkins.autotests.cloud/job/08-levikss-PetYes/)

![alt "Jenkins"](./images/Jenkins.png "Jenkins")

## :arrow_forward: Запуск тестов

Если файл local.properties заполнен, то для запуска тестов необходимо выполнить следующую команду:

```bash
gradle clean test
```

Если файл local.properties не заполнен, то для запуска тестов необходимо выполнить следующую команду:

```bash
gradle clean test 
-Dbrowser=${BROWSER} 
-DbrowserVersion=${BROWSER_VERSION} 
-DbrowserSize=${BROWSER_SIZE} 
-DbrowserMobileView="${BROWSER_MOBILE}" 
-DremoteDriverUrl=https://user1:1234@${REMOTE_DRIVER_URL}/wd/hub/ 
-DvideoStorage=https://${REMOTE_DRIVER_URL}/video/ -Dthreads=${THREADS}
```
- в параметре Dbrowser - указываем браузер, в котором будут выполняться тесты
- в параметре DbrowserVersion - указываем версию браузера
- в параметре DbrowserSize - указываем размер окна браузера
- в параметре DbrowserMobileView - указываем мобильное устройство, на котором будут выполняться тесты
- в параметре DremoteDriverUrl - указываем логин, пароль и адрес удаленного сервера, где будут проходить тесты 
- в параметре DvideoStorage указываем место для сохранения видео
- в параметре Dthreads задаем количетство потоков

## :bar_chart: Генерация отчета происходит в Allure Report

Для генерации отчета необходимо выполнить следующую команду:

```bash
allure serve build/allure-results
```

![alt "Allure Report"](./images/Allure_report1.png "Allure Report")

К каждому тесту прикладываются:
- Скриншоты
- Исходники страницы
- Логи браузера
- Видео

![alt "Allure Report"](./images/Allure_report2.png "Allure Report")

## :bar_chart: Результаты прохождения тестов записываются в Allure TestOps

![alt "Allure TestOps"](./images/Allure_TO.png "Allure TestOps")

## :pushpin: Проект интегрирован с [Jira](https://jira.autotests.cloud/browse/AUTO-488)

![alt "Jira"](./images/Jira.png "Jira)

## :heavy_check_mark: Уведомления о прохождении тестов отправляются в Telegram

![alt "Telegramm_notification"](./images/Telegramm_notification.png "Telegramm_notification") 

## :movie_camera: Видео с прохождением теста "Создание питомца"

![alt "CreatePetVideo"](./images/CreatePetVideo.gif "CreatePetVideo") 

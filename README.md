# Job Finder Server

Проект реализует сервер на Ktor, предоставляющий REST API для управления данными компаний, вакансий и резюме.
Этот сервер является основой для работы [Job Finder App](https://github.com/manwoodt/searchJob-client), предоставляя все необходимые данные.

## Основные возможности

### Компании ###
- Получение списка всех компаний.
- Получение информации о компании по её `id`.

### Вакансии ###
- Получение списка всех вакансий.
- Получение информации о вакансии по её `id`.

### Резюме ###
- Получение списка всех резюме.
- Получение информации о резюме по `id`.
- Добавление тегов к резюме.
- Получение тегов резюме по `id`.

## Используемые технологии

- **Ktor**: фреймворк для разработки серверных приложений на Kotlin.
- **Kotlin Serialization**: для работы с сериализацией и десериализацией JSON.
- **Netty**: серверный движок для Ktor.

## Установка и запуск

1. Склонируйте репозиторий на ваше устройство:
    ```bash
    git clone https://github.com/manwoodt/searchJob-server.git
    ```  

2. Откройте проект в **Android Studio**.

3. Соберите и запустите проект.

---

Этот сервер обеспечивает надежное взаимодействие с клиентом и служит важной частью системы **Job Finder**.

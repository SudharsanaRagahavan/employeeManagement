# employeeManagement
Basic project for employee Schedule Management


APIS 

1) POST http://localhost:8080/api/v1/employee

Create a new employee Schedule 

Request Body : 

{
    "employeeId": "11",
    "schedule": [
        {
            "startDate": "2021-04-01",
            "endDate": "2021-04-14",
            "time": "10:00",
            "duration": "60",
            "repeat": false,
            "frequency": null
        }
    ]
}

2) GET http://www.localhost:8080/api/v1/employee?employee_id=4

Get Employee by Id 

3) PUT http://www.localhost:8080/api/v1/employee/4

Update Employee By ID 

RequestBody 

{
    "schedule": [
        {
            "startDate": "2021-04-01",
            "endDate": "2021-04-01",
            "time": "10:00",
            "duration": "60",
            "repeat": false,
            "frequency": null
        },
        {
            "startDate": "2021-04-01",
            "endDate": "2021-04-11",
            "time": "10:00",
            "duration": "60",
            "repeat": false,
            "frequency": null
        }
    ]
}

4) GET http://www.localhost:8080/api/v1/employee/date?employeeGetSchedule=2021-04-01

Get all Schedule By date

5) POST http://www.localhost:8080/api/v1/employee/remove/schedule?employeeId=4

Remove all schedule By employee date

6) POST http://www.localhost:8080/api/v1/employee/remove/schedule/date?employeeId=4&startDate=2021-04-12

remove schdeule on given date 

7) POST http://www.localhost:8080/api/v1/employee/remove/schedule/date?employeeId=4&startDate=2021-04-12&endDate=2021-05-01

remove schedule on two given date 

